package me.mysterymystery.duelingfactions.apiv2.guiindependant.board

import me.mysterymystery.duelingfactions.apiv2.guidependant.game.Game
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections.Hand
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.decks.StubDeck

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
  val boards: Map[String, Board] = Map(
    "mySide" -> new Board(new StubDeck),
    "theirSide" -> new Board(new StubDeck)
  )

  val game = new Game(this)

}
