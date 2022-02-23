package battlefield
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import battlefield.repository.entity.Helps
import battlefield.repository.entity.Mines
import battlefield.repository.entity.Ships
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan //(basePackages = ["battlefield*"])
//@EntityScan(basePackages = ["entities-package"])

open class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
//    Создать заготовку для заполнения поля 8х8х8 случайно расположенными
//    "кораблями" (2 по 4, 8 по 2, 8 по 1), 1 "миной" и 1 "вызвать подмогу".
    val battlefieldSize = 8
    val gameSteps = 5
    val mineCount = 5
    val helpCount = 0

//    val battleFieldGenerator = battlefield.BattleFieldGenerator(
//        battlefieldSize, 1, 1,
//        listOf(battlefield.ShipTypeCount(1, 4), battlefield.ShipTypeCount(2, 4), battlefield.ShipTypeCount(4, 1)),
//        listOf(battlefield.ShipTypeCount(1, 4), battlefield.ShipTypeCount(2, 4), battlefield.ShipTypeCount(4, 1))
//    )
    val battleFieldGenerator = BattleFieldGenerator(
        battlefieldSize, mineCount, helpCount,
        listOf(ShipTypeCount(1, 10)),
        listOf(ShipTypeCount(1, 10))
    )
    val battleField = battleFieldGenerator.generateInfo()

//    val defaultTerminalFactory = DefaultTerminalFactory()
//    val terminal = defaultTerminalFactory.createTerminal()
//    battleField.battlefield.print(terminal)

    battleField.saveToDB()
    val battlefieldLoad = loadFromDB(battlefieldSize)
    println(battlefieldLoad.toString())

    for (i in 1..gameSteps) {
        println("---------------------------STEP$i-----------------------------------------------")
        battleField.moveItems()
        println(battleField.toString())
    }

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.drop(Ships, Mines, Helps)
    }
}


