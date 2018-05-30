package me.mysterymystery.duelingfactions.api.board

import javafx.event.ActionEvent
import javafx.scene.image
import javafx.scene.input.MouseEvent
import me.mysterymystery.duelingfactions.DuelingFactions
import me.mysterymystery.duelingfactions.api.exception.BoardFullException
import me.mysterymystery.duelingfactions.api.board.faction.Faction
import me.mysterymystery.duelingfactions.api.board.locations._
import me.mysterymystery.duelingfactions.api.card.cardcollection.{Deck, HiddenHand, VisibleHand}
import me.mysterymystery.duelingfactions.api.card._
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.api.player.LifePoints
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.stage.Popup

import scala.reflect.runtime.{universe => ru}

class Board(private val myFaction: Faction, private var myDeck: Deck, private val theirFaction: Faction, private var theirDeck: Deck) {
  val myLifePoints = myFaction.lifepoints
  val theirLifePoints = theirFaction.lifepoints

  val myGraveyardZone = new GraveyardZone
  val myDeckZone = new DeckZone(myDeck)
  val mySide = List[BoardZone](
    new MonsterZone  , new MonsterZone  , new MonsterZone  , new MonsterZone  , new MonsterZone  , myGraveyardZone,
    new SpellTrapZone, new SpellTrapZone, new SpellTrapZone, new SpellTrapZone, new SpellTrapZone, myDeckZone
  )

  val theirGraveyardZone: GraveyardZone = new GraveyardZone
  val theirDeckZone: DeckZone = new DeckZone(myDeck)
  val theirSide: Seq[BoardZone] = List[BoardZone](
    theirDeckZone     , new SpellTrapZone, new SpellTrapZone, new SpellTrapZone, new SpellTrapZone, new SpellTrapZone,
    theirGraveyardZone, new MonsterZone  , new MonsterZone  , new MonsterZone  , new MonsterZone  , new MonsterZone
  )

  val myHand: VisibleHand = new VisibleHand(myDeck)
  val theirHand: HiddenHand = new HiddenHand(theirDeck)

  myHand.draw(6)
  myLifePoints - 1000

  /**
    * All children and their actions of the visible hand.
    * @return All children
    */
  def visibleHandBoxChildren: Seq[ImageView] = myHand.map(i => {
    new ImageView(i.sprite){
      fitWidth = Config.cardWidth
      fitHeight = Config.cardHeight
      onMouseEntered = (e: MouseEvent) => {
        fitWidth = Config.cardWidth + 10
        fitHeight = Config.cardHeight + 10

        GameScene.cardViewerPictureBox.image = i.sprite
        GameScene.descBox.text = i.cardText
      }
      onMouseExited  = (e: MouseEvent) => {
        fitWidth = Config.cardWidth
        fitHeight = Config.cardHeight

        GameScene.descBox.text = ""
      }
      onMouseClicked = (e: MouseEvent) => {
        val pop = new Popup(){
          //x =  e.getX
          //y = e.getY
          autoFix = true

          content.add(new VBox(){
            children = Seq(
              if (/*i.getClass.isAssignableFrom(classOf[SpellOrTrapCard])*/ i.isInstanceOf[SpellOrTrapCard])
                new Button("Cast") {
                  styleClass = Seq("summonButton")
                  onAction = (e: ActionEvent) => {
                    myHand -= i
                    summonSpellTrap(i.asInstanceOf[SpellOrTrapCard], BoardSides.MySide)
                    if (i.isInstanceOf[SpellCard]){
                      i.asInstanceOf[SpellCard].action
                      sendToMyGraveyard(i)
                    }
                    hide()
                  }
                }
              else
                new Button("Summon") {
                  styleClass = Seq("summonButton")
                  onAction = (e: ActionEvent) => {
                    myHand -= i
                    summonMonster(i.asInstanceOf[MonsterCard], BoardSides.MySide)
                    visibleHandBox.children = visibleHandBoxChildren
                    hide()
                  }
                },
              new Button("Cancel"){
                styleClass = Seq("summonButton")
                onAction = (e: ActionEvent) => {
                  hide()
                }
              }
            )
          }.delegate
          )

          show(visual, e.getScreenX, e.getScreenY)
        }

      }
    }
  })

  /**
    * On draw -> needs refreshing
    */
  val visibleHandBox: FlowPane = new FlowPane(){
    hgap = -10
    children = visibleHandBoxChildren
  }

  val invisibleHandBox: FlowPane = new FlowPane(){
    hgap = -10
    children = theirHand.map(i => {
      new ImageView(i.sprite){
        fitWidth = Config.cardWidth
        fitHeight = Config.cardHeight
        onMouseEntered = (e: MouseEvent) => {
          fitWidth = Config.cardWidth + 10
          fitHeight = Config.cardHeight + 10
        }
        onMouseExited  = (e: MouseEvent) => {
          fitWidth = Config.cardWidth
          fitHeight = Config.cardHeight
        }
      }
    })
  }

  /**
    * The board graphics. It is a val and not def due to observable values.
    */
  val visual: VBox = new VBox(){
    margin = Insets(20)
    prefWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
    vgrow = Priority.Never
    hgrow = Priority.Never
    children = Seq(
      new HBox(){
        children = Seq(new ImageView(theirFaction.sprite) {fitWidth = 90; fitHeight = 90}, invisibleHandBox)
      },
      new FlowPane(){
        orientation = Orientation.Horizontal
        hgap = 5
        vgap = 5
        //maxWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
        children = theirSide
      },
      new HBox(){
        prefHeight = 20
        alignment = Pos.Center
        children = Seq(
          myLifePoints.progBar, myLifePoints.progressIndicator,
          theirLifePoints.progressIndicator, theirLifePoints.progBar
        )
      },
      new FlowPane(){
            orientation = Orientation.Horizontal
            hgap = 5
            vgap = 5
            //maxWidth = (Config.cardHeight * 5) + Config.cardWidth + (5 * 5)
            children = mySide
          },
      //visibleHandBox
      new HBox(){
        children = Seq(visibleHandBox, new ImageView(myFaction.sprite) {fitWidth = 90; fitHeight = 90})
      }
    )
  }

  @throws[BoardFullException]
  def summonMonster(card: MonsterCard, side: BoardSides.BoardSide): Unit = {
    /*
    If occupied monster slots == 5 throw exception else place in empty slot.
     */
    if (side == BoardSides.MySide){
      val x = mySide.filter(_.isInstanceOf[MonsterZone]).find(!_.occupied)
      if (x.isDefined)
        x.get.asInstanceOf[MonsterZone].occupy(card)
      else
        throw BoardFullException()
    }else {
      val x = theirSide.filter(_.isInstanceOf[MonsterZone]).find(!_.occupied)
      if (x.isDefined)
        x.get.asInstanceOf[MonsterZone].occupy(card)
      else
        throw BoardFullException()
    }
  }

  @throws[BoardFullException]
  def summonSpellTrap(card: SpellOrTrapCard, side: BoardSides.BoardSide): Unit = {
    if (side == BoardSides.MySide){
      val x = mySide.filter(_.isInstanceOf[SpellTrapZone]).find(!_.occupied)
      if (x.isDefined)
        x.get.asInstanceOf[SpellTrapZone].occupy(card)
      else
        throw BoardFullException()
    }else {
      val x = theirSide.filter(_.isInstanceOf[SpellTrapZone]).find(!_.occupied)
      if (x.isDefined)
        x.get.asInstanceOf[SpellTrapZone].occupy(card)
      else
        throw BoardFullException()
    }
  }

  def sendToMyGraveyard(card: Card): Unit = {

  }

  object BoardSides extends Enumeration {
    type BoardSide = Value
    val
      MySide,
      TheirSide = Value
  }
}
