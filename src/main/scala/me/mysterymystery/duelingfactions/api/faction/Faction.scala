package me.mysterymystery.duelingfactions.api.faction

import me.mysterymystery.duelingfactions.api.player.LifePoints

trait Faction {
  /**
    *
    * @return The name of the hero in play.
    */
  def heroName: String

  /**
    *
    * @return The action which the hero performs such as attacking or gaining armour.
    */
  def act: () => Unit

  /**
    *
    * @return Lifepoints associated with the Faction.
    */
  def lifepoints: LifePoints
}
