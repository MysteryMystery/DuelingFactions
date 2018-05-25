package me.mysterymystery.duelingfactions.api.card

import me.mysterymystery.duelingfactions.api.card.MonsterTypes.MonsterType

trait MonsterCard extends Card{
  def level: Int
  def monsterType: MonsterType
}
