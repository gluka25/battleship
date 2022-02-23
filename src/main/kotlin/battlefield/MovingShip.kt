package battlefield

import battlefield.repository.entity.ShipEntity

class MovingShip(override var firstPoint: Point3, override var lastPoint: Point3) : Ship(firstPoint, lastPoint) {

    override val icon: Char = '/'
    override fun saveToDb() {
        ShipEntity.new {
            xFirstPoint = firstPoint.x
            yFirstPoint = firstPoint.y
            zFirstPoint = firstPoint.z
            xLastPoint = lastPoint.x
            yLastPoint = lastPoint.y
            zLastPoint = lastPoint.z
            type = true
        }
    }

    private fun getDirection(): Pair<Direction, Int> {
        return if (firstPoint.x - lastPoint.x != 0) {
            if (firstPoint.x < lastPoint.x) {
                Pair(X, 1)
            } else {
                Pair(X, -1)
            }
        } else if (firstPoint.y - lastPoint.y != 0) {
            if (firstPoint.y < lastPoint.y) {
                Pair(Y, 1)
            } else {
                Pair(Y, -1)
            }
        } else {
            if (firstPoint.z < lastPoint.z) {
                Pair(Z, 1)
            } else {
                Pair(Z, -1)
            }
        }
    }


    override fun tryMove(battleField: BattleField): BattleFieldItem {
        if (firstPoint == lastPoint) {
            val setOfPointsToCheck = setOf(
                Point3(firstPoint.x - 1, firstPoint.y, firstPoint.z),
                Point3(lastPoint.x + 1, lastPoint.y, lastPoint.z),
                Point3(firstPoint.x, firstPoint.y - 1, firstPoint.z),
                Point3(lastPoint.x, lastPoint.y + 1, lastPoint.z),
                Point3(firstPoint.x, firstPoint.y, firstPoint.z - 1),
                Point3(firstPoint.x, firstPoint.y, firstPoint.z + 1)
            )
            for (point in setOfPointsToCheck) {
                val goodPoint: Boolean =
                    ifPointFree(battleField, point)
                if (goodPoint) {
                    return MovingShip(point, point)
                }
            }
        } else {
            val (direction, sign) = getDirection()
            if (ifPointFree(battleField, direction.move(lastPoint, sign * 2))) {
                return MovingShip(direction.move(firstPoint, sign * 2), direction.move(lastPoint, sign * 2))
            } else if (ifPointFree(battleField, direction.move(firstPoint, -sign * 2))) {
                return MovingShip(direction.move(lastPoint, -sign * 2), direction.move(firstPoint, -sign * 2))
            }
        }
        return this
    }

//    private fun revertShip() {
//        firstPoint = lastPoint.also { lastPoint = firstPoint }
//    }

    private fun ifPointFree(battleField: BattleField, point: Point3) = try {
        battleField.fieldPoints[point.z][point.y][point.x] == null
    } catch (e: Exception) {
        false
    }

    override fun toString(): String {
        return "battlefield.MovingShip(firstPoint=$firstPoint, lastPoint=$lastPoint)"
    }

    fun toShip(): Ship {
        return Ship(firstPoint, lastPoint)
    }
}
