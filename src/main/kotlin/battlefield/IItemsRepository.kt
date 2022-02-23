package battlefield

interface IItemsRepository {
    fun loadFromDB(): List<BattleFieldItem>
}