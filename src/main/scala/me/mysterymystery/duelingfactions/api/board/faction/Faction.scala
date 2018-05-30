package me.mysterymystery.duelingfactions.api.board.faction

import me.mysterymystery.duelingfactions.api.player.LifePoints
import scalafx.scene.image.Image

trait Faction {
  protected def produceSprite(name: String): Image = new Image(new javafx.scene.image.Image(getClass.getResource(s"/sprites/factions/$name").toExternalForm))

  /**
    *
    * @return The name of the hero in play.
    */
  def heroName: String

  /**
    *
    * @return The hero's sprite
    */
  def sprite: Image

  /**
    *
    * @return The action which the hero performs such as attacking or gaining armour.
    */
  def act: () => Unit

  /**
    *
    * @return Lifepoints associated with the Faction.
    */
  val lifepoints: LifePoints
}
