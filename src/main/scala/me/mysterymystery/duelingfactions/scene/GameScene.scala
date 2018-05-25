package me.mysterymystery.duelingfactions.scene

import scalafx.scene.Scene
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}

object GameScene {
  def apply: GameScene = new GameScene
}

class GameScene extends Scene {
  root = new BorderPane(){
    top = lifePointsArea
    left = cardViewer
    center = boardField
  }

  /**
    * Area to present lifepoints, turn count, who's turn it is.
    */
  val lifePointsArea: VBox = new VBox(){

  }

  /**
    * holds the board, cards on it.
    */
  val boardField: GridPane = new GridPane(){

  }

  /**
    * Card discriptions on card hover. Like in ygopro.
    */
  val cardViewer: HBox = new HBox(){

  }
}
