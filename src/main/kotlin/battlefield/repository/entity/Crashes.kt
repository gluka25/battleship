package battlefield.repository.entity

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Crashes : IntIdTable() {
    val x = integer("x")
    val y = integer("y")
    val z = integer("z")
}

class CrashEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CrashEntity>(Crashes)

    var x by Crashes.x
    var y by Crashes.y
    var z by Crashes.z
}
