package me.mysterymystery.duelingfactions.api.board

import me.mysterymystery.duelingfactions.api.board
import me.mysterymystery.duelingfactions.api.card.{Card, TrapCard}
import scalafx.scene.image.Image

import scala.collection.generic.{CanBuildFrom, GenericTraversableTemplate, SeqFactory}
import scala.collection.{SeqLike, TraversableLike, mutable}
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

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