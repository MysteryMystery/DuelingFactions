package me.mysterymystery.duelingfactions.api.card

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.Pane

trait Card {
  /**
    * Gets the sprite from resources.
    * @return sprite for the card.
    */
  protected def produceSprite(name: String): Image = new Image(new javafx.scene.image.Image(getClass.getResource(s"/sprites/$name").toExternalForm))

  /**
    *
    * @return The image for the card.
    */
  def sprite: Image

  /**
    * The effect that the card performs.
    * @return
    */
  def action: () => Unit

  /**
    * Card Description can either be geenral lore, or if the card has an effect, the effect.
    * @return Card description.
    */
  def cardText: String

  /**
    *
    * @return Name of the card.
    */
  def name: String
}
