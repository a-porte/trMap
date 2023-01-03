package treasureMap.rules

trait LootDigger {
  def amountDiggedAtATime = 1 // may be overriden

  def digLoot() : LootDigger

}
