package cucumber.steps

import BattleField
import BattleFieldGenerator
import Mine
import MovingShip
import Point3
import Ship
import ShipTypeCount
import io.cucumber.java8.En
import io.kotest.matchers.collections.shouldHaveAtLeastSize
import moveItems
import org.amshove.kluent.`should be in`
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBeEqualTo
import org.amshove.kluent.shouldNotBeInstanceOf

class ShipSteps : En {
    init {
        lateinit var battleField: BattleField
        lateinit var ships: MutableSet<Ship>
        lateinit var mines: MutableSet<Mine>

        Given(
            "Battlefield size {int} with {int} mines and {int} moving ships"
        ) { int1: Int, int2: Int, int3: Int ->
            val battleFieldGenerator = BattleFieldGenerator(
                int1, 0, 0,
                listOf(),
                listOf()
            )
            battleField = battleFieldGenerator.generateInfo()
            val mshPoint = Point3((0..7).random(), (0..7).random(), (0..7).random())
            battleField.addNewItem(MovingShip(mshPoint, mshPoint))
            ships = battleField.findShips()
        }

        Given(
            "Battlefield size {int} with {int} mines and {int} ships"
        ) { int1: Int, int2: Int, int3: Int ->
            val battleFieldGenerator = BattleFieldGenerator(
                int1, 0, 0,
                listOf(),
                listOf()
            )
            battleField = battleFieldGenerator.generateInfo()
            val mshPoint = Point3((0..7).random(), (0..7).random(), (0..7).random())
            battleField.addNewItem(Ship(mshPoint, mshPoint))
            ships = battleField.findShips()
        }

        Given(
            "Battlefield size {int} with {int} mines near {int} moving ships"
        ) { int1: Int, int2: Int, int3: Int ->
            val battleFieldGenerator = BattleFieldGenerator(
                int1, 0, 0,
                listOf(),
                listOf()
            )
            battleField = battleFieldGenerator.generateInfo()
            battleField.addNewItem(Mine(Point3(4, 1, 0)))
            battleField.addNewItem(MovingShip(Point3(0, 0, 0), Point3(3, 0, 0)))
            mines = battleField.findMines()
            ships = battleField.findShips()
        }

        Given(
            "Battlefield size {int} with {int} mines near {int} ships"
        ) { int1: Int, int2: Int, int3: Int ->
            val battleFieldGenerator = BattleFieldGenerator(
                int1, 0, 0,
                listOf(),
                listOf()
            )
            battleField = battleFieldGenerator.generateInfo()
            battleField.addNewItem(Mine(Point3(4, 1, 0)))
            battleField.addNewItem(Ship(Point3(0, 0, 0), Point3(3, 0, 0)))
            mines = battleField.findMines()
            ships = battleField.findShips()
        }

        When("Move moving ship") {
            battleField.moveShips()
        }

        When("Move battleField items") {
            battleField.moveItems()
        }

        Then("Moving ships moves one step") {
            ships.filterIsInstance<MovingShip>().toSet()
                .shouldNotBeEqualTo(battleField.findShips().filterIsInstance<MovingShip>().toSet())
        }

        Then("Ships do not move") {
            for (ship in ships) {
                ship.`should be in`(battleField.findShips().filterNot { it is MovingShip })
                ship.itemPoints().shouldBeEqualTo(battleField.findShips().first().itemPoints())
            }
        }

        Then("Ships are crashed") {
            for (ship in battleField.findShips()) {
                ship.crashPoints.shouldHaveAtLeastSize(1)
            }
        }

        Then("Moving ship becomes ship") {
            battleField.findShips().forEach { it.shouldBeInstanceOf<Ship>() }
            battleField.findShips().forEach { it.shouldNotBeInstanceOf<MovingShip>() }
        }
    }
}
