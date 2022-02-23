package battlefield

import battlefield.repository.entity.HelpEntity

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

//    fun callForHelp(battleField: BattleField) {
//        //"вызвать подмогу" (создание корабля длиной 2 ячейки)
//        //После взаимодействия ячейка, которую занимал объект "вызвать подмогу" помечается как незанятая.
//        val emptyPoints = mutableSetOf<Point3>()
//
//        battleField.fieldPoints.forEachIndexed { i, layer ->
//            layer.forEachIndexed { j, line ->
//                line.forEachIndexed { k, item ->
//                    if (item == null) emptyPoints.add(Point3(k, j, i))
//                }
//            }
//        }
//
//        for (ep in emptyPoints) {
//            val randomEmptyPoint = emptyPoints.random()
//            var shipSecondPoint: Point3
//            if (isPointOK(battleField, randomEmptyPoint)) {
//                val mbSecondPoints = randomEmptyPoint.generateBlockedPoints3D().toSet()
//                for (p in mbSecondPoints) {
//                    if (isPointOK(battleField, p) && p != randomEmptyPoint) {
//                        shipSecondPoint = p
//                        battleField.addNewItem(Ship(randomEmptyPoint, shipSecondPoint))
//                        battleField.removeItem(this)
//                        return
//                    }
//                }
//            }
//        }
//    }

    fun callForHelp(battleField: BattleField) {
        //"вызвать подмогу" (создание корабля длиной 2 ячейки)
        //После взаимодействия ячейка, которую занимал объект "вызвать подмогу" помечается как незанятая.
        getNewShip(battleField)?.let {
            battleField.addNewItem(it)
            battleField.removeItem(this)
        }
    }

    fun getNewShip(battleField: BattleField): Ship? {
        val emptyPoints = mutableSetOf<Point3>()

        battleField.fieldPoints.forEachIndexed { i, layer ->
            layer.forEachIndexed { j, line ->
                line.forEachIndexed { k, item ->
                    if (item == null) emptyPoints.add(Point3(k, j, i))
                }
            }
        }

        for (ep in emptyPoints) {
            val randomEmptyPoint = emptyPoints.random()
            val shipSecondPoint: Point3
            if (isPointOK(battleField, randomEmptyPoint)) {
                val mbSecondPoints = randomEmptyPoint.generateBlockedPoints3D().toSet()
                for (secondPoint in mbSecondPoints) {
                    if (isPointOK(battleField, secondPoint) && secondPoint != randomEmptyPoint) {
                        return Ship(randomEmptyPoint, secondPoint)
                    }
                }
            }
        }
        return null
    }

    private fun isPointOK(battleField: BattleField, point: Point3): Boolean {
        return try {
            battleField.fieldPoints[point.z][point.y][point.x] == null
        } catch (e: Exception) {
            false
        }
    }

    override fun tryMove(battleField: BattleField): BattleFieldItem {
        return this
    }

    override fun addCrashPoint(point: Point3) {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return "battlefield.Help(firstPoint=$firstPoint)"
    }
}
