import org.jetbrains.exposed.sql.insert
import repository.entity.ShipEntity
import repository.entity.Ships

open class Ship(val firstPoint: Point3, val lastPoint: Point3 = firstPoint) : BattleFieldItem() {

    override val icon: Char = 'S'

    override fun itemPoints(): Set<Point3> = setOf(firstPoint, lastPoint) + firstPoint.connectionPoints(lastPoint)

    override fun saveToDb() {
        ShipEntity.new {
            xFirstPoint = firstPoint.x
            yFirstPoint = firstPoint.y
            zFirstPoint = firstPoint.z
            xLastPoint = lastPoint.x
            yLastPoint = lastPoint.y
            zLastPoint = lastPoint.z
            type = false
        }
    }

    override fun toString(): String {
        return "Ship(firstPoint=$firstPoint, lastPoint=$lastPoint)"
    }


}
