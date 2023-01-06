package treasureMap.rules

import treasureMap.coordinates.Coordinates

@deprecatedInheritance
trait LootDigger {

  
  def amountDiggedAtATime = 1 // may be overriden

  def digLoot(hasLootFun : Coordinates => Boolean = _ => false) : LootDigger 
  

}
