import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import repository.entity.Helps
import repository.entity.Mines
import repository.entity.Ships

const val FIELD_SIZE = 8
const val GAME_STEPS = 5
const val MINE_COUNT = 5
const val HELP_COUNT = 0
const val SHIP_COUNT = 10
const val MOVING_SHIP_COUNT = 10

fun main(args: Array<String>) {
//    Создать заготовку для заполнения поля 8х8х8 случайно расположенными
//    "кораблями" (2 по 4, 8 по 2, 8 по 1), 1 "миной" и 1 "вызвать подмогу".

//    val battleFieldGenerator = BattleFieldGenerator(
//        battlefieldSize, 1, 1,
//        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1)),
//        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1))
//    )
    val battleFieldGenerator = BattleFieldGenerator(
        FIELD_SIZE, MINE_COUNT, HELP_COUNT,
        listOf(ShipTypeCount(1, SHIP_COUNT)),
        listOf(ShipTypeCount(1, MOVING_SHIP_COUNT))
    )
    val battleField = battleFieldGenerator.generateInfo()

//    val defaultTerminalFactory = DefaultTerminalFactory()
//    val terminal = defaultTerminalFactory.createTerminal()
//    battleField.print(terminal)

    battleField.saveToDB()
    val battlefieldLoad = loadFromDB(FIELD_SIZE)
    println(battlefieldLoad.toString())

    for (i in 1..GAME_STEPS) {
        println("---------------------------STEP$i-----------------------------------------------")
        battleField.moveItems()
        println(battleField.toString())
    }

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.drop(Ships, Mines, Helps)
    }
}
