package me.mysterymystery.duelingfactions.api.board.locations

import javafx.scene.{image, input}
import me.mysterymystery.duelingfactions.api.card.{Card, MonsterCard}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.Pane
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.scene.GameScene

class MonsterZone extends BoardZone {
  prefHeight = Config.cardHeight
  prefWidth = Config.cardHeight
  styleClass ++= Seq("monsterSlot")

  private var occupiedWith: MonsterCard = _

  onMouseEntered = (e: input.MouseEvent) => {
    GameScene.cardViewerPictureBox.image = occupiedWith.sprite
    GameScene.descBox.text = occupiedWith.cardText
  }
  onMouseExited = (e: input.MouseEvent) => {
    GameScene.cardViewerPictureBox.image = new Image(new image.Image(getClass.getResourceAsStream("/sprites/CardBack.png")))
    GameScene.descBox.text = ""
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
}
