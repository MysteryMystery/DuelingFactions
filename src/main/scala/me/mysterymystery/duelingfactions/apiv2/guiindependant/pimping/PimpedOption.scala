package me.mysterymystery.duelingfactions.apiv2.guiindependant.pimping

object PimpedOption {
  implicit class OptionOpts[T] (option: Option[T]){
    def ifPresent(func: T => Any): Any = {
      if (option.isDefined)
        func(option.get)
    }
  }
}