package me.mysterymystery.duelingfactions.api.card

/**
  * Defines a trap card.
  */
trait TrapCard extends SpellOrTrapCard {
  /**
    * Trap cards cannot be played the turn they are set.
    * @return Whether the card has been played that turn.
    */
  def playedThisTurn: Boolean
}
