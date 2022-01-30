import repository.entity.ShipEntity

open class Ship(open val firstPoint: Point3, open val lastPoint: Point3 = firstPoint) : BattleFieldItem() {
    val crashPoints: MutableSet<Point3> = mutableSetOf()

    override val icon: Char = 'S'

    override fun itemPoints(): Set<Point3> = setOf(firstPoint, lastPoint) + firstPoint.connectionPoints(lastPoint)

    override fun saveToDb() {
        ShipEntity.new {
            xFirstPoint = firstPoint.x
            yFirstPoint = firstPoint.y
            zFirstPoint = firstPoint.z
            xLastPoint = lastPoint.x
            yLastPoint = lastPoint.y
            zLastPoint = lastPoint.z
            type = false
        }
    }

    override fun tryMove(battleField: BattleField): BattleFieldItem {
        return this
    }

    override fun addCrashPoint(point: Point3) {
        crashPoints.add(point)
    }

    override fun toString(): String {
        return "Ship(firstPoint=$firstPoint, lastPoint=$lastPoint)"
    }

}
