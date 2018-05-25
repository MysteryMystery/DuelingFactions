package me.mysterymystery.duelingfactions.api.card

import scalafx.scene.image.Image

private[api] trait Card {
  /**
    * Gets the sprite from resources.
    * @return sprite for the card.
    */
  protected def produceSprite(name: String): String = getClass.getResource(s"/sprites/$name").toExternalForm

  def sprite: Image
  def action: () => Unit
  def cardText: String

  def name: String
}
