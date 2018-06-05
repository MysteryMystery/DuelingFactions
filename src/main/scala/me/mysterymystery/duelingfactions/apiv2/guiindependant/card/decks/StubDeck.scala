package me.mysterymystery.duelingfactions.apiv2.guiindependant.card.decks

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.cards.{ExampleMonster, ExampleSpell}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections.Deck
import me.mysterymystery.duelingfactions.apiv2.guiindependant.board.BoardSides.NoSide

class StubDeck extends Deck (
  Seq(
    new ExampleMonster(NoSide),
    new ExampleMonster(NoSide),
    new ExampleMonster(NoSide),
    new ExampleMonster(NoSide),
    new ExampleMonster(NoSide),
    new ExampleMonster(NoSide),
    new ExampleMonster(NoSide),
    new ExampleMonster(NoSide),
    new ExampleSpell(NoSide),
    new ExampleSpell(NoSide),
    new ExampleSpell(NoSide),
    new ExampleSpell(NoSide),
    new ExampleSpell(NoSide),
    new ExampleSpell(NoSide),
  )
)
