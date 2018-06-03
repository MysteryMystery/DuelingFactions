package me.mysterymystery.duelingfactions.apiv2.guiindependant.card

trait SpellOrTrapCard extends Card {
  private var _isSet = false

  /**
    *
    * @return Whether the card is set face down or not
    */
  def isSet: Boolean = _isSet

  /**
    *
    * @param bool to set the card or not
    */
  def isSet_=(bool: Boolean) = _isSet = bool
}
