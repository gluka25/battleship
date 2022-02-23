package battlefield

import battlefield.repository.entity.CrashEntity

class CrashPoint(private val firstPoint: Point3) : BattleFieldItem() {
    override val icon: Char = 'X'

    override fun itemPoints(): Set<Point3> = setOf(firstPoint)

    override fun toString(): String {
        return "Crash(firstPoint=$firstPoint)"
    }

    override fun saveToDb() {
        CrashEntity.new {
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
}
