package me.mysterymystery.duelingfactions.api.card.cardcollection

import me.mysterymystery.duelingfactions.api.card.Card
import scalafx.scene.layout.FlowPane

/**
  *
  * @param linkedDeck The deck in which this hand draws from.
  * @param cards contents of the hand.
  */
class VisibleHand(override protected val linkedDeck: Deck, cards: Card*) extends FlowPane with Hand {
  elems ++= cards


}
