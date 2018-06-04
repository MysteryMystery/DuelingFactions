package me.mysterymystery.duelingfactions

import javafx.event
import javafx.event.EventHandler
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import me.mysterymystery.duelingfactions.apiv2.guidependant.card._
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.scene.control.{Button, TextArea}
import scalafx.scene.image.ImageView
import scalafx.scene.layout.{HBox, Priority, VBox}

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

  private[scene] val descBox: TextArea = new TextArea {
    wrapText = true
    editable = false
    prefHeight = Double.MaxValue
  }

  private[scene] val cardViewerPictureBox: ImageView = new ImageView{
    //image = new Image(new javafx.scene.image.Image())
    fitWidth = 180
    fitHeight = 240
  }

  /**
    * Card discriptions on card hover. Like in ygopro.
    */
  private[scene] val cardViewer: VBox = new VBox(){
    styleClass ++= Seq("cardViewer", "pane")
    minHeight = 500
    minWidth = 200
    maxWidth = 250
    alignment = Pos.TopCenter
    children = Seq(cardViewerPictureBox, descBox)
  }
}
