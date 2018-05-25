package me.mysterymystery.duelingfactions

import javafx.scene.image
import me.mysterymystery.duelingfactions.scene.GameScene
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
import scalafx.stage.StageStyle

object DuelingFactions extends JFXApp {
  stage = new PrimaryStage{
    title = "Dueling Factions"
    scene = GameScene.get
  }
}