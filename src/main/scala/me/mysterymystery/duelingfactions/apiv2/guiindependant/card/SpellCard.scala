package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

@SerialVersionUID(123L)
trait SpellCard extends SpellOrTrapCard {
  override def toString: String = super.toString + s"[Spell]"
}
