abstract class BattleFieldItem {

    abstract val icon: Char

    abstract fun itemPoints(): Set<Point3>

    open fun blockedPoints(): Set<Point3> = itemPoints().flatMap { it.generateBlockedPoints3D() }.toSet()

    operator fun contains(other: BattleFieldItem): Boolean {
        return other.blockedPoints().intersect(itemPoints()).isNotEmpty()
    }
}