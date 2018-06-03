package me.mysterymystery.duelingfactions.apiv2.guidependant.game

import java.util.Date

import javafx.animation
import javafx.beans.InvalidationListener
import javafx.beans.value.{ChangeListener, ObservableObjectValue}
import javafx.event.{ActionEvent, EventHandler}
import javafx.scene.input.MouseEvent
import me.mysterymystery.duelingfactions.DuelingFactions
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.GameController
import me.mysterymystery.duelingfactions.apiv2.guidependant.card._
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterPositions
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card._
import me.mysterymystery.duelingfactions.apiv2.guiindependant.config.Config
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.scene.control.Button
import scalafx.scene.image.ImageView
import scalafx.scene.layout._
import scalafx.stage.Popup
import javafx.scene.input
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections.{Deck, Graveyard, Hand}
import scalafx.geometry.{Insets, Orientation, Pos}
import me.mysterymystery.duelingfactions.apiv2.guidependant.hero._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.beans.value.ObservableValue
import scalafx.scene.text.Text
import scalafx.util.Duration

import scala.collection.mutable

class Game(val gameController: GameController) {
  val myField: mutable.MutableList[StackPane] = mutable.MutableList (
    monsterPane(null), monsterPane(null), monsterPane(null), monsterPane(null), monsterPane(null), graveyardPane(gameController.boards("mySide").graveyard),
    spellPane(null), spellPane(null), spellPane(null), spellPane(null), spellPane(null), deckPane(gameController.boards("mySide").deck)
  )

  val theirField: mutable.MutableList[StackPane] = mutable.MutableList(
    spellPane(null), spellPane(null), spellPane(null), spellPane(null), spellPane(null), deckPane(gameController.boards("theirSide").deck),
    monsterPane(null), monsterPane(null), monsterPane(null), monsterPane(null), monsterPane(null), graveyardPane(gameController.boards("theirSide").graveyard)
  )

  def field: List[Pane] = (theirField.toBuffer ++= myField).toList

  /**
    *
    * @param boardIndex of the overall field
    */
  def cardOcuppiedBy(boardIndex: Int): Card = {
    if (boardIndex < 12){
      (gameController.boards("theirSide").spellTrapZones.toBuffer ++= gameController.boards("theirSide").monsterZones)(boardIndex)
    }else {
      (gameController.boards("mySide").monsterZones.toBuffer ++= gameController.boards("mySide").spellTrapZones)(boardIndex % 12)
    }
  }

  def occupy(boardIndex: Int, card: Card): Unit = {
    if (boardIndex < 12){
      (gameController.boards("theirSide").spellTrapZones.toBuffer ++= gameController.boards("theirSide").monsterZones)(boardIndex) = card
    }else {
      (gameController.boards("mySide").monsterZones.toBuffer ++= gameController.boards("mySide").spellTrapZones)(boardIndex % 12) =  card
    }
  }

  //TODO comp;lete for deck and gy and other side.
  /**
    * Syncs front end with backend.
   */
  def syncField: Unit = {
    for (i <- gameController.boards("mySide").monsterZones.indices){
      if (gameController.boards("mySide").monsterZones(i) != null){
        myField(i).children = Seq(
          gameController.boards("mySide").monsterZones(i).imageView
        )
        myField(i).onMouseClicked = (e: input.MouseEvent) => {
          new Popup(){
            autoFix = true
            autoHide = true
            content.addAll(
              if (gameController.boards("mySide").monsterZones(i).position == MonsterPositions.Defense){
                new Button("Attack Mode"){
                  styleClass ++= Seq("monsterSlot")
                  onMouseClicked = (e: input.MouseEvent) => gameController.boards("mySide").monsterZones(i).position = MonsterPositions.Attack
                }.delegate
              } else {
                new Button("Defense Mode"){
                  styleClass ++= Seq("monsterSlot")
                  onMouseClicked = (e: input.MouseEvent) => gameController.boards("mySide").monsterZones(i).position = MonsterPositions.Defense
                }.delegate
              }
            )
            if (gameController.boards("mySide").monsterZones(i).position == MonsterPositions.Attack)
              content.add(
                new Button("Attack"){
                  styleClass ++= Seq("monsterSlot")
                }.delegate
              )
          }
        }
      }
    }

    for (i <- gameController.boards("mySide").spellTrapZones.indices){
      if (gameController.boards("mySide").spellTrapZones(i) != null) {
        myField(i).children = Seq(
          gameController.boards("mySide").spellTrapZones(i).imageView
        )
        myField(i).onMouseClicked = (e: input.MouseEvent) => {
          new Popup() {
            autoFix = true
            autoHide = true
            content.addAll(
              new Button("Cast"){
                styleClass ++= Seq("monsterSlot")
              }.delegate
            )
          }
        }
      }
    }
  }

  def monsterPane(card: MonsterCard): StackPane = {
    new StackPane(){
      alignment = Pos.Center
      prefHeight = Config.cardHeight
      prefWidth = Config.cardHeight
      styleClass ++= Seq("monsterSlot")

      onMouseEntered = (e: MouseEvent) => {
        GameScene.setDescriptionBox(card.asInstanceOf[Card])
      }
      onMouseExited = (e: MouseEvent) => {
        GameScene.setDescriptionBox(Card.empty)
      }

      onMouseClicked = (e: MouseEvent) => if (card != null) new Popup(){
        autoFix = true
        autoHide = true

        content.add(new VBox(){
          children = Seq(
            new Button("Attack") {
              styleClass = Seq("summonButton")
              onAction = (e: ActionEvent) => {
                //Get attack target on next click then attack
                hide()
              }
            }
          )
        }.delegate
        )
        show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
      }
    }
  }

  def spellPane(card: SpellOrTrapCard): StackPane = {
    new StackPane(){
      alignment = Pos.Center
      prefHeight = Config.cardHeight
      prefWidth = Config.cardHeight
      styleClass ++= Seq("spellSlot")

      onMouseEntered = (e: MouseEvent) => {
        if (card != null){
          GameScene.setDescriptionBox(card)
        }
      }
      onMouseExited = (e: MouseEvent) => {
        GameScene.setDescriptionBox(Card.empty)
      }

      onMouseClicked = (e: MouseEvent) => if (card != null) new Popup(){
        autoFix = true
        autoHide = true

        content.add(new VBox(){
          children = Seq(
            new Button("Activate") {
              styleClass = Seq("summonButton")
              onAction = (e: ActionEvent) => {

                hide()
              }
            }
          )
        }.delegate
        )
        show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
      }
    }
  }

  private val deckPanePopup = new Popup(){
    autoHide = true
    autoFix = true
  }

  def deckPane(deck: Deck): StackPane = {
    new StackPane(){
      prefWidth = Config.cardWidth
      styleClass ++= Seq("deckSlot")

      onMouseEntered = (e: MouseEvent) =>{
        deckPanePopup.content.add(new HBox(){
          styleClass = Seq("summonButton")
          children = Seq(
            new Text(deck.length.toString)
          )
        }.delegate)
        deckPanePopup.show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
      }

      onMouseExited = (e: MouseEvent) => {
        deckPanePopup.hide()
      }

      onMouseClicked = (e: MouseEvent) => {
        new Popup(){
          styleClass = Seq("summonButton")
          autoFix = false
          autoHide = true
          deckPanePopup.hide()

          children.add(
            new Button("Draw"){
              styleClass = Seq("summonButton")
              onAction = (e: ActionEvent) => {
                val t = new Date()
                val x: Card = gameController.boards("mySide").hand.draw
                handBox.children add addHandboxChild(x).delegate
                println("Drawn")
                val y = new Date()
                println(y.getTime - t.getTime)
                hide()
              }
            }.delegate
          )
          show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
        }
      }
    }
  }

  private val gyPanePopup = new Popup(){
    autoHide = true
    autoFix = true
  }
  def graveyardPane(gy: Graveyard): StackPane = {
    new StackPane(){
      prefWidth = Config.cardWidth
      styleClass ++= Seq("graveSlot")

      onMouseEntered = (e: MouseEvent) =>{
        gyPanePopup.content.add(new HBox(){
          styleClass = Seq("summonButton")
          children = new Text(gy.length.toString)
        }.delegate)
        gyPanePopup.show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
      }

      onMouseExited = (e: MouseEvent) => {
        gyPanePopup.hide()
      }
    }
  }

  /**
    * On draw -> needs refreshing
    */
  private val handBox: FlowPane = new FlowPane(){
    hgap = -10
    //children = visibleHandBoxChildren
    children = visibleHandBoxChildren
  }

  /**
    * All children and their actions of the visible hand.
    * @return All children
    */
  private def visibleHandBoxChildren: Seq[ImageView] = gameController.boards("mySide").hand.map(addHandboxChild  )

  private def addHandboxChild(i: Card): ImageView = {
    new ImageView(i.sprite){
      fitWidth = Config.cardWidth
      fitHeight = Config.cardHeight
      onMouseEntered = (e: MouseEvent) => {
        fitWidth = Config.cardWidth + 10
        fitHeight = Config.cardHeight + 10

        GameScene.setDescriptionBox(i)
      }
      onMouseExited  = (e: MouseEvent) => {
        fitWidth = Config.cardWidth
        fitHeight = Config.cardHeight

        GameScene.setDescriptionBox(null)
      }
      onMouseClicked = (e: MouseEvent) => {
        new Popup(){
          //x =  e.getX
          //y = e.getY
          autoFix = true
          autoHide = true

          content.add(new VBox(){
            children = Seq(
              i match {
                case spellCard: SpellCard => new Button("Cast") {
                  styleClass = Seq("summonButton")
                  onAction = (e: ActionEvent) => {
                    gameController.boards("mySide").hand -= i
                    syncField
                    gameController.boards("mySide").cast(spellCard)
                    syncField
                    handBox.children = visibleHandBoxChildren
                    hide()
                  }
                }
                case trapCard: TrapCard => new Button("Set"){
                  styleClass = Seq("summonButton")
                  onAction = (e: ActionEvent) => {
                    gameController.boards("mySide").hand -= i
                    gameController.boards("mySide").set(trapCard)
                    handBox.children = visibleHandBoxChildren
                    syncField
                    hide()
                  }
                }
                case monster: MonsterCard => new VBox(){
                  children = Seq(
                    new Button("Summon") {
                      styleClass = Seq("summonButton")
                      onAction = (e: ActionEvent) => {
                        gameController.boards("mySide").hand -= i
                        gameController.boards("mySide").summon(monster, MonsterPositions.Attack)
                        handBox.children = visibleHandBoxChildren
                        syncField
                        hide()
                      }
                    },
                    new Button("Set"){
                      styleClass = Seq("summonButton")
                      onAction = (e: ActionEvent) => {
                        gameController.boards("mySide").hand -= i
                        gameController.boards("mySide").summon(monster, MonsterPositions.Defense)
                        handBox.children = visibleHandBoxChildren
                        syncField
                        hide()
                      }
                    }
                  )
                }
              }
            )
          }.delegate
          )
          show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
        }
      }
    }
  }

  val visual: VBox = new VBox(){
    styleClass ++= Seq("boardField")
    margin = Insets(20)
    prefWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
    vgrow = Priority.Never
    hgrow = Priority.Never
    children = Seq(
      new HBox(){
        children = Seq(new ImageView(gameController.boards("theirSide").hero.sprite) {fitWidth = 90; fitHeight = 90})
      },
      new FlowPane(){
        orientation = Orientation.Horizontal
        hgap = 5
        vgap = 5
        //maxWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
        children = theirField
      },
      new HBox(){
        prefHeight = 20
        alignment = Pos.Center
        children = Seq(

        )
      },
      new FlowPane(){
        orientation = Orientation.Horizontal
        hgap = 5
        vgap = 5
        //maxWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
        children = myField
      },
      //visibleHandBox
      new HBox(){
        children = Seq(handBox, new ImageView(gameController.boards("mySide").hero.sprite) {fitWidth = 90; fitHeight = 90})
      }
    )
  }

  /*val timer: Timeline = new Timeline{
    cycleCount = Timeline.Indefinite
    keyFrames = Seq(
      new KeyFrame(new animation.KeyFrame(Duration(1000), new EventHandler[ActionEvent] {
        override def handle(event: ActionEvent): Unit = handBox.children = visibleHandBoxChildren
      }))
    )
  }*/
  //timer.play()
}
