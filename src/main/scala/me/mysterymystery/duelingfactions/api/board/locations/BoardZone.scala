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

  onMouseClicked = (e: MouseEvent) => if (occupied) new Popup(){
    autoFix = true
    autoHide = true

    content.add(new VBox(){
      children = Seq(
        if (peek.isDefined && peek.get.isInstanceOf[SpellOrTrapCard])
          new Button("Activate") {
            styleClass = Seq("summonButton")
            onAction = (e: ActionEvent) => {

              hide()
            }
          }
        else /*if (peek.isDefined && peek.get.isInstanceOf[MonsterCard])*/ {
          new Button("Attack") {
            styleClass = Seq("summonButton")
            onAction = (e: ActionEvent) => {
              //Get attack target on next click then attack
              hide()
            }
          }
        }
      )
    }.delegate
    )
    show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
  }

  /**
    *
    * @return Whether this zone contains a card or not.
    */
  def occupied: Boolean

  def peek: Option[Card]
}
