package me.mysterymystery.duelingfactions.api.turn

import me.mysterymystery.duelingfactions.api.board.Board

/**
  * Builds the board and controls the game flow.
  */
class GameController (board: Board) {

  def drawPhase: Unit = {

  }

  def startOfTurn: Unit = {
    //board.mySide.foreach()
  }

  def endPhase: Unit = {
  }

  def doLoop: Unit = {
    //for each zone containing card, register popup allowing attacking etc.
    // then de register those who dont
  }
}
