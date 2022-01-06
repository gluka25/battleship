import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import repository.entity.Helps
import repository.entity.Mines
import repository.entity.Ships


fun main(args: Array<String>) {
//    Создать заготовку для заполнения поля 8х8х8 случайно расположенными
//    "кораблями" (2 по 4, 8 по 2, 8 по 1), 1 "миной" и 1 "вызвать подмогу".
    val battlefieldSize = 8

    val battleFieldGenerator = BattleFieldGenerator(
        battlefieldSize, 1, 1,
        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1)),
        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1))
    )
    val battleField = battleFieldGenerator.generateInfo()

    val defaultTerminalFactory = DefaultTerminalFactory()
    val terminal = defaultTerminalFactory.createTerminal()
    battleField.print(terminal)

    battleField.saveToDB()
    val battlefieldLoad = loadFromDB(battlefieldSize)
    println(battlefieldLoad.toString())

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.drop(Ships, Mines, Helps)
    }
}