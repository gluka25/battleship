class Mine(private val firstPoint: Point3) : BattleFieldItem() {

    override val icon: Char = 'M'

    override fun itemPoints(): Set<Point3> = setOf(firstPoint)
    override fun toString(): String {
        return "Mine(firstPoint=$firstPoint)"
    }
}