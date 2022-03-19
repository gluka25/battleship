package cucumber.steps

import BattleField
import BattleFieldGenerator
import Mine
import Point3
import Ship
import ShipTypeCount
import io.cucumber.java8.En
import io.kotest.matchers.shouldHave
import io.kotest.matchers.shouldNotHave
import org.amshove.kluent.*
import kotlin.math.abs
import kotlin.math.min

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

        Given("Battlefield size {int} with mine {int} x {int} y {int} z") { int1: Int, int2: Int, int3: Int, int4: Int ->
            val battleFieldGenerator = BattleFieldGenerator(
                int1, 0, 0,
                listOf(),
                listOf()
            )
            battleField = battleFieldGenerator.generateInfo()
            battleField.addNewItem(Mine(Point3(int2, int3, int4)))
            mines = battleField.findMines()
        }

        Given("Add ship") {
            battleField.addNewItem(Ship(Point3(1, 0, 0), Point3(1, 0, 0)))
        }

        When("Move mine") {
            battleField.moveMines()
        }

        Then("Mine moves one step") {
            battleField.findMines().shouldHaveSize(1)
            battleField.findMines().shouldNotBeEqualTo(mines)
            abs(
                mines.first().itemPoints().first().x - battleField.findMines().first().itemPoints().first().x
            ).shouldBeEqualTo(1)
        }

        Then("Mine moves one step by y") {
            battleField.findMines().shouldHaveSize(1)
            battleField.findMines().shouldNotBeEqualTo(mines)
            abs(
                mines.first().itemPoints().first().y - battleField.findMines().first().itemPoints().first().y
            ).shouldBeEqualTo(1)
        }


    }
}
