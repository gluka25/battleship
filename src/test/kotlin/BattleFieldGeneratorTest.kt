import configuration.DatabaseSettings
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import repository.entity.Helps
import repository.entity.Mines
import repository.entity.Ships


class BattleFieldGeneratorTest {

    @BeforeEach
    fun init() {
        DatabaseSettings.embedded
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.drop(Ships, Mines, Helps)
        }
    }

    @AfterEach
    fun teardown() {
        DatabaseSettings.embedded
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.drop(Ships, Mines, Helps)
        }
    }

//    @ParameterizedTest(name = "Тест на количество объектов на игровом поле")
//    @ValueSource(ints = [34])
    @Test
    fun `filled cells count test`() {
        val steps = BattleFieldSteps()
        val battleFieldSize = 8
        val battleField = steps.generateField(battleFieldSize)
        steps.saveToDB(battleField)
        val battlefieldLoad = steps.restoreFromDB(battleFieldSize)
        println(battlefieldLoad.toString())
        steps.checkFilledPoints(battlefieldLoad, 34)
    }

}
