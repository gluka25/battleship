package battlefield.configuration

import org.jetbrains.exposed.sql.Database
import java.sql.DriverManager

object DatabaseSettings {
    val embedded by lazy {
        Database.connect("jdbc:sqlite:data/battleship.db", "org.sqlite.JDBC")
    }

    val inmemory by lazy {
        Database.connect({
            DriverManager.getConnection("")
        })
    }
}
