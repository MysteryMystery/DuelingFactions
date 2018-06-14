package me.mysterymystery.duelingfactions.apiv2.guiindependant.board

import me.mysterymystery.duelingfactions.apiv2.guidependant.game.Game
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections.Hand
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.decks.StubDeck
import scalafx.collections.ObservableBuffer.Change
import scalafx.collections.ObservableBuffer
import BoardSides.{BoardSide, MySide, TheirSide}
import me.mysterymystery.duelingfactions.DuelingFactions
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.EventManager
import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.events.cardevent.CardSummonedEvent
import me.mysterymystery.duelingfactions.eventprocesses.ListenTo

object GameController{
  private var _conn: GameController = null
  def gameController: GameController = _conn
  def gameController_=(conn: GameController): Unit = _conn = conn

  def apply: GameController = {
    val x = new GameController
    _conn = x
    _conn
  }
}

/**
  * controls game flow and provides link methods between api and gui
  */
class GameController {

  GameController.gameController = this

  /**
    * the boards to be controlled. Used for get/post gamestate?
    */
  val boards: Map[BoardSide, Board] = Map(
    MySide -> new Board(MySide, new StubDeck, this),
    TheirSide -> new Board(TheirSide, new StubDeck, this)
  )

  val game = new Game(this)

  EventManager.get.registerListener(this)

  def sendFieldChange[T <: Card](changes: Seq[ObservableBuffer.Change[T]], typ: Class[_ <: Card], side: BoardSides.BoardSide): Unit = {
    game.sendFieldChange[T](changes, typ, side)
  }

  @ListenTo
  def onCardPlay(e: CardSummonedEvent): Unit = {
    DuelingFactions.playSound("SummonSound.wav")
  }
}
