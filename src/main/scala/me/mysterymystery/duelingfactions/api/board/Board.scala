package me.mysterymystery.duelingfactions.api.board

import me.mysterymystery.duelingfactions.api.Exception.BoardFullException
import me.mysterymystery.duelingfactions.api.board.locations.BoardZone
import me.mysterymystery.duelingfactions.api.card.{Card, MonsterCard, SpellOrTrapCard}
import scalafx.scene.layout.GridPane

class Board(private var myDeck: Deck, private var theirDeck: Deck) {
  private val mySide = Map[BoardZone, Card](

  )
  private val myGraveyard = new Graveyard()

  @throws[BoardFullException]
  def summonMonster(card: MonsterCard): Unit = {
    /*
    If occupied monster slots == 5 throw exception else place in empty slot.
     */
  }

  def SummonSpellTrap(card: SpellOrTrapCard): Unit = {

  }

  def getVisual: GridPane = {
    new GridPane()
  }
}
