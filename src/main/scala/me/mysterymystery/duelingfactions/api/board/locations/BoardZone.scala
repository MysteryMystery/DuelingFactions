package me.mysterymystery.duelingfactions.api.board.locations

import javafx.event.EventHandler
import javafx.scene.input
import me.mysterymystery.duelingfactions.api.card.Card
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.geometry.Pos
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{Pane, StackPane}

trait BoardZone extends StackPane{
  alignment = Pos.Center
  prefHeight = Config.cardHeight
  prefWidth = Config.cardHeight

  def occupied: Boolean
}
