package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.{Board, GameController}

object Card {
  def empty: Card = new Card {
    /**
      *
      * @return The effect that the card performs.
      */
    override def action: GameController => Unit = _ => ()

    /**
      * Card Description can either be geenral lore, or if the card has an effect, the effect.
      *
      * @return Card description.
      */
    override def cardText: String = ""

    /**
      *
      * @return Name of the card.
      */
    override def name: String = ""

    /**
      *
      * @return The file that holds the sprite for the card.
      */
    override def spriteName: String = "CardBack.png"

    /**
      *
      * @return Action to perform when card is drawn.
      */
    override def onDrawn: GameController => Unit = _ => ()

    /**
      *
      * @return Action to perform on the start of the turn.
      */
    override def onStartOfTurn: GameController => Unit = _ => ()

    /**
      *
      * @return Action to perform on the end of the turn.
      */
    override def onEndOfTurn: GameController => Unit = _ => ()
  }
}

trait Card {

  override def toString: String = s"$name "

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