package me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
  * Stack of cards. A literal stack. Although not really implemented as one i suppose.
 *
  * @param cards Cards in which the deck contains.
  */
class Deck (cards: Seq[Card]) extends Iterable[Card] with Seq[Card]{
  protected var elems: ArrayBuffer[Card] = ArrayBuffer[Card]()
  elems ++= cards
  elems = Random.shuffle(elems)

  /**
    * Shuffles the deck.
    * @return The shuffled deck.
    */
  def shuffle: Deck = new Deck(Random.shuffle(elems))

  /**
    * Takes a card from the deck.
    * @return The card drawn.
    */
  def draw: Card = {
    val toDraw = elems.last
    elems = elems.dropRight(1)
    toDraw
  }

  /**
    * Takes cards from the deck.
    * @return The cards drawn.
    */
  def draw(numberOfCards: Int): Seq[Card] = {
    var tr = ArrayBuffer[Card]()
    for (i <- 0 to numberOfCards){
      tr += elems.last
      elems = elems.dropRight(1)
    }
    tr
  }

  def += (card: Card): Unit = elems += card

  def -= (card: Card): Unit = elems -= card

  override def length: Int = elems.length

  override def apply(idx: Int): Card = elems apply idx

  override def iterator: Iterator[Card] = elems.iterator
}
