package me.mysterymystery.duelingfactions.api.board

import me.mysterymystery.duelingfactions.api.card.{Card, HiddenCard}

import scala.collection.mutable.ArrayBuffer

/**
  * The opponents deck.
  * @param linkedDeck The deck in which this hand draws from.
  * @param cards contents of the hand.
  */
class HiddenHand (override protected val linkedDeck: Deck, cards: Card*) extends Hand {
  elems ++= cards
}
