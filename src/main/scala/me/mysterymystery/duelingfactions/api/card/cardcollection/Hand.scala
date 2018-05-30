package me.mysterymystery.duelingfactions.api.card.cardcollection

import me.mysterymystery.duelingfactions.api.card.Card

import scala.collection.mutable.ArrayBuffer

/**
  *
  */
private[card] trait Hand extends Iterable[Card] with Seq[Card]{
  protected val linkedDeck: Deck
  protected val elems: ArrayBuffer[Card] = ArrayBuffer[Card]()

  def += (card: Card): Unit = elems += card

  def -= (card: Card): Unit = elems -= card

  def draw(numberToDraw: Int = 1): Unit = elems ++= linkedDeck.draw(numberToDraw)

  override def length: Int = elems.length

  override def apply(idx: Int): Card = elems apply idx

  override def iterator: Iterator[Card] = elems.iterator
}
