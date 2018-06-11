package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

import enums.MonsterPositions.{MonsterPosition, Attack, Defense}
import enums.MonsterTypes.MonsterType

@SerialVersionUID(123L)
trait MonsterCard extends Card {
  private var _position = Attack

  override def toString: String = super.toString + s"[$level / $attack / $defense]"

  /**
    *
    * @return Level of the monster
    */
  def level: Int

  /**
    *
    * @return Attack points of the monster
    */
  def attack: Int

  /**
    *
    * @return Defense points of the monster
    */
  def defense: Int

  /**
    *
    * @return Monster Type
    */
  def monsterType: MonsterType

  /**
    *
    * @return Position of the monster
    */
  def position: MonsterPosition = _position

  /**
    *
    * @param position Position for the monster to undertake
    */
  def position_=(position: MonsterPosition): Unit = _position = position
}
