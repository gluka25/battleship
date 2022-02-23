package battlefield

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertTrue


@ExtendWith(SpringExtension::class)
@SpringBootTest
class SpringTest {

    @Test
    // пример как можно замокать игровое поле, получаемое из бд
    fun mockDBTest() {
        val size = 8
        val mines = listOf(Mine(Point3(0, 0, 0)))
        val ships = listOf(Ship(Point3(2, 2, 2), Point3(2, 2, 2)))
        val movingShips = listOf(MovingShip(Point3(3, 3, 3), Point3(3, 3, 3)))
        val items: List<BattleFieldItem> = mines + ships + movingShips

//        Mockito.`when`(itemsRepo.battlefield.loadFromDB()).thenReturn(items)
        val mockk = mock<ItemsRepository> {
            on { loadFromDB() } doReturn items
        }

        val generator = BattleFieldGenerator(
            8, 1, 0, listOf(ShipTypeCount(1, 1)),
            listOf(ShipTypeCount(1, 1))
        )
        val battlefield = generator.generateBattlefield(mockk, size)

        assertTrue(battlefield.fieldPoints[0][0][0] is Mine)
    }

    @Test
    fun callForHelpTest() {
        // В mock-реализации всегда размещать корабль в координатах (0,0,0) и (0,0,1)

        val size = 8
        val helps = listOf(Help(Point3(6, 6, 6)))
        val items: List<BattleFieldItem> = helps

        val mockk = mock<ItemsRepository> {
            on { loadFromDB() } doReturn items
        }

        val generator = BattleFieldGenerator(
            8, 0, 1, listOf(ShipTypeCount(1, 1)),
            listOf(ShipTypeCount(1, 1))
        )
        val battlefield = generator.generateBattlefield(mockk, size)

        val spyList = Mockito.spy<Help>(helps[0])
        Mockito.doReturn(Ship(Point3(0, 0, 0), Point3(0, 0, 1))).`when`(spyList).getNewShip(battlefield)
        spyList.callForHelp(battlefield)

        assertTrue(battlefield.fieldPoints[0][0][0] is Ship)
        assertTrue(battlefield.fieldPoints[1][0][0] is Ship)
    }

}