open class Ship(private val firstPoint: Point3, private val lastPoint: Point3 = firstPoint) : BattleFieldItem() {

    override val icon: Char = 'S'

    override fun itemPoints(): Set<Point3> = setOf(firstPoint, lastPoint) + firstPoint.connectionPoints(lastPoint)

    override fun toString(): String {
        return "Ship(firstPoint=$firstPoint, lastPoint=$lastPoint)"
    }
}
