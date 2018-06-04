package me.mysterymystery.duelingfactions.apiv2.guiindependant.logging

import java.util.Date

object Logger {
  private val format = "[{type}][{source}] {msg}"
  def info(source: Any, msg: String): Unit = {
    println(
      format
        .replaceAll("\\{type\\}", "info")
        .replaceAll("\\{source\\}", source.getClass.getSimpleName)
        .replaceAll("\\{msg\\}", msg)
    )
  }
}
