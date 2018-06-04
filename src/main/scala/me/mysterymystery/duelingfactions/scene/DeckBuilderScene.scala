package me.mysterymystery.duelingfactions.scene
import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.event
import javafx.scene.image
import me.mysterymystery.duelingfactions.DuelingFactions
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards.{ExampleMonster, ExampleSpell}
import me.mysterymystery.duelingfactions.apiv2.guidependant.card._
import scalafx.geometry.Orientation
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ListView, SelectionMode, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout._

object DeckBuilderScene extends SceneBuilder {
  val cardListPane: ListView[Card] = new ListView[Card](Seq(new ExampleMonster, new ExampleSpell)){
    orientation = Orientation.Vertical
    selectionModel.value.setSelectionMode(SelectionMode.SINGLE)
    selectionModel.value.selectedItemProperty.addListener(new ChangeListener[Card] {
      override def changed(observable: ObservableValue[_ <: Card], oldValue: Card, newValue: Card): Unit = {
        setDescriptionBox(observable.getValue)
      }
    }
    )
  }

  val deckViewer: TilePane = new TilePane(){
    maxWidth = 500
    prefWidth = 400
  }

  override val get: Scene =  new Scene() {
    root = new BorderPane() {

      right = new VBox(){
        prefWidth = 300
        children = Seq(
          new HBox(){
            children = Seq(
              new TextField(){
                promptText = "Search"
              },
              new Button("Search")
            )
          },
          cardListPane
        )
      }
      left = cardViewer
      center = deckViewer
    }

    stylesheets.addAll(
      getClass.getResource("/css/appSkin.css").toExternalForm,
      getClass.getResource("/css/boardSkin.css").toExternalForm,
      getClass.getResource("/css/cardSkin.css").toExternalForm
    )
  }

  def setDescriptionBox(card: Card): Unit = {
    if (card != null){
      cardViewerPictureBox.image = card.sprite
      descBox.text = card.cardText
    }else {
      cardViewerPictureBox.image = Card.empty.sprite
      descBox.text = Card.empty.cardText
    }
  }
}
