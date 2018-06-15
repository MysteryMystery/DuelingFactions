package me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.events.cardevent

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.SpellOrTrapCard

final case class SpellTrapSetEvent (card: SpellOrTrapCard) extends CardSummonedEvent (card)

