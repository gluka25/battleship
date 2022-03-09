import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import repository.entity.Helps
import repository.entity.Mines
import repository.entity.Ships


fun main(args: Array<String>) {
//    Создать заготовку для заполнения поля 8х8х8 случайно расположенными
//    "кораблями" (2 по 4, 8 по 2, 8 по 1), 1 "миной" и 1 "вызвать подмогу".
    val battlefieldSize = 8
    val gameSteps = 5
    val mineCount = 5
    val helpCount = 0

//    val battleFieldGenerator = BattleFieldGenerator(
//        battlefieldSize, 1, 1,
//        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1)),
//        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1))
//    )
    val battleFieldGenerator = BattleFieldGenerator(
        battlefieldSize, mineCount, helpCount,
        listOf(ShipTypeCount(1, 10)),
        listOf(ShipTypeCount(1, 10))
    )
    val battleField = battleFieldGenerator.generateInfo()

//    val defaultTerminalFactory = DefaultTerminalFactory()
//    val terminal = defaultTerminalFactory.createTerminal()
//    battleField.print(terminal)

    battleField.saveToDB()
    val battlefieldLoad = loadFromDB(battlefieldSize)
    println(battlefieldLoad.toString())

    for (i in 1..gameSteps) {
        println("---------------------------STEP$i----------------------------------------------")
        battleField.moveItems()
        println(battleField.toString())
    }

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.drop(Ships, Mines, Helps)
    }
}
