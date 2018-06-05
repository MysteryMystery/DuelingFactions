package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.BoardSides.BoardSide
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.{Board, GameController}

object Card {
}

trait Card {

  override def toString: String = s"$name "

  val owner: BoardSide

  /**
    *
    * @return The effect that the card performs.
    */
  def action: GameController => Unit

  /**
    * Card Description can either be geenral lore, or if the card has an effect, the effect.
    * @return Card description.
    */
  def cardText: String

  /**
    *
    * @return Name of the card.
    */
  def name: String

  /**
    *
    * @return The file that holds the sprite for the card.
    */
  def spriteName: String

  //Could add stuff lie onDraw, onBattle etc and then game loop updates and plays all these effects.
  /**
    *
    * @return Action to perform when card is drawn.
    */
  def onDrawn: GameController => Unit

  /**
    *
    * @return Action to perform on the start of the turn.
    */
  def onStartOfTurn: GameController => Unit

  /**
    *
    * @return Action to perform on the end of the turn.
    */
  def onEndOfTurn: GameController => Unit
}