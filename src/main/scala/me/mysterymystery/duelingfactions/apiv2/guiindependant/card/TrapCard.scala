package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

@SerialVersionUID(123L)
trait TrapCard extends SpellOrTrapCard {
  override def toString: String = super.toString + s"[Trap]"
}
