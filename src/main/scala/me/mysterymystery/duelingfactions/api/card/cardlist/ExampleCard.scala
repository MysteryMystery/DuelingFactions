package me.mysterymystery.duelingfactions.api.card.cardlist

import javafx.scene.image
import me.mysterymystery.duelingfactions.api.board.Board
import me.mysterymystery.duelingfactions.api.card.{MonsterCard, MonsterTypes}
import me.mysterymystery.duelingfactions.api.card.MonsterTypes.MonsterType
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.Pane

class ExampleCard extends MonsterCard{
  /**
    *
    * @return Level of the card.
    */
  override def level: Int = 20

  /**
    *
    * @return Type of the monster.
    */
  override def monsterType: MonsterType = MonsterTypes.EFFECT

  /**
    *
    * @return The image for the card.
    */
  override def sprite: Image = produceSprite("TheRedLight.png")

  /**
    * The effect that the card performs.
    *
    * @return
    */
  override def action: (Board) => Unit = (b) => {}

  /**
    * Card Description can either be geenral lore, or if the card has an effect, the effect.
    *
    * @return Card description.
    */
override def cardText: String = "The monster effect here"

  /**
    *
    * @return Name of the card.
    */
  override def name: String = "Example Monster"
}
