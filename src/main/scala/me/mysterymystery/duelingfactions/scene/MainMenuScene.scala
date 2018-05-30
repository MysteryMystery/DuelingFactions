package me.mysterymystery.duelingfactions.scene

import me.mysterymystery.duelingfactions.DuelingFactions
import javafx.event
import javafx.scene.image
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{BorderPane, HBox, VBox}

object MainMenuScene extends SceneBuilder {
  override def get: Scene = {

    new Scene() {
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
  }
}
