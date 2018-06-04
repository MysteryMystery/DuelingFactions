package me.mysterymystery.duelingfactions.apiv2.guidependant

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterPositions
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.{Card, MonsterCard}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.config.Config
import scalafx.scene.image.{Image, ImageView}

package object card {
  implicit class CardOps (card: Card){
    /**
      * Gets the sprite from resources.
      * @return sprite for the card.
      */
    protected def produceSprite(name: String): Image = new Image(new javafx.scene.image.Image(getClass.getResourceAsStream(s"/sprites/$name")))

    /**
      *
      * @return The image for the card.
      */
    def sprite: Image = if (card != null) produceSprite(card.spriteName) else produceSprite("CardBack.png")

    /**
      *
      * @return Image view
      */
    def imageView: ImageView = new ImageView(sprite) {
      fitWidth = Config.cardWidth
      fitHeight = Config.cardHeight
      rotate = if (card.isInstanceOf[MonsterCard] && card.asInstanceOf[MonsterCard].position == MonsterPositions.Defense) 90 else 0
    }
  }

  implicit class ImageViewOps (imgv: ImageView) {

  }
}
