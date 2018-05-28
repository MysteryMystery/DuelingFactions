package me.mysterymystery.duelingfactions.api.board.locations

import me.mysterymystery.duelingfactions.api.card.{Card, MonsterCard}
import scalafx.scene.image.ImageView
import scalafx.scene.layout.Pane
import me.mysterymystery.duelingfactions.api.config.Config

class MonsterZone extends BoardZone {
  prefHeight = Config.cardHeight
  prefWidth = Config.cardHeight
  styleClass ++= Seq("monsterSlot")

  def occupy (card: MonsterCard): Unit = {
    occupiedWith = card
    children = Seq(new ImageView(card.sprite) {fitWidth = 90; fitHeight = 120})
  }

  def deoccupy: MonsterCard = {
    val c = occupiedWith
    occupiedWith = null
    c.asInstanceOf[MonsterCard]
  }
}
