package me.mysterymystery.duelingfactions.api.board.locations

import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.image
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import me.mysterymystery.duelingfactions.DuelingFactions
import me.mysterymystery.duelingfactions.api.board.misc.CardPane
import me.mysterymystery.duelingfactions.api.card.{Card, MonsterCard, SpellCard, SpellOrTrapCard}
import me.mysterymystery.duelingfactions.api.card.cardcollection.Graveyard
import me.mysterymystery.duelingfactions.api.card.cardlist.ExampleCard
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.geometry.Pos
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{FlowPane, Pane, VBox}
import scalafx.stage.Popup

class GraveyardZone extends BoardZone {
  prefWidth = Config.cardWidth
  styleClass ++= Seq("graveSlot")
  private var occupiedWith: Graveyard = new Graveyard(new ExampleCard, new ExampleCard)

  val newPop = new Popup() {
    autoFix = true
    autoHide = true

    content.add(
      new VBox() {
        alignment = Pos.Center
        styleClass = Seq("graveyardPane")
        children = Seq(
          new Label("Graveyard"){
            style = "-fx-background-color: white;"
          },
          new FlowPane() {
            alignment = Pos.Center
            minWidth = 600
            minHeight = 200
            children = occupiedWith.map(
              i => new CardPane(i){
                children = new ImageView(i.sprite) {
                  fitHeight = Config.cardHeight * 1.25
                  fitWidth = Config.cardWidth * 1.25
                  onMouseEntered = (e: MouseEvent) => {
                    fitHeight = Config.cardHeight * 1.25 + 10
                    fitWidth = Config.cardWidth * 1.25 + 10
                    GameScene.setDescriptionBox(i)
                  }
                  onMouseExited = (e: MouseEvent) => {
                    fitHeight = Config.cardHeight * 1.25 - 10
                    fitWidth = Config.cardWidth * 1.25 - 10
                  }
                }
              }
            )
          },
          new Button("Close") {
            styleClass = Seq("summonButton")
            onAction = (event: ActionEvent) => hide()
          }
        )
      }.delegate
    )
  }

  onMouseClicked = (e: MouseEvent) => {
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
              newPop.show(DuelingFactions.stage, DuelingFactions.stage.x.value + DuelingFactions.stage.width.value / 2d, DuelingFactions.stage.y.value + DuelingFactions.stage.height.value / 2d)
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

  override def peek: Option[Card] = None
}
