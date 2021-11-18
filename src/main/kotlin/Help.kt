class Help(private val firstPoint: Point3) : BattleFieldItem() {

    override val icon: Char = 'H'

    override fun itemPoints(): Set<Point3> = setOf(firstPoint)
    override fun toString(): String {
        return "Help(firstPoint=$firstPoint)"
    }
}