package me.mysterymystery.duelingfactions.api.util

object PimpedOption {
  implicit class OptionOpts[T] (option: Option[T]){
    def ifPresent(func: T => Any): Any = {
      if (option.isDefined)
        func(option.get)
    }
  }
}
