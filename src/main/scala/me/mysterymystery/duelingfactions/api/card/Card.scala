package me.mysterymystery.duelingfactions.api.card

import me.mysterymystery.duelingfactions.api.board.Board
import me.mysterymystery.duelingfactions.api.config.Config
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.Pane

trait Card {
  /**
    * Gets the sprite from resources.
    * @return sprite for the card.
    */
  protected def produceSprite(name: String): Image = new Image(new javafx.scene.image.Image(getClass.getResourceAsStream(s"/sprites/$name")))

  /**
    *
    * @return The image for the card.
    */
  def sprite: Image

  /**
    *
    * @return Image view
    */
  def imageView: ImageView = new ImageView(sprite) {fitWidth = Config.cardWidth; fitHeight = Config.cardHeight}

  /**
    * The effect that the card performs.
    * @return
    */
  def action: Board => Unit

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

  //Could add stuff lie onDraw, onBattle etc and then game loop updates and plays all these effects.
  /**
    *
    * @return Action to perform when card is drawn.
    */
  def onDrawn: Board => Unit

  /**
    *
    * @return Action to perform on the start of the turn.
    */
  def onStartOfTurn: Board => Unit

  /**
    *
    * @return Action to perform on the end of the turn.
    */
  def onEndOfTurn: Board => Unit
}
