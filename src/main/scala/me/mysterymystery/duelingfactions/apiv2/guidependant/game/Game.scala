package me.mysterymystery.duelingfactions.apiv2.guidependant.game

import java.util.Date

import javafx.animation
import javafx.beans.InvalidationListener
import javafx.beans.value.{ChangeListener, ObservableObjectValue}
import javafx.collections.ObservableList
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
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.BoardSides.{BoardSide, MySide, TheirSide}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.logging.Logger
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.beans.value.ObservableValue
import scalafx.scene.text.Text
import scalafx.util.Duration
import scalafx.collections.ObservableBuffer
import scalafx.collections.ObservableBuffer._

import scala.collection.mutable

class Game(val gameController: GameController) {
  private var myField: ObservableBuffer[StackPane] = ObservableBuffer (
    monsterPane(null), monsterPane(null), monsterPane(null), monsterPane(null), monsterPane(null), graveyardPane(gameController.boards(MySide).graveyard),
    spellPane(null), spellPane(null), spellPane(null), spellPane(null), spellPane(null), deckPane(gameController.boards(MySide).deck)
  )
  myField.onChange((source, changes) => {
    myFieldPane.children = myField
    updateVisual
  })
  private var theirField: ObservableBuffer[StackPane] = ObservableBuffer(
    spellPane(null), spellPane(null), spellPane(null), spellPane(null), spellPane(null), deckPane(gameController.boards(TheirSide).deck),
    monsterPane(null), monsterPane(null), monsterPane(null), monsterPane(null), monsterPane(null), graveyardPane(gameController.boards(TheirSide).graveyard)
  )
  theirField.onChange((source, changes) => theirFieldPane.children = theirField)

  def field: List[Pane] = (theirField ++= myField).toList

  /**
    *
    * @param changes The changes to be made to the field.
    */
  def sendFieldChange[_ <: Card](changes: Seq[ObservableBuffer.Change[_]], typ: Class[_ <: Card], side: BoardSide): Unit = {
    Logger.info(this, s"sendFileChange -> passed in: ${typ.getSimpleName}  does it match one of these? [${classOf[MonsterCard].getSimpleName}, ${classOf[SpellOrTrapCard].getSimpleName}]")
    changes.foreach {
      case Add(position, added) => {
        Logger.info(this, "Matched Add")
        if (side == MySide){
          try {
            myField.update(position, monsterPane(added.head.asInstanceOf[MonsterCard]))
          }catch {case _: Throwable => }
          try {
            myField.update(position + 6, spellPane(added.head.asInstanceOf[SpellOrTrapCard]))
          } catch {case _: Throwable => }
        } else if (side == TheirSide){
          try {
            theirField.update(position+6, monsterPane(added.head.asInstanceOf[MonsterCard]))
          }catch {case _: Throwable => }
          try {
            theirField.update(position, spellPane(added.head.asInstanceOf[SpellOrTrapCard]))
          } catch {case _: Throwable => }
        }
      }
      case Remove(position, removed) => {
        Logger.info(this, "Matched Remove.")
        //TODO figure out a thing that works
      }

      /*case Add(position, added)  => {
        Logger.info(this, "Matched Add")
        //FIXME these if statments
        if (typ.getClass.getSimpleName == classOf[MonsterCard].getSimpleName && side == TheirSide){
          theirField.update(position + 6, monsterPane(added.head.asInstanceOf[MonsterCard]))
          Logger.info(this, "Monster Card Added.")
        }
        else if (typ.getClass.getName == classOf[MonsterCard].getName && side == MySide){
          myField.update(position, monsterPane(added.head.asInstanceOf[MonsterCard]))
          Logger.info(this, "Monster Card Added.")
        }

        if (typ.getClass.getName == classOf[SpellOrTrapCard].getName && side == MySide){
          myField.update(position + 6, spellPane(added.head.asInstanceOf[SpellOrTrapCard]))
          Logger.info(this, "Spell or Trap Card Added.")
        }else if (typ.getClass.getName == classOf[SpellOrTrapCard].getName && side == TheirSide){
          theirField.update(position, spellPane(added.head.asInstanceOf[SpellOrTrapCard]))
          Logger.info(this, "Spell or Trap Card Added.")
        }
      }
      case Remove(position, removed) => {
        Logger.info(this, "Matched Remove.")
        if (typ.getClass.getName == classOf[MonsterCard].getName && side == TheirSide){
          theirField.remove(position)
        }
        else if (side == MySide)
          myField.remove(position)

        if (typ.getClass.getName == classOf[SpellOrTrapCard].getName && side == TheirSide){
          myField.remove(position)
        }else {
          theirField.remove(position)
        }
      }*/
    }
    Logger.info(this, s"---- Observable Board ---- \n${myField.map(_.children).mkString(" ")}\n -----    -------    -----\n")
  }

  /**
    *
    * @param boardIndex of the overall field
    */
  def cardOcuppiedBy(boardIndex: Int): Card = {
    if (boardIndex < 12){
      (gameController.boards(TheirSide).spellTrapZones.toBuffer ++= gameController.boards(TheirSide).monsterZones)(boardIndex)
    }else {
      (gameController.boards(MySide).monsterZones.toBuffer ++= gameController.boards(MySide).spellTrapZones)(boardIndex % 12)
    }
  }

  def occupy(boardIndex: Int, card: Card): Unit = {
    if (boardIndex < 12){
      (gameController.boards(TheirSide).spellTrapZones.toBuffer ++= gameController.boards(TheirSide).monsterZones)(boardIndex) = card
    }else {
      (gameController.boards(MySide).monsterZones.toBuffer ++= gameController.boards(MySide).spellTrapZones)(boardIndex % 12) =  card
    }
  }

  //TODO comp;lete for deck and gy and other side.
  /**
    * Syncs front end with backend.
   */
  def syncField: Unit = {
    for (i <- gameController.boards(MySide).monsterZones.indices){
      if (gameController.boards(MySide).monsterZones(i) != null){
        val child = gameController.boards(MySide).monsterZones(i).imageView
        child.onMouseClicked = (e: input.MouseEvent) => {
          val poppy = new Popup(){
            autoFix = true
            autoHide = true
          }
          val contents = new VBox(){
            children.addAll(
              if (gameController.boards(MySide).monsterZones(i).position == MonsterPositions.Defense){
                new Button("Attack Mode"){
                  styleClass ++= Seq("summonButton")
                  onAction = (e: ActionEvent) => {
                    gameController.boards(MySide).monsterZones(i).position = MonsterPositions.Attack
                    myField(i).children = gameController.boards(MySide).monsterZones(i).imageView
                    syncField
                    poppy hide
                  }
                }.delegate
              } else {
                new Button("Defense Mode"){
                  styleClass ++= Seq("summonButton")
                  onAction = (e: ActionEvent) => {
                    gameController.boards(MySide).monsterZones(i).position = MonsterPositions.Defense
                    myField(i).children = gameController.boards(MySide).monsterZones(i).imageView
                    syncField
                    poppy hide
                  }
                }.delegate
              }
            )
          }
          if (gameController.boards(MySide).monsterZones(i).position == MonsterPositions.Attack)
            contents.children.add(
              new Button("Attack"){
                styleClass ++= Seq("summonButton")
                onAction = (e: ActionEvent) => {
                  
                  poppy hide
                }
              }.delegate
            )
          poppy.content add contents
          poppy show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
        }

        myField(i).children = Seq(
          child
        )
        //myField(i).onMouseClicked = (e: input.MouseEvent) => {
      }
    }

    for (i <- gameController.boards(MySide).spellTrapZones.indices){
      if (gameController.boards(MySide).spellTrapZones(i) != null) {
        myField(i).children = Seq(
          gameController.boards(MySide).spellTrapZones(i).imageView
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

      if (card != null)
        children = ObservableBuffer(card.imageView)

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
                val x: Card = gameController.boards(MySide).hand.draw
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
  private def visibleHandBoxChildren: Seq[ImageView] = gameController.boards(MySide).hand.map(addHandboxChild  )

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
                    gameController.boards(MySide).hand -= i
                    syncField
                    gameController.boards(MySide).cast(spellCard)
                    syncField
                    handBox.children = visibleHandBoxChildren
                    hide()
                  }
                }
                case trapCard: TrapCard => new Button("Set"){
                  styleClass = Seq("summonButton")
                  onAction = (e: ActionEvent) => {
                    gameController.boards(MySide).hand -= i
                    gameController.boards(MySide).set(trapCard)
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
                        gameController.boards(MySide).hand -= i
                        gameController.boards(MySide).summon(monster, MonsterPositions.Attack)
                        handBox.children = visibleHandBoxChildren
                        syncField
                        hide()
                      }
                    },
                    new Button("Set"){
                      styleClass = Seq("summonButton")
                      onAction = (e: ActionEvent) => {
                        gameController.boards(MySide).hand -= i
                        gameController.boards(MySide).summon(monster, MonsterPositions.Defense)
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

  val theirFieldPane = new FlowPane(){
    orientation = Orientation.Horizontal
    hgap = 5
    vgap = 5
    //maxWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
    children = theirField
  }

  val myFieldPane = new FlowPane(){
    orientation = Orientation.Horizontal
    hgap = 5
    vgap = 5
    //maxWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
    children = myField
  }

  def updateVisual: Unit = {
    visual.children = Seq(
      new HBox(){
        children = Seq(new ImageView(gameController.boards(TheirSide).hero.sprite) {fitWidth = 90; fitHeight = 90})
      },
      theirFieldPane,
      new HBox(){
        prefHeight = 20
        alignment = Pos.Center
        children = Seq(

        )
      },
      myFieldPane,
      //visibleHandBox
      new HBox(){
        children = Seq(handBox, new ImageView(gameController.boards(MySide).hero.sprite) {fitWidth = 90; fitHeight = 90})
      }
    )
  }

  val visual: VBox = new VBox(){
    styleClass ++= Seq("boardField")
    margin = Insets(20)
    prefWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
    vgrow = Priority.Never
    hgrow = Priority.Never
  }
  updateVisual

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
