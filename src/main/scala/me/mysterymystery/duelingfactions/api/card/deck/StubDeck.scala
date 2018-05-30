package me.mysterymystery.duelingfactions.api.card.deck

import me.mysterymystery.duelingfactions.api.card.cardcollection.Deck
import me.mysterymystery.duelingfactions.api.card.cardlist.{ExampleCard, ExampleSpell}

object StubDeck extends Deck (
  Seq(
    new ExampleCard,
    new ExampleCard,
    new ExampleCard,
    new ExampleCard,
    new ExampleCard,
    new ExampleCard,
    new ExampleCard,
    new ExampleSpell,
    new ExampleSpell,
    new ExampleSpell,
    new ExampleSpell,
    new ExampleSpell,
    new ExampleSpell
  )
)
