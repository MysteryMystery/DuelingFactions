package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.{BoardSides, GameController}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.BoardSides.BoardSide
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.{ClasspathHelper, ConfigurationBuilder}

import scala.collection.JavaConverters._

//FIXME https://stackoverflow.com/questions/50702113/dynamically-instantiating-classes-scala
package object cards {
  private val reflections: Reflections = new Reflections(
    new ConfigurationBuilder()
      .setUrls(ClasspathHelper.forPackage("me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards"))
      .setScanners(new SubTypesScanner())
  )

  /**
    *
    * @return All Cards in the game.
    */
  def getAllCards: Seq[Card] = getAllMonsters ++ getAllSpells ++ getAllTraps

  def getAllSpellOrTraps: Seq[SpellOrTrapCard] = getAllSpells ++ getAllTraps

  def getAllSpells: Seq[SpellCard] = reflections.getSubTypesOf(classOf[SpellCard]).asScala.map(_.getConstructors()(0).newInstance(BoardSides.NoSide).asInstanceOf[SpellCard]).toSeq

  def getAllTraps: Seq[TrapCard] = reflections.getSubTypesOf(classOf[TrapCard]).asScala.map(_.getConstructors()(0).newInstance(BoardSides.NoSide).asInstanceOf[TrapCard]).toSeq

  def getAllMonsters: Seq[MonsterCard] = reflections.getSubTypesOf(classOf[MonsterCard]).asScala.map(_.getConstructors()(0).newInstance(BoardSides.NoSide).asInstanceOf[MonsterCard]).toSeq

  def emptyCard: Card = new Card {
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
    override def spriteName: String = "CardBack.png"

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

    override val owner: BoardSide = null
  }
}
