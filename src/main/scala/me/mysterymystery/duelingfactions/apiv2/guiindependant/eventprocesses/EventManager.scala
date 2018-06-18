package me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses

import java.lang.reflect.{Method, Type}

import com.google.common.base.Predicate
import me.mysterymystery.duelingfactions.apiv2.guiindependant.logging.Logger
import me.mysterymystery.duelingfactions.eventprocesses.ListenTo
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.util.{ClasspathHelper, ConfigurationBuilder, FilterBuilder}
import org.reflections.ReflectionUtils._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

object EventManager {
  private var _e: EventManager = _
  private[eventprocesses] def set(e: EventManager) = _e = e
  def get: EventManager = _e
  def init: Unit = _e = new EventManager
}

/**
  * Manages events dispatched by the app.
  */
class EventManager {
  private var listeners: mutable.ListBuffer[(Any, List[Method])] = ListBuffer()

  /**
    *
    * @param e Event to pass to all accepting listeners.
    */
  def fireEvent(e: Event): Unit = {
    println(listeners.mkString("\n"))
    val eventType = e.getClass
    println ( s"Fired Event: ${eventType.getSimpleName}")
    for (listener <- listeners){
      val methods = listener._2
      val obj = listener._1

      println(s" Object: ${obj.getClass.getSimpleName} \n Methods: ${methods.map(_.getName).mkString(", ")} \n")

      for (method <- methods) {
        if (method.getParameterCount == 1 && method.getParameterTypes.head == eventType){
          method.invoke(obj, e)
        }
      }
    }
  }

  /**
    *
    * @param obj Object to be registered
    */
  def registerListener(obj: Any): Unit = {
    //listeners += Tuple2(obj, annotationsScanner(obj.getClass).getMethodsAnnotatedWith(classOf[me.mysterymystery.duelingfactions.eventprocesses.ListenTo]).asScala.toList)
    //listeners += Tuple2(obj, getAllMethods(obj.getClass, withAnnotation(classOf[ListenTo])).asScala.toList)

    val x: Array[Method] = obj.getClass.getMethods.filter(_.isAnnotationPresent(classOf[ListenTo]))
    listeners += Tuple2(obj, x.toList)
  }

  /**
    *
    * @param obj Object to be deregistered
    */
  def deregisterListener(obj: Any): Unit = {
    listeners -= Tuple2(obj, obj.getClass.getMethods.filter(_.isAnnotationPresent(classOf[ListenTo])).toList)
  }

}
