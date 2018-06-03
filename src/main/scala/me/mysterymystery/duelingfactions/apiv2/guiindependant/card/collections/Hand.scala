package me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import scalafx.beans.value.ObservableValue

import scala.collection.mutable.ArrayBuffer

class Hand (protected val linkedDeck: Deck) extends Iterable[Card] with Seq[Card]{
  protected val elems: ArrayBuffer[Card] = ArrayBuffer[Card]()

  def += (card: Card): Unit = elems += card

  def -= (card: Card): Unit = elems -= card

  def draw(numberToDraw: Int = 1): Seq[Card] = {
    val x = linkedDeck.draw(numberToDraw)
    elems ++= x
    x
  }

  def draw: Card = {
    val x = linkedDeck.draw
    elems += x
    x
  }

  override def length: Int = elems.length

  override def apply(idx: Int): Card = elems apply idx

  override def iterator: Iterator[Card] = elems.iterator
}