package me.mysterymystery.duelingfactions.api.board.locations

import me.mysterymystery.duelingfactions.api.card.SpellOrTrapCard
import me.mysterymystery.duelingfactions.api.config.Config

class SpellTrapZone extends BoardZone {
  prefHeight = Config.cardHeight
  prefWidth = Config.cardHeight
  styleClass ++= Seq("spellSlot")


  def occupy (card: SpellOrTrapCard): Unit = occupiedWith = card
  def deoccupy: SpellOrTrapCard = {
    val c = occupiedWith
    occupiedWith = null
    c.asInstanceOf[SpellOrTrapCard]
  }
}
