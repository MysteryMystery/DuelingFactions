package me.mysterymystery.duelingfactions.api.card

trait TrapCard extends SpellOrTrap {
  /**
    * Trap cards cannot be played the turn they are set.
    * @return Whether the card has been played that turn.
    */
  def playedThisTurn: Boolean
}
