package me.mysterymystery.duelingfactions.apiv2.guiindependant.exception

/**
  * Thrown when the board is full so a card cannot be placed.
  * @param message The message to be output.
  * @param cause
  */
final case class BoardFullException (private val message: String = "Board is full!",
                                     private val cause: Throwable = None.orNull)
  extends Exception(message, cause)
