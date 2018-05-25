package me.mysterymystery.duelingfactions.api.player

class LifePoints(private[this] var value: Int = 16000) {

  def + (int: Int): Unit = value + int

  def - (int: Int): Unit = value - int

  def set(int: Int): Unit = value = int

  def isLoss: Boolean = value <= 0

  def get: Int = value

  def copy: LifePoints = new LifePoints(value)

  override def toString: String = value.toString
}
