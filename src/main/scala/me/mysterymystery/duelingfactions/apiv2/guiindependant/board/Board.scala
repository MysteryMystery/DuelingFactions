package me.mysterymystery.duelingfactions.apiv2.guiindependant.board

import me.mysterymystery.duelingfactions.apiv2.guidependant.game.Game
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections.{Deck, Graveyard, Hand}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.FieldPositions
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.{Card, MonsterCard, SpellCard, SpellOrTrapCard}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.exception.{BoardFullException, CardNotFoundException}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.MonsterPositions.{Attack, Defense, MonsterPosition}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.enums.FieldPositions.{Field, FieldPosition, Hand}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.hero.Hero
import me.mysterymystery.duelingfactions.apiv2.guiindependant.pimping.PimpedOption._
import me.mysterymystery.duelingfactions.apiv2.guidependant.hero._
import me.mysterymystery.duelingfactions.apiv2.guiindependant.logging.Logger
import scalafx.collections.ObservableBuffer

import scala.collection.mutable

/**
  * GUI independant implementation of the board.
  */
class Board (val side: BoardSides.BoardSide, val deck: Deck, val linkedGameController: GameController) {
  private val _monsterZones: ObservableBuffer[MonsterCard] = ObservableBuffer(null, null, null, null, null)
  _monsterZones.onChange(
    (source, changes) => {
      linkedGameController.sendFieldChange[MonsterCard](changes, classOf[MonsterCard], side)
      changes.foreach(c => Logger.info(this, s"Monster zone change: $c"))
    }
  )
  private val _spellTrapZones: ObservableBuffer[SpellOrTrapCard] = ObservableBuffer(null, null, null, null, null)
  _spellTrapZones.onChange(
    (source, changes) => {
      linkedGameController.sendFieldChange[SpellOrTrapCard](changes, classOf[SpellOrTrapCard], side)
      changes.foreach(c => Logger.info(this, s"Monster zone change: $c"))
    }
  )
  val hand: Hand = new Hand(deck)
  val graveyard: Graveyard = new Graveyard
  val hero: Hero = new Hero {
    override def name: String = "Jaina"

    override def spriteFileName: String = "FlameDemon.png"
  }

  def monsterZones: List[MonsterCard] = _monsterZones.toList
  def spellTrapZones: List[SpellOrTrapCard] = _spellTrapZones.toList
  
  @throws[BoardFullException]
  def summon(card: MonsterCard, position: MonsterPosition): Unit = {
    for (m <- _monsterZones.indices){
      if (_monsterZones(m) == null){
        card.position = position
        _monsterZones(m) = card
        return
      }
    }
    throw BoardFullException("Monster zones are full!")
  }

  @throws[BoardFullException]
  def cast(card: SpellCard): Unit = {
    for (z <- _spellTrapZones.indices){
      if (_spellTrapZones(z) == null){
        _spellTrapZones(z) = card
        activate(_spellTrapZones(z), FieldPositions.Field)
        return
      }
    }
    throw BoardFullException("Spell Trap zones are full!")
  }

  @throws[BoardFullException]
  def set(card: SpellOrTrapCard): Unit = {
    for (z <- _spellTrapZones.indices){
      if (_spellTrapZones(z) == null){
        card.isSet = true
        _spellTrapZones(z) = card
        return
      }
    }
    throw BoardFullException("Spell Trap zones are full!")
  }

  def activate(card: SpellOrTrapCard, from: FieldPosition): Unit = {
    card.action(GameController.gameController)
    if (from == FieldPositions.Hand){
      discard(card.asInstanceOf[Card])
    }else if (from == FieldPositions.Field){
      destroy(card)
    }

    if (from != FieldPositions.Graveyard)
      graveyard += card
  }

  @throws[CardNotFoundException]
  def destroy(card: MonsterCard): Unit = {
    for (m <- _monsterZones.indices) {
      if (_monsterZones(m) == card) {
        _monsterZones(m) = null
        graveyard += _monsterZones(m)
        return
      }
      throw CardNotFoundException()
    }
  }

  @throws[CardNotFoundException]
  def destroy(card: SpellOrTrapCard): Unit = {
    for (m <- _spellTrapZones.indices){
      if (_spellTrapZones(m) == card){
        _spellTrapZones(m) = null
        graveyard += _spellTrapZones(m)
        return
      }
    }
    throw CardNotFoundException()
  }

  @throws[CardNotFoundException]
  def discard(card: Card): Unit = {
    if (hand contains card){
      hand -= card
      graveyard += card
    }
  }
}
