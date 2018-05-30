package me.mysterymystery.duelingfactions.api.board.locations

import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.image
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import me.mysterymystery.duelingfactions.DuelingFactions
import me.mysterymystery.duelingfactions.api.card.{Card, MonsterCard, SpellCard, SpellOrTrapCard}
import me.mysterymystery.duelingfactions.api.card.cardcollection.Graveyard
import me.mysterymystery.duelingfactions.api.card.cardlist.ExampleCard
import me.mysterymystery.duelingfactions.api.config.Config
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{FlowPane, VBox}
import scalafx.stage.Popup

class GraveyardZone extends BoardZone {
  prefWidth = Config.cardWidth
  styleClass ++= Seq("graveSlot")
  private var occupiedWith: Graveyard = new Graveyard(new ExampleCard, new ExampleCard)

  onMouseClicked = (e: MouseEvent) => {
    val newPop = new Popup() {
      autoFix = true
      autoHide = true

      content.add(
        new HBox() {
          styleClass = Seq("graveyardPane")
          children = Seq(
            new Label("Graveyard"){
              style = "-fx-background-color: white;"
            },
            new FlowPane() {
              minWidth = 600
              minHeight = 200
              children = occupiedWith.map(
                i => new ImageView(i.sprite) {
                  fitHeight = Config.cardHeight * 1.25
                  fitWidth = Config.cardWidth * 1.25
                  onMouseEntered = (e: MouseEvent) => {

                  }
                  onMouseExited = (e: MouseEvent) => {

                  }
                }
              )
            },
            new Button("Close") {
              styleClass = Seq("summonButton")
              onAction = (event: ActionEvent) => hide()
            }
          )
        }
      )
    }
    new Popup(){
      //x =  e.getX
      //y = e.getY
      autoFix = true
      autoHide = true

      content.add(new VBox(){
        children = Seq(
          new Button("View") {
            styleClass = Seq("summonButton")
            onAction = (e: ActionEvent) => {
              newPop.show(DuelingFactions.stage, DuelingFactions.stage.x.value, DuelingFactions.stage.y.value)
              hide()
            }
          }
        )
      }.delegate
      )
      show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
    }
  }

  def + (card: Card): Unit = occupiedWith += card
  def - (card: Card): Unit = occupiedWith -= card

  override def occupied: Boolean = true
}
