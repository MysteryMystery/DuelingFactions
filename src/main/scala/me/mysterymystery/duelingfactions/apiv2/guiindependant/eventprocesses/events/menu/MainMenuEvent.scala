package me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.events.menu

import me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses.Event
import scalafx.scene.control.Button

object MainMenuEvent extends Event {
  case class ButtonHover(button: Button) extends Event
}
