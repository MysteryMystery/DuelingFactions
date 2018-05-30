package me.mysterymystery.duelingfactions.api.board.faction.factions

import me.mysterymystery.duelingfactions.api.board.faction.Faction
import me.mysterymystery.duelingfactions.api.player.LifePoints
import scalafx.scene.image.Image

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
  override val lifepoints: LifePoints = new LifePoints(15000)

  /**
    *
    * @return The hero's sprite
    */
  override def sprite: Image = produceSprite("FlameDemon.png")
}
