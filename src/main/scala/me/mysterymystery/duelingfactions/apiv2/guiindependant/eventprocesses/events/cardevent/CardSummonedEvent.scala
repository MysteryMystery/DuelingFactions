package me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.events.cardevent

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.Event

class CardSummonedEvent (card: Card) extends Event

object CardSummonedEvent {
  def apply(card: Card): CardSummonedEvent = new CardSummonedEvent(card)
}