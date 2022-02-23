package battlefield

import battlefield.configuration.DatabaseSettings
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import battlefield.repository.entity.HelpEntity
import battlefield.repository.entity.MineEntity
import battlefield.repository.entity.ShipEntity
import battlefield.repository.entity.Ships

//@Component
class ItemsRepository : IItemsRepository {
    override fun loadFromDB(): List<BattleFieldItem> {
        DatabaseSettings.embedded
        val items = mutableListOf<BattleFieldItem>()
        transaction {
            addLogger(StdOutSqlLogger)
            for (mine in MineEntity.all()) {
                items.add(Mine(Point3(mine.x, mine.y, mine.z)))
            }
            for (help in HelpEntity.all()) {
                items.add(Help(Point3(help.x, help.y, help.z)))
            }
            for (ship in ShipEntity.find { Ships.type eq false }) {
                items.add(
                    Ship(
                        Point3(ship.xFirstPoint, ship.yFirstPoint, ship.zFirstPoint),
                        Point3(ship.xLastPoint, ship.yLastPoint, ship.zLastPoint)
                    )
                )
            }
            for (ship in ShipEntity.find { Ships.type eq true }) {
                items.add(
                    MovingShip(
                        Point3(ship.xFirstPoint, ship.yFirstPoint, ship.zFirstPoint),
                        Point3(ship.xLastPoint, ship.yLastPoint, ship.zLastPoint)
                    )
                )
            }
        }
        return items
    }
}