package repository.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Helps : IntIdTable() {
    val x = integer("x")
    val y = integer("y")
    val z = integer("z")
}

class HelpEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HelpEntity>(Helps)

    var x by Helps.x
    var y by Helps.y
    var z by Helps.z
}
