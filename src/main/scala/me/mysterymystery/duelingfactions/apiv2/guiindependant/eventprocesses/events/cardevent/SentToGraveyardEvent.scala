package me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.events.cardevent

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections.Graveyard
import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.Event

case class SentToGraveyardEvent(card: Card, graveyard: Graveyard) extends Event
