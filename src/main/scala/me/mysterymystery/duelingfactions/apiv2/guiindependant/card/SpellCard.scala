package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

trait SpellCard extends SpellOrTrapCard {
  override def toString: String = super.toString + s"[Spell]"
}
