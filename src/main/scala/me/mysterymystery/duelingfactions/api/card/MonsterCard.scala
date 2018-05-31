package me.mysterymystery.duelingfactions.api.card

import me.mysterymystery.duelingfactions.api.card.MonsterTypes.MonsterType

trait MonsterCard extends Card{
  /**
    *
    * @return Level of the card.
    */
  def level: Int

  /**
    *
    * @return Type of the monster.
    */
  def monsterType: MonsterType

  /**
    *
    * @return Attack points for the monster.
    */
  def attackPoints: Int

  /**
    *
    * @return Defense points for the monster.
    */
  def defensePoints: Int
}
