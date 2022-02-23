package battlefield

data class ShipTypeCount(val size: Int, val count: Int)


class BattleFieldGenerator(
    private val size: Int,
    private val mineCount: Int,
    private val helpCount: Int,
    private val shipsInfo: List<ShipTypeCount>,
    private val movingShipsInfo: List<ShipTypeCount>
) {
    private val freePoints by lazy { generateFreePoints() }
    private val ships by lazy { generateShips() }
    private val movingShips by lazy { generateMovingShips() }
    private val mines by lazy { generateMines() }
    private val helps by lazy { generateHelps() }

    fun generateInfo(): BattleField {
        println("battlefield.BattleField size: $size")
        println("battlefield.BattleField points: $freePoints")
        println("Mines: $mines")
        println("Helps: $helps")
        println("Ships: $ships")
        println("Moving Ships: $movingShips")
        println("Battle Field: M - mine, H - help, S - ship, / - Moving ship")
        val battleField = BattleField(mines + helps + ships + movingShips, size)
        println(battleField.toString())
        return battleField
    }

    private fun generateShips(): List<Ship> {
        val ships = mutableListOf<Ship>()
        for (shipTypeCount in shipsInfo) {
            for (i in 0 until shipTypeCount.count) {
                val ship = BattlefieldItemGenerator.generateShip(freePoints, shipTypeCount.size)
                freePoints.removeAll(ship.blockedPoints())
                ships += ship
            }
        }
        return ships
    }

    private fun generateMovingShips(): List<MovingShip> {
        val ships = mutableListOf<MovingShip>()
        for (shipTypeCount in movingShipsInfo) {
            for (i in 0 until shipTypeCount.count) {
                val ship = BattlefieldItemGenerator.generateMovingShip(freePoints, shipTypeCount.size)
                freePoints.removeAll(ship.blockedPoints())
                ships += ship
            }
        }
        return ships
    }

    private fun generateMines(): List<Mine> {
        val mines = mutableListOf<Mine>()
        repeat(mineCount) {
            val mine = BattlefieldItemGenerator.generateMine(freePoints)
            freePoints.removeAll(mine.blockedPoints())
            mines += mine
        }
        return mines
    }

    private fun generateHelps(): List<Help> {
        val helps = mutableListOf<Help>()
        repeat(helpCount) {
            val help = BattlefieldItemGenerator.generateHelp(freePoints)
            freePoints.removeAll(help.blockedPoints())
            helps += help
        }
        return helps
    }

    private fun generateFreePoints(): MutableSet<Point3> {
        val freePoints = mutableSetOf<Point3>()
        for (x in 0 until size) {
            for (y in 0 until size) {
                for (z in 0 until size) {
                    freePoints.add(Point3(x, y, z))
                }
            }
        }
        return freePoints
    }

    fun generateBattlefield(itemsRepository: ItemsRepository, battlefieldSize: Int): BattleField {
        return BattleField(itemsRepository.loadFromDB(), battlefieldSize)
    }

}
