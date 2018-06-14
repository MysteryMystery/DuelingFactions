package me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.events.game

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.MonsterCard
import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.Event

final case class AttackEvent (attacker: MonsterCard, defender: MonsterCard) extends Event
