package me.mysterymystery.duelingfactions.api.card

trait TrapCard extends SpellOrTrap {
  def playedThisTurn: Boolean
}
