import io.qameta.allure.Step
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class BattleFieldGeneratorTest {
    @ParameterizedTest(name = "Тест на количество объектов на игровом поле")
    @ValueSource(ints = [34])
    fun `filled cells count test`(count: Int) {
        val battleFieldSize = 8
        val battleField = generateField(battleFieldSize)
        checkFilledPoints(battleField, count)
    }

    @Step("Генерация игрового поля")
    private fun generateField(battleFieldSize: Int): BattleField = BattleFieldGenerator(
        battleFieldSize, 1, 1,
        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1)),
        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1))
    ).generateInfo()

    @Step("Проверка числа заполненных ячеек")
    private fun checkFilledPoints(battleField: BattleField, count: Int) {
        var actualFilledPoints = 0
        for (layer in battleField.fieldPoints)
            for (line in layer)
                for (point in line)
                    if (point != null) actualFilledPoints++
        actualFilledPoints.shouldBeEqualTo(count)
    }
}