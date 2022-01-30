package kotest

import BattleFieldGenerator
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.spec.style.FunSpec
import org.amshove.kluent.shouldHaveSize
import org.amshove.kluent.shouldNotBeEqualTo


class MineTestBehavior : BehaviorSpec({
    given("Battlfield size 8 with 1 mine") {
        val battleFieldGenerator = BattleFieldGenerator(
            8, 1, 0,
            listOf(),
            listOf()
        )
        val battleField = battleFieldGenerator.generateInfo()
        val mines = battleField.findMines()
        `when`("Move mine") {
            battleField.moveMines()
            then("Mine moves one step") {
                battleField.findMines().shouldHaveSize(1)
                battleField.findMines().shouldNotBeEqualTo(mines)
            }
        }
    }
})

class MineTestFeature : FeatureSpec({
    feature("Mines") {
        scenario("Move Mines") {
            val battleFieldGenerator = BattleFieldGenerator(
                8, 1, 0,
                listOf(),
                listOf()
            )
            val battleField = battleFieldGenerator.generateInfo()
            val mines = battleField.findMines()
            battleField.moveMines()
            battleField.findMines().shouldHaveSize(1)
            battleField.findMines().shouldNotBeEqualTo(mines)
        }
    }
})

class MineTestFun : FunSpec({
    test("Move Mines") {
        val battleFieldGenerator = BattleFieldGenerator(
            8, 1, 0,
            listOf(),
            listOf()
        )
        val battleField = battleFieldGenerator.generateInfo()
        battleField.moveMines()
        val mines = battleField.findMines()
        mines.shouldHaveSize(1)

    }
})

