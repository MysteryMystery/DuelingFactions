package me.mysterymystery.duelingfactions.api.card

import scalafx.scene.image.Image

@Deprecated
trait HiddenCard {
  protected var cards: Seq[Card]

  /**
    *
    * @return The back of the card.
    */
  def sprite: Image
}
