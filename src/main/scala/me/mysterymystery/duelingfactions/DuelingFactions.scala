package me.mysterymystery.duelingfactions

import java.io.File

import javafx.scene.image
import me.mysterymystery.duelingfactions.api.card.cardcollection.Deck
import me.mysterymystery.duelingfactions.api.card.cardlist.ExampleCard
import me.mysterymystery.duelingfactions.api.config.Config
import me.mysterymystery.duelingfactions.apiv2.guiindependant.config.Database
import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.EventManager
import me.mysterymystery.duelingfactions.apiv2.guiindependant.webrequest.ServerAPI.get
import me.mysterymystery.duelingfactions.scene.{GameScene, MainMenuScene}
import net.liftweb.json.JsonAST.{JArray, JField}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.layout.{BorderPane, GridPane, HBox, VBox}
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.stage.StageStyle
import net.liftweb.json._

object DuelingFactions extends JFXApp {
  Database.init
  EventManager.init

  stage = new PrimaryStage{
    title = "Dueling Factions"
    scene = MainMenuScene.get
    icons.add(new Image(new image.Image(getClass.getResourceAsStream("/skin/icon.png"))))
    //initStyle(StageStyle.Undecorated)
  }

  var mediaPlayer: MediaPlayer = _
  //playSound("MainMusic.wav")

  new Deck(Seq(new ExampleCard))

  def playSound(soundName: String): Unit = {
    try{
      mediaPlayer.stop()
    } catch {case _: Throwable =>}
    val mf = getClass.getResource(s"/music/$soundName").toExternalForm
    val sound: Media = new Media(new javafx.scene.media.Media(mf))
    mediaPlayer = new MediaPlayer(sound)
    mediaPlayer.play
  }

  def changeScene(scene: Scene): Unit = stage.scene = scene

  def close: Unit = stage.close()

  import me.mysterymystery.duelingfactions.apiv2.guiindependant.webrequest.ServerAPI.RequestParams
  me.mysterymystery.duelingfactions.apiv2.guiindependant.webrequest.ServerAPI.get(RequestParams.Clients)
  println(compactRender(get(RequestParams.Clients) \\ "data"))
}