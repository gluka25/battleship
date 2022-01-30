import repository.entity.HelpEntity

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

    override fun tryMove(battleField: BattleField): BattleFieldItem {
        return this
    }

    override fun addCrashPoint(point: Point3) {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "Help(firstPoint=$firstPoint)"
    }
}
