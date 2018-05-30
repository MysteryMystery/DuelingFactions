package me.mysterymystery.duelingfactions.api.board.locations

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import me.mysterymystery.duelingfactions.api.card.Card
import me.mysterymystery.duelingfactions.api.card.cardcollection.Graveyard
import me.mysterymystery.duelingfactions.api.config.Config

class GraveyardZone extends BoardZone {
  prefWidth = Config.cardWidth
  styleClass ++= Seq("graveSlot")
  private var occupiedWith: Graveyard = new Graveyard

  onMouseClicked = (e: MouseEvent) => {
    view
  }

  def view: Unit = {
    //Launch pane to view graveyard
  }

  def + (card: Card): Unit = occupiedWith += card
  def - (card: Card): Unit = occupiedWith -= card

  override def occupied: Boolean = true
}
