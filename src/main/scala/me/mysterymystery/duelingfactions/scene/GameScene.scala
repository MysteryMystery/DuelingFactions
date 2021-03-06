package me.mysterymystery.duelingfactions.scene

import javafx.event.ActionEvent
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.{Scene, image}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import javafx.scene.image
import me.mysterymystery.duelingfactions.api.board.faction.factions.ExampleFaction
import me.mysterymystery.duelingfactions.api.board.Board
import me.mysterymystery.duelingfactions.api.board.locations.MonsterZone
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import me.mysterymystery.duelingfactions.apiv2.guidependant.card._
import me.mysterymystery.duelingfactions.api.card.deck.StubDeck
import me.mysterymystery.duelingfactions.api.card.cardcollection.Deck
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards.ExampleSpell
import me.mysterymystery.duelingfactions.api.card.deck.StubDeck
import scalafx.scene.control.{Button, TextArea}
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.apiv2.guidependant.game.Game
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.GameController
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards.ExampleMonster
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterPositions
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.BoardSides._
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards

import scala.collection.parallel.Task
import scala.concurrent.Future

object GameScene extends SceneBuilder {

  /**
    * Area to present lifepoints, turn count, who's turn it is.
    */
  val lifePointsArea: VBox = new VBox(){
    prefHeight = 50
  }

  val newGame = new Game(new GameController)
  //newGame.gameController.boards("mySide").hand.draw(1)

  /**
    *
    * @return the scene for the game board.
    */
  override val get: Scene = new Scene() {

      stylesheets.addAll(
        getClass.getResource("/css/appSkin.css").toExternalForm,
        getClass.getResource("/css/boardSkin.css").toExternalForm,
        getClass.getResource("/css/cardSkin.css").toExternalForm
      )

      root = new BorderPane() {
        styleClass ++= Seq("background", "pane")
        prefHeight = 500
        top = new HBox(){
          children = Seq(
            lifePointsArea
          )
        }
        left = cardViewer
        center = new HBox(){
          children = Seq(/*boardField*/
            newGame.visual
          )
          styleClass ++= Seq("boardWrapper")
          alignmentInParent = Pos.Center
        }
      }
    }

  def setDescriptionBox(card: Card): Unit = {
    if (card != null){
      cardViewerPictureBox.image = card.sprite
      descBox.text = card.cardText
    }else {
      cardViewerPictureBox.image = cards.emptyCard.sprite
      descBox.text = cards.emptyCard.cardText
    }
  }
}
