package me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards

import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.{Board, GameController}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.{SpellCard, SpellOrTrapCard}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.BoardSides.MySide

class ExampleSpell extends SpellCard with SpellOrTrapCard{
  /**
    *
    * @return The effect that the card performs.
    */
  override def action: GameController => Unit = gc => gc.boards(MySide).hand.draw(1)

  /**
    * Card Description can either be geenral lore, or if the card has an effect, the effect.
    *
    * @return Card description.
    */
  override def cardText: String = "Draw a card!"

  /**
    *
    * @return Name of the card.
    */
override def name: String = "Magic Tome"

  /**
    *
    * @return The file that holds the sprite for the card.
    */
override def spriteName: String = "MagicTomb.png"

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
