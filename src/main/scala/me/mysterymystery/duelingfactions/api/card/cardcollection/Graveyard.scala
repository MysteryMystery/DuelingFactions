package me.mysterymystery.duelingfactions.api.card.cardcollection

import me.mysterymystery.duelingfactions.api.card.Card

import scala.collection.mutable.ArrayBuffer

class Graveyard(seq: Card*) extends Iterable[Card] with Seq[Card] {
  private val elems = ArrayBuffer[Card]()
  elems ++= seq

  override def iterator: Iterator[Card] = elems.iterator

  override def length: Int = elems.length

  override def apply(idx: Int): Card = elems.apply(idx)

  def += (card: Card): Unit = elems += card

  def ++= (graveyard: Graveyard): Unit = elems ++= graveyard

  def -= (card: Card): Unit = elems -= card

  def --= (graveyard: Graveyard): Unit = elems --= graveyard
}