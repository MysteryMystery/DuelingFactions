package me.mysterymystery.duelingfactions.api.board.locations

import javafx.event.ActionEvent
import javafx.scene.input.MouseEvent
import javafx.scene.{image, input}
import me.mysterymystery.duelingfactions.DuelingFactions
import me.mysterymystery.duelingfactions.api.card.{Card, MonsterCard, SpellOrTrapCard}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Pane, VBox}
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.scene.control.Button
import scalafx.stage.Popup

class MonsterZone extends BoardZone {
  prefHeight = Config.cardHeight
  prefWidth = Config.cardHeight
  styleClass ++= Seq("monsterSlot")

  private var occupiedWith: MonsterCard = _

  onMouseEntered = (e: input.MouseEvent) => {
    if (occupiedWith != null){
      GameScene.cardViewerPictureBox.image = occupiedWith.sprite
      GameScene.descBox.text = occupiedWith.cardText
    }
  }
  onMouseExited = (e: input.MouseEvent) => {
    GameScene.cardViewerPictureBox.image = new Image(new image.Image(getClass.getResourceAsStream("/sprites/CardBack.png")))
    GameScene.descBox.text = ""
  }

  onMouseClicked = (e: MouseEvent) => if (occupied) new Popup(){
    autoFix = true
    autoHide = true

    content.add(new VBox(){
      children = Seq(
        new Button("Attack") {
          styleClass = Seq("summonButton")
          onAction = (e: ActionEvent) => {
              //Get attack target on next click then attack
            hide()
          }
        }
      )
    }.delegate
    )
    show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
  }

  override def occupied: Boolean = occupiedWith != null

  def occupy (card: MonsterCard): Unit = {
    occupiedWith = card
    children = Seq(new ImageView(card.sprite) {fitWidth = Config.cardWidth; fitHeight = Config.cardHeight})
  }

  def deoccupy: MonsterCard = {
    val c = occupiedWith
    occupiedWith = null
    c
  }

  /**
    *
    * @return The card contained within
    */
  def peek: Option[MonsterCard] = Some(occupiedWith)
}
