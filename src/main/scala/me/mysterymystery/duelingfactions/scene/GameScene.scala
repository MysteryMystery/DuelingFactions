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
import me.mysterymystery.duelingfactions.api.card.deck.StubDeck
import me.mysterymystery.duelingfactions.api.card.cardcollection.Deck
import me.mysterymystery.duelingfactions.api.card.cardlist.{ExampleCard, ExampleSpell}
import me.mysterymystery.duelingfactions.api.card.deck.StubDeck
import scalafx.scene.control.{Button, TextArea}
import me.mysterymystery.duelingfactions.api.config.Config

object GameScene extends SceneBuilder {

  /**
    * Area to present lifepoints, turn count, who's turn it is.
    */
  val lifePointsArea: VBox = new VBox(){
    minHeight = 50
  }

  val cardViewerPictureBox: ImageView = new ImageView{
    //image = new Image(new javafx.scene.image.Image())
    fitWidth = 180
    fitHeight = 240
  }

  val descBox: TextArea = new TextArea {
    wrapText = true
    editable = false
  }

  /**
    * Card discriptions on card hover. Like in ygopro.
    */
  val cardViewer: VBox = new VBox(){
    styleClass ++= Seq("cardViewer", "pane")
    minHeight = 500
    minWidth = 200
    maxWidth = 250
    alignment = Pos.Center
    children = Seq(cardViewerPictureBox, descBox)
  }

  val b : Board = new Board(new ExampleFaction(), StubDeck, new ExampleFaction, new Deck(Seq()))
  b.summonMonster(new ExampleCard, b.BoardSides.MySide)

  /**
    *
    * @return the scene for the game board.
    */
  override def get: Scene = {
    new Scene() {

      stylesheets.addAll(
        getClass.getResource("/css/appSkin.css").toExternalForm,
        getClass.getResource("/css/boardSkin.css").toExternalForm,
        getClass.getResource("/css/cardSkin.css").toExternalForm
      )

      root = new BorderPane() {
        styleClass ++= Seq("background", "pane")
        top = new HBox(){
          children = Seq(
            lifePointsArea
          )
        }
        left = cardViewer
        center = new HBox(){
          children = Seq(/*boardField*/
            b.visual,
            new VBox(){
              children = Seq(
                new Button("Summon A card! (testing)"){
                  onAction = (e: ActionEvent) => b.summonMonster(new ExampleCard, b.BoardSides.MySide)
                },
                new Button("Summon A Spell! (testing)"){
                  onAction = (e: ActionEvent) => b.summonSpellTrap(new ExampleSpell, b.BoardSides.MySide)
                }
              )
            }
          )
          styleClass ++= Seq("boardWrapper")
          alignmentInParent = Pos.Center
        }
      }
    }
  }
}
