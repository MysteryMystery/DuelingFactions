package me.mysterymystery.duelingfactions.api.faction.factions

import me.mysterymystery.duelingfactions.api.faction.Faction
import me.mysterymystery.duelingfactions.api.player.LifePoints

class ExampleFaction extends Faction {
  /**
    *
    * @return The name of the hero in play.
    */
  override def heroName: String = ???

  /**
    *
    * @return The action which the hero performs such as attacking or gaining armour.
    */
  override def act: () => Unit = ???

  /**
    *
    * @return Lifepoints associated with the Faction.
    */
  override def lifepoints: LifePoints = new LifePoints(15000)
}
