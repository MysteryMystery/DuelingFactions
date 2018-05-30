package me.mysterymystery.duelingfactions.api.card.cardcollection

import me.mysterymystery.duelingfactions.api.card.Card

/**
  *
  * @param linkedDeck The deck in which this hand draws from.
  * @param cards contents of the hand.
  */
class VisibleHand(override protected val linkedDeck: Deck, cards: Card*) extends Hand {
  elems ++= cards
}
