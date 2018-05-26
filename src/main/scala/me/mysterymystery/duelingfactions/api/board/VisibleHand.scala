package me.mysterymystery.duelingfactions.api.board

import me.mysterymystery.duelingfactions.api.card.Card

import scala.collection.mutable.ArrayBuffer

class VisibleHand(override protected val linkedDeck: Deck, cards: Card*) extends Hand {
  elems ++= cards
}
