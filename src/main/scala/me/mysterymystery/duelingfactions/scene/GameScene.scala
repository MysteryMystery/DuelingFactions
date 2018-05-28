package me.mysterymystery.duelingfactions.scene

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.{Scene, image}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import javafx.scene.image
import me.mysterymystery.duelingfactions.api.board.locations.MonsterZone
import me.mysterymystery.duelingfactions.api.card.cardlist.ExampleCard
import scalafx.scene.control.TextArea
import me.mysterymystery.duelingfactions.api.config.Config

object GameScene extends SceneBuilder {

  /**
    * Area to present lifepoints, turn count, who's turn it is.
    */
  val lifePointsArea: VBox = new VBox(){
    minHeight = 50
  }

  /**
    * holds the board, cards on it.
    */
  val boardField: GridPane = new GridPane(){
    gridLinesVisible = true
    styleClass ++= Seq("boardField", "pane")
    prefHeight = 600
    prefWidth = 800
    minHeight = 600
    minWidth = 800
    maxHeight = 600
    maxWidth = 800
    alignmentInParent = Pos.Center
    hgap = 5
    vgap = 5
  }
  val sp = deckSlotPane
  sp.alignmentInParent = Pos.CenterRight
  val gp = graveSlotPane
  val mz = new MonsterZone
  gp.alignmentInParent = Pos.CenterRight
  boardField.addRow(0, sp, spellTrapSlotPane, spellTrapSlotPane, spellTrapSlotPane, spellTrapSlotPane, spellTrapSlotPane)
  boardField.addRow(1, gp, monsterSlotPane, monsterSlotPane, monsterSlotPane, monsterSlotPane, monsterSlotPane)
  //boardField.addRow(2, monsterSlotPane, monsterSlotPane, monsterSlotPane, monsterSlotPane, monsterSlotPane, graveSlotPane)
  boardField.addRow(2, mz, new MonsterZone, new MonsterZone, new MonsterZone, new MonsterZone, graveSlotPane)
  boardField.addRow(3, spellTrapSlotPane, spellTrapSlotPane, spellTrapSlotPane, spellTrapSlotPane, spellTrapSlotPane, deckSlotPane)
  mz.occupy(new ExampleCard)

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

  def monsterSlotPane: Pane = new Pane(){
    prefHeight = Config.cardHeight
    prefWidth = Config.cardHeight
    styleClass ++= Seq("monsterSlot")
  }

  def spellTrapSlotPane: Pane = new Pane(){
    prefHeight = Config.cardHeight
    prefWidth = Config.cardHeight
    styleClass ++= Seq("spellSlot")
  }

  def graveSlotPane: Pane = new Pane(){
    prefHeight = Config.cardHeight
    prefWidth = Config.cardWidth
    maxWidth = Config.cardWidth
    styleClass ++= Seq("graveSlot")
  }

  def deckSlotPane: Pane = new Pane(){
    prefHeight = Config.cardHeight
    prefWidth = Config.cardWidth
    maxWidth = Config.cardWidth
    styleClass ++= Seq("deckSlot")
  }

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
          children = Seq(boardField)
          styleClass ++= Seq("boardWrapper")
          alignmentInParent = Pos.Center
        }
      }
    }
  }
}
