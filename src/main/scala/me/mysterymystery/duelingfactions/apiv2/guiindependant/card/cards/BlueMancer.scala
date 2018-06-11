package me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards

import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.BoardSides.BoardSide
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.GameController
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.MonsterCard
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterTypes
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterTypes.MonsterType
import me.mysterymystery.duelingfactions.apiv2.guiindependant.pimping.PimpedInt._

@SerialVersionUID(123L)
class BlueMancer(override val owner: BoardSide) extends MonsterCard{
  /**
    *
    * @return Level of the monster
    */
  override def level: Int = 4

  /**
    *
    * @return Attack points of the monster
    */
  override def attack: Int = 0

  /**
    *
    * @return Defense points of the monster
    */
  override def defense: Int = 0

  /**
    *
    * @return Monster Type
    */
  override def monsterType: MonsterType = MonsterTypes.EFFECT

  /**
    *
    * @return The effect that the card performs.
    */
  override def action: GameController => Unit = gc => {

  }

  /**
    * Card Description can either be geenral lore, or if the card has an effect, the effect.
    *
    * @return Card description.
    */
  override def cardText: String = ""

  /**
    *
    * @return Name of the card.
    */
override def name: String = ""

  /**
    *
    * @return The file that holds the sprite for the card.
    */
override def spriteName: String = "TemplarOfBlue.png"

  /**
    *
    * @return Action to perform when card is drawn.
    */
override def onDrawn: GameController => Unit = ???

  /**
    *
    * @return Action to perform on the start of the turn.
    */
override def onStartOfTurn: GameController => Unit = ???

  /**
    *
    * @return Action to perform on the end of the turn.
    */
override def onEndOfTurn: GameController => Unit = ???
}
