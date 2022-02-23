import battlefield.BattleField
import battlefield.BattleFieldGenerator
import battlefield.ShipTypeCount
import battlefield.loadFromDB
import battlefield.saveToDB
import io.qameta.allure.Step
import org.amshove.kluent.shouldBeEqualTo

class BattleFieldSteps {
    @Step("Генерация игрового поля")
    fun generateField(battleFieldSize: Int): BattleField = BattleFieldGenerator(
        battleFieldSize, 1, 1,
        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1)),
        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1))
    ).generateInfo()

    @Step("Сохранение игрового в бд")
    fun saveToDB(battleField: BattleField) {
        battleField.saveToDB()
    }

    @Step("Загрузка игрового из бд")
    fun restoreFromDB(battleFieldSize: Int): BattleField {
        return loadFromDB(battleFieldSize)
    }

    @Step("Проверка числа заполненных ячеек")
    fun checkFilledPoints(battleField: BattleField, count: Int) {
        var actualFilledPoints = 0
        for (layer in battleField.fieldPoints)
            for (line in layer)
                for (point in line)
                    if (point != null) actualFilledPoints++
        actualFilledPoints.shouldBeEqualTo(count)
    }
}
