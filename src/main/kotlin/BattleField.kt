import com.googlecode.lanterna.terminal.Terminal

class BattleField(battleFieldItems: Collection<BattleFieldItem>, size: Int) : IBattleField {
    private val grid: MutableList<MutableList<MutableList<BattleFieldItem?>>> = mutableListOf()

    init {

        repeat(size) {
            val layer = mutableListOf<MutableList<BattleFieldItem?>>()
            repeat(size) {
                val line = mutableListOf<BattleFieldItem?>()
                repeat(size) {
                    line.add(null)
                }
                layer.add(line)
            }
            grid.add(layer)
        }
        for (battleFieldItem in battleFieldItems) {
            for (point in battleFieldItem.itemPoints()) {
                grid[point.z][point.y][point.x] = battleFieldItem
            }
        }
    }

    override fun toString(): String {
        return grid.joinToString(separator = "\n\n") { layer ->
            layer.joinToString(separator = "\n") { line ->
                line.map {
                    it?.icon ?: '_'
                }.joinToString(separator = " ")
            }
        }
    }
}

// реализовать послойную визуализацию (в консоли) как метод расширения класса игрового поля;
fun BattleField.print(terminal: Terminal) {
    val grid = toString()
    grid.forEach { terminal.putCharacter(it) }
    terminal.flush()
}