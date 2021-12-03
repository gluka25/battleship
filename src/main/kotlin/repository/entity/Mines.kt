package repository.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Mines : IntIdTable() {
    val x = integer("x")
    val y = integer("y")
    val z = integer("z")
}

class MineEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<MineEntity>(Mines)

    var x by Mines.x
    var y by Mines.y
    var z by Mines.z
}