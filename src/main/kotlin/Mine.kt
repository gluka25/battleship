import repository.entity.MineEntity

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

    override fun tryMove(battleField: BattleField): BattleFieldItem {
        //взорвать другие battlefielditems
        //если вокруг мины есть корабли, то соответствующая точка корабля становится crashpoint
        val setOfPointsToCheckShips = firstPoint.generateBlockedPoints3D().toSet()
        for (point in setOfPointsToCheckShips) {
            if (ifPointOKToCrashShips(battleField, point)) {
                val item = battleField.fieldPoints[point.z][point.y][point.x]
                item?.addCrashPoint(point)
                //movingship становится ship
                if (item is MovingShip) {
                    val sh = item.toShip()
                    sh.addCrashPoint(point)
                    battleField.refresh(item, sh)
                }
                //в точку на поле помещается crashpoint
                battleField.addCrashPoint(point)
            }
        }

        //получить новое положение мины
        val setOfPointsToCheckMoving = setOf(
            Point3(firstPoint.x - 1, firstPoint.y, firstPoint.z),
            Point3(firstPoint.x + 1, firstPoint.y, firstPoint.z),
            Point3(firstPoint.x, firstPoint.y - 1, firstPoint.z),
            Point3(firstPoint.x, firstPoint.y + 1, firstPoint.z),
            Point3(firstPoint.x, firstPoint.y, firstPoint.z - 1),
            Point3(firstPoint.x, firstPoint.y, firstPoint.z + 1)
        )
        for (point in setOfPointsToCheckMoving) {
            val goodPoint: Boolean = ifPointOKToMove(battleField, point)
            if (goodPoint) {
                return Mine(point)
            }
        }
        return this
    }

    override fun addCrashPoint(point: Point3) {
        TODO("Not yet implemented")
    }

    private fun ifPointOKToMove(battleField: BattleField, point: Point3): Boolean {
        return try {
            battleField.fieldPoints[point.z][point.y][point.x] == null
        } catch (e: Exception) {
            false
        }
    }

    private fun ifPointOKToCrashShips(battleField: BattleField, point: Point3): Boolean {
        return try {
            battleField.fieldPoints[point.z][point.y][point.x] is Ship
        } catch (e: Exception) {
            false
        }
    }

    override fun toString(): String {
        return "Mine(firstPoint=$firstPoint)"
    }

}
