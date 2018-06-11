package me.mysterymystery.duelingfactions.apiv2.guiindependant.pimping

object PimpedInt {
  implicit class IntOps(int: Int) {
    def times(func: () => Unit): Unit = {
      for (i <- 0 until int){
        func()
      }
    }


  }
}
