package me.mysterymystery.duelingfactions

import javafx.scene.image
import me.mysterymystery.duelingfactions.api.board.Deck
import me.mysterymystery.duelingfactions.api.card.cardlist.ExampleCard
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.scene.{GameScene, MainMenuScene}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
import scalafx.stage.StageStyle

object DuelingFactions extends JFXApp {
  stage = new PrimaryStage{
    title = "Dueling Factions"
    scene = MainMenuScene.get
    //initStyle(StageStyle.Undecorated)
  }

  new Deck(Seq(new ExampleCard))

  def changeScene(scene: Scene): Unit = stage.scene = scene

  def close: Unit = stage.close()
}