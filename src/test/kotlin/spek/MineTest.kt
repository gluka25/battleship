package spek

import battlefield.BattleField
import battlefield.BattleFieldGenerator
import org.amshove.kluent.shouldHaveSize
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import org.spekframework.spek2.style.specification.describe

class MineSpekTest : Spek({
//    lateinit var battleField: battlefield.BattleField
    describe("Mines") {
        it("battlefield.Mine moves") {
            val battleFieldGenerator = BattleFieldGenerator(
                8, 1, 0,
                listOf(),
                listOf()
            )
            val battleField = battleFieldGenerator.generateInfo()
            battleField.moveMines()
            battleField.findMines().shouldHaveSize(1)
        }
    }

})

class MineGherkinTest : Spek({

    Feature("Mines") {
        Scenario("Moving mines") {
            lateinit var battleField: BattleField
            Given("Battlfield size 8 with 1 mine") {
                val battleFieldGenerator = BattleFieldGenerator(
                    8, 1, 0,
                    listOf(),
                    listOf()
                )
                battleField = battleFieldGenerator.generateInfo()

            }
            When("Move mine") {
                battleField.moveMines()

            }
            Then("battlefield.Mine moves one step") {
                battleField.findMines().shouldHaveSize(1)
            }
        }
    }

})
