import org.jetbrains.exposed.sql.insert
import repository.entity.ShipEntity
import repository.entity.Ships

class MovingShip(firstPoint: Point3, lastPoint: Point3) : Ship(firstPoint, lastPoint) {
    override val icon: Char = '/'
    override fun saveToDb() {
        ShipEntity.new {
            xFirstPoint = firstPoint.x
            yFirstPoint = firstPoint.y
            zFirstPoint = firstPoint.z
            xLastPoint = lastPoint.x
            yLastPoint = lastPoint.y
            zLastPoint = lastPoint.z
            type = true
        }
    }
}