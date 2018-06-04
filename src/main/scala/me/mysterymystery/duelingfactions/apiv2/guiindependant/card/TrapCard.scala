package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

trait TrapCard extends SpellOrTrapCard {
  override def toString: String = super.toString + s"[Trap]"
}
