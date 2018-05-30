package me.mysterymystery.duelingfactions.api.turn

import me.mysterymystery.duelingfactions.api.board.Board
import me.mysterymystery.duelingfactions.api.util.PimpedOption._
/**
  * Builds the board and controls the game flow.
  */
class GameController (board: Board) {

  def drawPhase: Unit = {

  }

  def startOfTurn: Unit = {
    //board.mySide.foreach()
    board.mySide.filter(_.occupied).foreach(c => c.peek.ifPresent(_.onStartOfTurn(board)))
  }

  def endPhase: Unit = {
    board.mySide.filter(_.occupied).foreach(c => c.peek.ifPresent(_.onEndOfTurn(board)))
  }

  def doLoop: Unit = {
    //for each zone containing card, register popup allowing attacking etc.
    // then de register those who dont
    drawPhase
    startOfTurn
    endPhase
    //blah
  }
}
