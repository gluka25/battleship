import org.jetbrains.exposed.sql.insert
import repository.entity.HelpEntity
import repository.entity.Helps
import repository.entity.MineEntity
import repository.entity.Mines

class Help(private val firstPoint: Point3) : BattleFieldItem() {

    override val icon: Char = 'H'

    override fun itemPoints(): Set<Point3> = setOf(firstPoint)
    override fun saveToDb() {
        HelpEntity.new {
            x = firstPoint.x
            y = firstPoint.y
            z = firstPoint.z

        }
    }

    override fun toString(): String {
        return "Help(firstPoint=$firstPoint)"
    }
}