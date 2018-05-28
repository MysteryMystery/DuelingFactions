package me.mysterymystery.duelingfactions

import javafx.event
import javafx.event.EventHandler
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox

package object scene {
  val closeButton: Button = new Button("X") {
    styleClass ++= Seq(
      "closeButton"
    )

    maxWidth = 30
    maxHeight = 30
    minWidth = 30
    minHeight = 30

    onAction = (e: event.ActionEvent) => {
      DuelingFactions.close
    }
  }

  val windowControls: HBox = new HBox(){
    alignment = Pos.TopRight
    children = closeButton
  }
}
