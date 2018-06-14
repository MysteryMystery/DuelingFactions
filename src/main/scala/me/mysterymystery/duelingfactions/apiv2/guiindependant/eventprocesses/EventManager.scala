package me.mysterymystery.duelingfactions.apiv2.guiindependant.eventprocesses

import java.lang.reflect.{Method, Type}

import com.google.common.base.Predicate
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.util.{ClasspathHelper, ConfigurationBuilder}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConverters._

object EventManager {
  private[this] var _e: EventManager = _
  private[eventprocesses] def set(e: EventManager) = _e = e
  def get: EventManager = _e
  def init: Unit = _e = new EventManager
}

/**
  * Manages events dispatched by the app.
  */
class EventManager {
  private var listeners: mutable.ListBuffer[(Any, List[Method])] = ListBuffer()

  //fixme not working
  def fireEvent(e: Event): Unit = {
    val eventType = e.getClass
    for (listener <- listeners){
      val methods = listener._2
      val obj = listener._1

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
    listeners += Tuple2(obj, annotationsScanner(obj.getClass).getMethodsAnnotatedWith(classOf[me.mysterymystery.duelingfactions.eventprocesses.ListenTo]).asScala.toList)
  }

  /**
    *
    * @param obj Object to be deregistered
    */
  def deregisterListener(obj: Any): Unit = {
    listeners -= Tuple2(obj, annotationsScanner(obj.getClass).getMethodsAnnotatedWith(classOf[me.mysterymystery.duelingfactions.eventprocesses.ListenTo]).asScala.toList)
  }

  private def getListenerMethods(annotationsScanner: Reflections): Unit = {
    annotationsScanner.getMethodsAnnotatedWith(classOf[me.mysterymystery.duelingfactions.eventprocesses.ListenTo])
  }

  private def annotationsScanner(clazz: Class[_]): Reflections = {
    val className = this.getClass.getCanonicalName

    val filter: Predicate[String] = (input: String) => input.startsWith(className)

    new Reflections(
      new ConfigurationBuilder()
        //.setUrls(ClasspathHelper.forPackage("me.mysterymystery.duelingfactions"))
        .setUrls(ClasspathHelper.forClass(clazz))
        .filterInputsBy(filter)
        .setScanners(new MethodAnnotationsScanner)
    )
  }
}
