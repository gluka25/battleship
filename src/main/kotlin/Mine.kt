import org.jetbrains.exposed.sql.insert
import repository.entity.MineEntity
import repository.entity.Mines

class Mine(private val firstPoint: Point3) : BattleFieldItem() {

    override val icon: Char = 'M'

    override fun itemPoints(): Set<Point3> = setOf(firstPoint)
    override fun saveToDb() {
        MineEntity.new {
            x = firstPoint.x
            y = firstPoint.y
            z = firstPoint.z
        }
    }

    override fun toString(): String {
        return "Mine(firstPoint=$firstPoint)"
    }
}