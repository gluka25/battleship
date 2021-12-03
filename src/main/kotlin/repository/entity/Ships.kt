package repository.entity
import org.jetbrains.exposed.dao.*

object Ships: IntIdTable() {
    val xFirstPoint = integer("x1")
    val yFirstPoint = integer("y1")
    val zFirstPoint = integer("z1")
    val xLastPoint = integer("x2")
    val yLastPoint = integer("y2")
    val zLastPoint = integer("z2")
    var type = bool("type")
}

class ShipEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<ShipEntity>(Ships)

    var xFirstPoint by Ships.xFirstPoint
    var yFirstPoint by Ships.yFirstPoint
    var zFirstPoint by Ships.zFirstPoint
    var xLastPoint by Ships.xLastPoint
    var yLastPoint by Ships.yLastPoint
    var zLastPoint by Ships.zLastPoint
    var type by Ships.type

}