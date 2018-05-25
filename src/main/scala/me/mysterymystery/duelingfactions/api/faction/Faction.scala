package me.mysterymystery.duelingfactions.api.faction

trait Faction {
  def heroName: String
  def act: () => Unit
}
