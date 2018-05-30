package me.mysterymystery.duelingfactions.api.board.locations

import javafx.scene.{image, input}
import me.mysterymystery.duelingfactions.api.card.{Card, MonsterCard, SpellOrTrapCard}
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.scene.image.{Image, ImageView}

class SpellTrapZone extends BoardZone {
  prefHeight = Config.cardHeight
  prefWidth = Config.cardHeight
  styleClass ++= Seq("spellSlot")

  private var occupiedWith: SpellOrTrapCard = _

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

  def occupied: Boolean = occupiedWith != null
  def occupy (card: SpellOrTrapCard): Unit = {
    occupiedWith = card
    children = Seq(new ImageView(card.sprite) {fitWidth = Config.cardWidth; fitHeight = Config.cardHeight})
  }
  def deoccupy: SpellOrTrapCard = {
    val c = occupiedWith
    occupiedWith = null
    c
  }

  /**
    *
    * @return The card contained within
    */
  def peek: Option[SpellOrTrapCard] = Some(occupiedWith)
}
