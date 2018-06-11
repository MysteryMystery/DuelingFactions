package me.mysterymystery.duelingfactions.apiv2.guiindependant.config

import java.io._

import scala.collection.JavaConverters._
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.{Card, MonsterCard, SpellCard, TrapCard}
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections.Deck
import org.yaml.snakeyaml.{DumperOptions, Yaml}

import scala.collection.mutable

object Database {
  private var _d: Database = _
  def get: Database = _d
  def init: Unit = if (get == null) set (new Database)
  private[config] def set(value: Database) = _d = value
}

class Database {
  private val databaseFile: File = new File("DuelingFactions.yml")
  if (!databaseFile.exists()) setup

  def setup: Unit = {
    databaseFile.createNewFile()
    val toDump = mutable.Map[String, Object](
      "decks" -> Map[String, java.util.ArrayList[String]]().asJava
    ).asJava

    yaml.dump(toDump, new PrintWriter(new FileOutputStream(databaseFile)))
  }

  def loadAll: mutable.Map[String, Object] = {
    yaml.load(new FileReader(databaseFile)).asInstanceOf[java.util.Map[String, Object]].asScala
  }

  def loadDecks: Map[String, Seq[Card]] = {
    val loadedData: mutable.Map[String, mutable.Buffer[String]] = loadAll(Keys.Decks).asInstanceOf[java.util.Map[String, java.util.ArrayList[String]]].asScala.map(x => x._1 -> x._2.asScala)
    var newMap = mutable.Map[String, mutable.Seq[Card]]()
    loadedData.foreach(
      i => newMap(i._1) = i._2.map(
        x => {
          val bytes = x.getBytes
          val card = new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject().asInstanceOf[Card]
          card match {
            case e: MonsterCard => e.asInstanceOf[MonsterCard]
            case e: SpellCard => e.asInstanceOf[SpellCard]
            case e: TrapCard => e.asInstanceOf[TrapCard]
          }
        }
      )
    )
    newMap.toMap
  }

  def saveDeck(deck: Deck): Unit = {

  }

  private def yaml: Yaml = {
    val duo = new DumperOptions
    new Yaml(duo)
  }

  private object Keys {
    val Decks = "decks"
  }
}
