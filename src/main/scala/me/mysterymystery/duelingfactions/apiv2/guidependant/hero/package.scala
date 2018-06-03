package me.mysterymystery.duelingfactions.apiv2.guidependant

import me.mysterymystery.duelingfactions.apiv2.guiindependant.config.Config
import me.mysterymystery.duelingfactions.apiv2.guiindependant.hero.Hero
import scalafx.scene.image.{Image, ImageView}

package object hero {
  implicit class HeroOpts (hero: Hero) {
    /**
      * Gets the sprite from resources.
      * @return sprite for the card.
      */
    protected def produceSprite(name: String): Image = new Image(new javafx.scene.image.Image(getClass.getResourceAsStream(s"/sprites/factions/$name")))

    /**
      *
      * @return The image for the card.
      */
    def sprite: Image = produceSprite(hero.spriteFileName)

    /**
      *
      * @return Image view
      */
    def imageView: ImageView = new ImageView(sprite) {fitWidth = Config.cardWidth; fitHeight = Config.cardHeight}
  }
}
