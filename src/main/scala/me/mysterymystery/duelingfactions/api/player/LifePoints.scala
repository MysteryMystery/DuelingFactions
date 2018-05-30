package me.mysterymystery.duelingfactions.api.player

import scalafx.scene.control.{Label, ProgressBar, ProgressIndicator}

class LifePoints(private[this] var value: Int = 16000) {
  private val startingLP: Float = value

  val progBar = new ProgressBar(){
    styleClass ++= Seq("lifePointsBar")
    progress = value
  }
  val progressIndicator = new Label(){
    text = value.toString
  }

  def + (int: Int): Unit = {
    value += int
    progBar.progress = value / startingLP
    progressIndicator.text = progBar.progress.toString()
  }

  def - (int: Int): Unit = {
    value -= int
    progBar.progress = value / startingLP
    progressIndicator.text = value.toString
  }

  def set(int: Int): Unit = {
    value = int
    progBar.progress = value / startingLP
    progressIndicator.text = value.toString
  }

  def isLoss: Boolean = value <= 0

  def get: Int = value

  def copy: LifePoints = new LifePoints(value)

  override def toString: String = value.toString
}
