package me.mysterymystery.duelingfactions.api.card.cardlist

import me.mysterymystery.duelingfactions.api.board.Board
import me.mysterymystery.duelingfactions.api.card.SpellCard
import scalafx.scene.image.Image

class ExampleSpell extends SpellCard{
  /**
    *
    * @return The image for the card.
    */
  override def sprite: Image = produceSprite("MagicTomb.png")

  /**
    * The effect that the card performs.
    *
    * @return
    */
  override def action: Board => Unit = b => b.myHand.draw()

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
  override def name: String = "Magic Tomb"

  /**
    *
    * @return Action to perform when card is drawn.
    */
  override def onDrawn: Board => Unit = _ => {}

  /**
    *
    * @return Action to perform on the start of the turn.
    */
  override def onStartOfTurn: Board => Unit = _ => {}

  /**
    *
    * @return Action to perform on the end of the turn.
    */
  override def onEndOfTurn: Board => Unit = _ => {}
}
