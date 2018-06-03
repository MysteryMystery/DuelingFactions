package me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards

import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.{Board, GameController}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.MonsterCard
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterTypes
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterTypes.MonsterType

class ExampleMonster extends MonsterCard{

  override def level: Int = 6

  override def attack: Int = 1200

  override def defense: Int = 3000

  override def monsterType: MonsterType = MonsterTypes.NORMAL

  /**
    * The effect that the card performs.
    *
    * @return
    */
  override def action: GameController => Unit = _ => ()

  /**
    * Card Description can either be geenral lore, or if the card has an effect, the effect.
    *
    * @return Card description.
    */
override def cardText: String = "An example card"

  /**
    *
    * @return Name of the card.
    */
override def name: String = "The Red Light"

  /**
    *
    * @return The file that holds the sprite for the card.
    */
override def spriteName: String = "TheRedLight.png"

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
