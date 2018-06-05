package me.mysterymystery.duelingfactions.apiv2.guiindependant.config

import java.io.File

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card

class Database {
  private val databaseFile: File = new File("/DuelingFactions.yml")
  if (!databaseFile.exists()) databaseFile.createNewFile()

  def load: Map[String, List[Card]] = {
    Map()
  }
}
