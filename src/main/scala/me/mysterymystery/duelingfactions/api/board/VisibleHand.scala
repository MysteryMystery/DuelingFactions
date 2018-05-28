package me.mysterymystery.duelingfactions.api.board

import me.mysterymystery.duelingfactions.api.card.Card

import scala.collection.mutable.ArrayBuffer

/**
  *
  * @param linkedDeck The deck in which this hand draws from.
  * @param cards contents of the hand.
  */
class VisibleHand(override protected val linkedDeck: Deck, cards: Card*) extends Hand {
  elems ++= cards
}
