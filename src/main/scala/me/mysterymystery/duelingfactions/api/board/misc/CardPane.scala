package me.mysterymystery.duelingfactions.api.board.misc

import me.mysterymystery.duelingfactions.api.card.Card
import scalafx.scene.layout.Pane

class CardPane(private var _card: Card = null) extends Pane {
  def card: Card = _card
  def card_=(card: Card) = _card = card
}
