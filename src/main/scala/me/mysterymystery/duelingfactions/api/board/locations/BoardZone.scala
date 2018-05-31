package me.mysterymystery.duelingfactions.api.board.locations

import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.input
import javafx.scene.input.MouseEvent
import me.mysterymystery.duelingfactions.DuelingFactions
import me.mysterymystery.duelingfactions.api.card.{Card, MonsterCard, SpellCard, SpellOrTrapCard}
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.geometry.Pos
import scalafx.scene.control.Button
import scalafx.scene.layout.{Pane, StackPane, VBox}
import scalafx.stage.Popup

trait BoardZone extends StackPane{
  alignment = Pos.Center
  prefHeight = Config.cardHeight
  prefWidth = Config.cardHeight

  /**
    *
    * @return Whether this zone contains a card or not.
    */
  def occupied: Boolean

  def peek: Option[Card]
}
