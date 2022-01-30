import kotlin.random.Random

object BattlefieldItemGenerator {
    fun generateShip(freePoints: Set<Point3>, size: Int): Ship {
        var ship: Ship
        do {
            val firstPoint: Point3 = freePoints.random()
            val sign = if (Random.nextBoolean()) -1 else 1
            ship = Ship(firstPoint, DirectionGenerator.generateDirection().move(firstPoint, sign * size))
        } while (!freePoints.containsAll(ship.itemPoints()))
        return ship
    }

    fun generateMovingShip(freePoints: Set<Point3>, size: Int): MovingShip {
        var ship: MovingShip
        do {
            val firstPoint: Point3 = freePoints.random()
            val sign = if (Random.nextBoolean()) -1 else 1
            ship = MovingShip(firstPoint, DirectionGenerator.generateDirection().move(firstPoint, sign * size))
        } while (!freePoints.containsAll(ship.itemPoints()))
        return ship
    }

    fun generateMine(freePoints: Set<Point3>): Mine {
        return Mine(freePoints.random())
    }

    fun generateHelp(freePoints: Set<Point3>): Help {
        return Help(freePoints.random())
    }
}
