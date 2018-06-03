package me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import scalafx.beans.value.ObservableValue

import scala.collection.mutable.ArrayBuffer

class Hand (protected val linkedDeck: Deck) extends Iterable[Card] with Seq[Card]{
  protected val elems: ArrayBuffer[Card] = ArrayBuffer[Card]()

  def += (card: Card): Unit = elems += card

  def -= (card: Card): Unit = elems -= card

  def draw(numberToDraw: Int = 1): Unit = elems ++= linkedDeck.draw(numberToDraw)

  override def length: Int = elems.length

  override def apply(idx: Int): Card = elems apply idx

  override def iterator: Iterator[Card] = elems.iterator
}