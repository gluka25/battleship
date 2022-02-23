package battlefield

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class AppConfig {

    @Bean
    open fun battleFieldService(): BattleFieldGenerator {
        return BattleFieldGenerator(
            8, 1, 0, listOf(ShipTypeCount(1, 1)),
            listOf(ShipTypeCount(1, 1))
        )
    }
}