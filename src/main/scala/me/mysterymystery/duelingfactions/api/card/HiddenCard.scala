package me.mysterymystery.duelingfactions.api.card

import scalafx.scene.image.Image

trait HiddenCard {
  /**
    *
    * @return The back of the card.
    */
  def sprite: Image
}
