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
import me.mysterymystery.duelingfactions.api.card.cardlist.ExampleCard
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.api.player.LifePoints
import me.mysterymystery.duelingfactions.scene.GameScene
import me.mysterymystery.duelingfactions.api.util.PimpedOption._
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.stage.Popup

import scala.reflect.runtime.{universe => ru}

class Board(private val myFaction: Faction, private var myDeck: Deck, private val theirFaction: Faction, private var theirDeck: Deck) {
  private val myLifePoints = myFaction.lifepoints
  private val theirLifePoints = theirFaction.lifepoints

  private val myGraveyardZone = new GraveyardZone
  private val myDeckZone = new DeckZone(myDeck)
  private val mySide = List[BoardZone](
    new MonsterZone  , new MonsterZone  , new MonsterZone  , new MonsterZone  , new MonsterZone  , myGraveyardZone,
    new SpellTrapZone, new SpellTrapZone, new SpellTrapZone, new SpellTrapZone, new SpellTrapZone, myDeckZone
  )

  private val theirGraveyardZone: GraveyardZone = new GraveyardZone
  private val theirDeckZone: DeckZone = new DeckZone(myDeck)
  private val theirSide: List[BoardZone] = List[BoardZone](
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
  private def visibleHandBoxChildren: Seq[ImageView] = myHand.map(i => {
    new ImageView(i.sprite){
      fitWidth = Config.cardWidth
      fitHeight = Config.cardHeight
      onMouseEntered = (e: MouseEvent) => {
        fitWidth = Config.cardWidth + 10
        fitHeight = Config.cardHeight + 10

        //GameScene.cardViewerPictureBox.image = i.sprite
        //GameScene.descBox.text = i.cardText
      }
      onMouseExited  = (e: MouseEvent) => {
        fitWidth = Config.cardWidth
        fitHeight = Config.cardHeight

        //GameScene.descBox.text = ""
      }
      onMouseClicked = (e: MouseEvent) => {
        new Popup(){
          //x =  e.getX
          //y = e.getY
          autoFix = true
          autoHide = true

          content.add(new VBox(){
            children = Seq(
              if (/*i.getClass.isAssignableFrom(classOf[SpellOrTrapCard])*/ i.isInstanceOf[SpellOrTrapCard])
                new Button("Cast") {
                  styleClass = Seq("summonButton")
                  onAction = (e: ActionEvent) => {
                    myHand -= i
                    summonSpellTrap(i.asInstanceOf[SpellOrTrapCard], BoardSides.MySide)
                    visibleHandBox.children = visibleHandBoxChildren
                    //FIXME doesnt work -> check for how to check that SpellTrapCard is SpellCard
                    try {
                      //FIXME  needs to be .asInstanceOf[SpellCard].action(BOARD)
                      i.asInstanceOf[SpellCard].action
                      sendToMyGraveyardFromMyBoard(i)
                    } catch {case _: Throwable => }
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
                }
            )
          }.delegate
          )
          show(DuelingFactions.stage, e.getScreenX, e.getScreenY)
        }
      }
    }
  })

  /**
    * On draw -> needs refreshing
    */
  private val visibleHandBox: FlowPane = new FlowPane(){
    hgap = -10
    children = visibleHandBoxChildren
  }

  private val invisibleHandBox: FlowPane = new FlowPane(){
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
    styleClass ++= Seq("boardField")
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

  @Deprecated
  def sendToMyGraveyardFromMyBoard(card: Card): Unit = {
    card match {
      case _: MonsterCard =>
        val zones: Seq[MonsterZone] = mySide.filter(_.isInstanceOf[MonsterZone]).map(_.asInstanceOf[MonsterZone])
        if (zones.exists(_.peek == card)) {
          zones.head.deoccupy
          myGraveyardZone.+(card)
        }
      case _: SpellOrTrapCard =>
        val zones = mySide.filter(_.isInstanceOf[SpellTrapZone]).map(_.asInstanceOf[SpellTrapZone])
        if (zones.exists(_.peek == card)) {
          zones.head.deoccupy
          myGraveyardZone.+(card)
        }
      case _ =>
    }
  }

  def sendToGraveyard(card: Card, side: BoardSides.BoardSide, fromWhere: BoardLocations.BoardLocation): Unit = {
    if (fromWhere == BoardLocations.MyField || fromWhere == BoardLocations.TheirField){
      var s: List[BoardZone] = null
      side match {
        case BoardSides.MySide => s = mySide
        case BoardSides.TheirSide => s = theirSide
      }
      if (side == BoardSides.MySide)
        s = mySide
      else
        s = theirSide

      val zones = s.filter(_.peek.orNull == card)
      if (zones.exists(_.peek.orNull == card)) {
        zones.head match{
          case mz: MonsterZone => mz.deoccupy
          case stz: SpellTrapZone => stz deoccupy
        }
        side match {
          case BoardSides.MySide => myGraveyardZone + card
          case BoardSides.TheirSide => theirGraveyardZone + card
        }
      }
    } else if (fromWhere == BoardLocations.MyHand || fromWhere == BoardLocations.TheirHand){
      //TODO complete
    }
  }

  private def getMe = this

  object BoardSides extends Enumeration {
    type BoardSide = Value
    val
      MySide,
      TheirSide = Value
  }

  object BoardLocations extends Enumeration {
    type BoardLocation = Value
    val
      MyHand,
      TheirHand,
      MyField,
      TheirField = Value
  }

  /* Game Loop stuff*/
  private def drawPhase: Unit = {

  }

  private def startOfTurn: Unit = {
    //board.mySide.foreach()
    mySide.filter(_.occupied).foreach(c => c.peek.ifPresent(_.onStartOfTurn(this)))
  }

  private def endPhase: Unit = {
    mySide.filter(_.occupied).foreach(c => c.peek.ifPresent(_.onEndOfTurn(this)))
  }

  /**
    * Game loop
    */
  def doLoop: Unit = {
    //for each zone containing card, register popup allowing attacking etc.
    // then de register those who dont
    drawPhase
    startOfTurn
    endPhase
    //blah
  }

  summonMonster(new ExampleCard, BoardSides.MySide)
  sendToMyGraveyardFromMyBoard(new ExampleCard)
}
