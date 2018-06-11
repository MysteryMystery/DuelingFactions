package me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards

import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.BoardSides.BoardSide
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.GameController
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.MonsterCard
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterTypes
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterTypes.MonsterType

@SerialVersionUID(123L)
class BloodMancer(override val owner: BoardSide) extends MonsterCard {
  /**
    *
    * @return Level of the monster
    */
  override def level: Int = 10

  /**
    *
    * @return Attack points of the monster
    */
  override def attack: Int = 1300

  /**
    *
    * @return Defense points of the monster
    */
  override def defense: Int = 1500

  /**
    *
    * @return Monster Type
    */
  override def monsterType: MonsterType = MonsterTypes.EFFECT

  /**
    *
    * @return The effect that the card performs.
    */
  override def action: GameController => Unit = _ => ()

  /**
    * Card Description can either be geenral lore, or if the card has an effect, the effect.
    *
    * @return Card description.
    */
  override def cardText: String = "If \"Blue Mancer\" is on your side of the field, you can summong this card."

  /**
    *
    * @return Name of the card.
    */
override def name: String = "Blood Mancer"

  /**
    *
    * @return The file that holds the sprite for the card.
    */
override def spriteName: String = "BloodMancer.png"

  /**
    *
    * @return Action to perform when card is drawn.
    */
override def onDrawn: GameController => Unit = _ => ()

  /**
    *
    * @return Action to perform on the start of the turn.
    */
override def onStartOfTurn: GameController => Unit = _ => ()

  /**
    *
    * @return Action to perform on the end of the turn.
    */
override def onEndOfTurn: GameController => Unit = _ => ()
}
