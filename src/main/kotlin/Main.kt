import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.swing.ScrollingSwingTerminal
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame
import java.awt.Window
import javax.swing.JScrollPane


fun main(args: Array<String>) {
//    Создать заготовку для заполнения поля 8х8х8 случайно расположенными
//    "кораблями" (2 по 4, 8 по 2, 8 по 1), 1 "миной" и 1 "вызвать подмогу".

    val battleFieldGenerator = BattleFieldGenerator(
        8, 1, 1,
        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1)),
        listOf(ShipTypeCount(1, 4), ShipTypeCount(2, 4), ShipTypeCount(4, 1))
    )
    val battleField = battleFieldGenerator.generateInfo()

    val defaultTerminalFactory = DefaultTerminalFactory()
    val terminal = defaultTerminalFactory.createTerminal()
//    terminal.setSize(1000, 1000)
    battleField.print(terminal)

}