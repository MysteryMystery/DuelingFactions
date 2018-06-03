package me.mysterymystery.duelingfactions.apiv2.guiindependant.exception

/**
  * Thrown when the a card cannot be found on the board.
  * @param message The message to be output.
  * @param cause
  */
final case class CardNotFoundException (private val message: String = "Card not present!",
private val cause: Throwable = None.orNull)
extends Exception(message, cause)
