import com.googlecode.lanterna.terminal.Terminal
import configuration.DatabaseSettings
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import repository.entity.HelpEntity
import repository.entity.Helps
import repository.entity.MineEntity
import repository.entity.Mines
import repository.entity.ShipEntity
import repository.entity.Ships


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
            addNewItem(battleFieldItem)
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


    fun moveMines() {
        val mines: MutableSet<Mine> = findMines()
        for (mine in mines) {
            val newMine = mine.tryMove(this)
            this.refresh(mine, newMine)
        }
    }

    fun findMines(): MutableSet<Mine> {
        val mines = mutableSetOf<Mine>()
        for (layer in fieldPoints) {
            for (line in layer) {
                for (item in line) {
                    if (item is Mine) {
                        mines.add(item)
                    }
                }
            }
        }
        return mines
    }

    fun findShips(): MutableSet<Ship> {
        val ships = mutableSetOf<Ship>()
        for (layer in fieldPoints) {
            for (line in layer) {
                for (item in line) {
                    if (item is Ship) {
                        ships.add(item)
                    }
                }
            }
        }
        return ships
    }

    fun moveShips() {
        //var movingShips = mutableSetOf<MovingShip>()
        val ships = findShips()
        val movingShips: MutableSet<MovingShip> = ships.filterIsInstance<MovingShip>().toMutableSet()
//        for (layer in fieldPoints) {
//            for (line in layer) {
//                for (item in line) {
//                    if (item is MovingShip) {
//                        movingShips.add(item)
//                    }
//                }
//            }
//        }
        for (ship in movingShips) {
            val newShip = ship.tryMove(this)
            this.refresh(ship, newShip)
        }
    }

    fun refresh(item: BattleFieldItem, newItem: BattleFieldItem) {
        for (point in item.itemPoints()) {
            grid[point.z][point.y][point.x] = null
        }
        addNewItem(newItem)
    }

    fun addNewItem(newItem: BattleFieldItem) {
        for (newPoint in newItem.itemPoints()) {
            grid[newPoint.z][newPoint.y][newPoint.x] = newItem
        }
    }

    fun addCrashPoint(point: Point3) {
        grid[point.z][point.y][point.x] = CrashPoint(Point3(point.x, point.y, point.z))
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


fun BattleField.moveItems() {
    moveMines()
    moveShips()
}


