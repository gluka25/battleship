import com.googlecode.lanterna.terminal.Terminal
import configuration.DatabaseSettings
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import repository.entity.*

class BattleField(battleFieldItems: Collection<BattleFieldItem>, size: Int) : IBattleField {
    private val grid: MutableList<MutableList<MutableList<BattleFieldItem?>>> = mutableListOf()

    val fieldPoints: List<List<List<BattleFieldItem?>>>
        get() = grid

    init {
        repeat(size) {
            val layer = mutableListOf<MutableList<BattleFieldItem?>>()
            repeat(size) {
                val line = mutableListOf<BattleFieldItem?>()
                repeat(size) {
                    line.add(null)
                }
                layer.add(line)
            }
            grid.add(layer)
        }
        for (battleFieldItem in battleFieldItems) {
            for (point in battleFieldItem.itemPoints()) {
                grid[point.z][point.y][point.x] = battleFieldItem
            }
        }
    }

    override fun toString(): String {
        return grid.joinToString(separator = "\n\n") { layer ->
            layer.joinToString(separator = "\n") { line ->
                line.map {
                    it?.icon ?: '_'
                }.joinToString(separator = " ")
            }
        }
    }
}

// реализовать послойную визуализацию (в консоли) как метод расширения класса игрового поля;
fun BattleField.print(terminal: Terminal) {
    val grid = toString()
    grid.forEach { terminal.putCharacter(it) }
    terminal.flush()
}

fun BattleField.saveToDB() {
    DatabaseSettings.embedded
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Ships, Mines, Helps)
        for (layer in fieldPoints)
            for (line in layer)
                for (point in line)
                    point?.saveToDb()
    }
}

fun loadFromDB(battlefieldSize: Int): BattleField {
    DatabaseSettings.embedded
    val mines = mutableListOf<Mine>()
    val helps = mutableListOf<Help>()
    val ships = mutableListOf<Ship>()
    val movingShips = mutableListOf<MovingShip>()
    transaction {
        addLogger(StdOutSqlLogger)
        for (mine in MineEntity.all()) {
            mines.add(Mine(Point3(mine.x, mine.y, mine.z)))
        }
        for (help in HelpEntity.all()) {
            helps.add(Help(Point3(help.x, help.y, help.z)))
        }
        for (ship in ShipEntity.find { Ships.type eq false }) {
            ships.add(
                Ship(
                    Point3(ship.xFirstPoint, ship.yFirstPoint, ship.zFirstPoint),
                    Point3(ship.xLastPoint, ship.yLastPoint, ship.zLastPoint)
                )
            )
        }
        for (ship in ShipEntity.find { Ships.type eq true }) {
            movingShips.add(
                MovingShip(
                    Point3(ship.xFirstPoint, ship.yFirstPoint, ship.zFirstPoint),
                    Point3(ship.xLastPoint, ship.yLastPoint, ship.zLastPoint)
                )
            )
        }
    }
    return BattleField(mines + helps + ships + movingShips, battlefieldSize)
}