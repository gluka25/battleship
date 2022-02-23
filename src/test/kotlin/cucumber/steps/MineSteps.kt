package cucumber.steps

import battlefield.BattleField
import battlefield.BattleFieldGenerator
import battlefield.Mine
import io.cucumber.java8.En
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotBeEqualTo
import kotlin.math.abs

class MineSteps : En {
    init {
        lateinit var battleField: BattleField
        lateinit var mines: MutableSet<Mine>

        Given("Battlefield size {int} with {int} mines") { int1: Int, int2: Int ->
            val battleFieldGenerator = BattleFieldGenerator(
                int1, int2, 0,
                listOf(),
                listOf()
            )
            battleField = battleFieldGenerator.generateInfo()
            mines = battleField.findMines()
        }

        When("Move mine") {
            battleField.moveMines()
        }

        Then("battlefield.Mine moves one step") {
            battleField.findMines().shouldHaveSize(1)
            battleField.findMines().shouldNotBeEqualTo(mines)
            abs(
                mines.first().itemPoints().first().x - battleField.findMines().first().itemPoints().first().x
            ).shouldBeEqualTo(1)
        }
    }
}
