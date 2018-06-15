package me.mysterymystery.duelingfactions.scene

import me.mysterymystery.duelingfactions.DuelingFactions
import javafx.event
import javafx.scene.image
import javafx.scene.input.MouseEvent
import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.EventManager
import me.mysterymystery.duelingfactions.eventprocesses.ListenTo
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.events.menu.MainMenuEvent
import scalafx.scene.media.MediaPlayer

object MainMenuScene extends SceneBuilder {
  {
    EventManager.get.registerListener(this)
  }

  override val get: Scene = new Scene() {
      root = new BorderPane() {
        top = new ImageView(new Image(new image.Image(getClass.getResourceAsStream("/skin/logo.png")))){
          fitWidth = 400
          fitHeight = 100
        }
        right = new VBox(){
          prefWidth = 300
          children = Seq(
            new Button("Switch to game scene"){
              styleClass ++= Seq("mainMenuButton")
              onAction = (e: event.ActionEvent) => DuelingFactions.changeScene(GameScene.get)
              onMouseEntered = (e: MouseEvent) => EventManager.get.fireEvent(MainMenuEvent.ButtonHover(this))
            },
            new Button("Switch to Deck Builder"){
              styleClass ++= Seq("mainMenuButton")
              onAction = (e: event.ActionEvent) => DuelingFactions.changeScene(DeckBuilderScene.get)
              onMouseEntered = (e: MouseEvent) => EventManager.get.fireEvent(MainMenuEvent.ButtonHover(this))
            }
          )
        }
      }

      stylesheets.addAll(
        getClass.getResource("/css/appSkin.css").toExternalForm,
        getClass.getResource("/css/boardSkin.css").toExternalForm,
        getClass.getResource("/css/cardSkin.css").toExternalForm
      )
    }

  @ListenTo
  def onButtonHover(event: MainMenuEvent.ButtonHover): Unit = {
    DuelingFactions.playSound("MainMenuButtonSound.wav")
  }
}
