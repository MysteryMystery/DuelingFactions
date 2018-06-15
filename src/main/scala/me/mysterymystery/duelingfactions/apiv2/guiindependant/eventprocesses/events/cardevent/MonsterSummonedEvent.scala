package me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.events.cardevent

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.MonsterCard

final case class MonsterSummonedEvent (card: MonsterCard) extends CardSummonedEvent (card)
