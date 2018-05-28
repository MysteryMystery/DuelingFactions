package me.mysterymystery.duelingfactions.api.board.locations

import javafx.event.EventHandler
import javafx.scene.input
import me.mysterymystery.duelingfactions.api.card.Card
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.geometry.Pos
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{Pane, StackPane}

trait BoardZone extends StackPane{
  alignment = Pos.Center

  protected var occupiedWith: Card = _

  onMouseEntered = (e: input.MouseEvent) => {
    GameScene.cardViewerPictureBox.image = occupiedWith.sprite
    GameScene.descBox.text = occupiedWith.cardText
  }
  onMouseExited = (e: input.MouseEvent) => {
    GameScene.cardViewerPictureBox.image = null
    GameScene.descBox.text = ""
  }
}
