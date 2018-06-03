package me.mysterymystery.duelingfactions.api.board.locations

import javafx.scene.{image, input}
import me.mysterymystery.duelingfactions.api.card.Card
import me.mysterymystery.duelingfactions.api.card.cardcollection.Deck
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.scene.image.Image
import scalafx.scene.layout.{HBox, Pane}
import scalafx.scene.text.Text

class DeckZone(private var occupiedWith: Deck = new Deck(Seq()) ) extends BoardZone {
  prefWidth = Config.cardWidth
  styleClass ++= Seq("deckSlot")

  onMouseEntered = (e: input.MouseEvent) =>{
    new Pane(){
      layoutX = e.getSceneX
      layoutY = e.getSceneY
      children = new HBox(){
        children = new Text(occupiedWith.length.toString)
      }
    }
  }

  onMouseExited = (e: input.MouseEvent) => {
  }

  def occupy(deck: Deck): Unit = occupiedWith = deck

  override def occupied: Boolean = true

  override def peek: Option[Card] = None
}
