package battlefield

interface IBattleFieldGenerator {
    val itemsRepository: IItemsRepository
    fun loadFromDBandBuild(itemsRepository: IItemsRepository)
}