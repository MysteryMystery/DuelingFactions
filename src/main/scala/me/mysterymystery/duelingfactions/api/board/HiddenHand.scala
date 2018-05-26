package me.mysterymystery.duelingfactions.api.board

import me.mysterymystery.duelingfactions.api.card.{Card, HiddenCard}

import scala.collection.mutable.ArrayBuffer

class HiddenHand (override protected val linkedDeck: Deck, cards: Card*) extends Hand {
  elems ++= cards
}
