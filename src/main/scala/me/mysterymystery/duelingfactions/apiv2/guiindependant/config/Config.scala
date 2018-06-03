package me.mysterymystery.duelingfactions.apiv2.guiindependant.config

import java.io.InputStream

import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.Card
import me.mysterymystery.duelingfactions.apiv2.guiindependant.card.collections.Deck
import org.yaml.snakeyaml.Yaml

import scala.collection.JavaConverters._
import scala.collection.mutable

object Config {
    private val configFile: InputStream = getClass.getResourceAsStream("/config/config.yml")
    private val conf: mutable.Map[String, Object] = new Yaml().load(configFile).asInstanceOf[java.util.Map[String, Object]].asScala

    def fieldZoneHeightWidth: Int = conf("cardFieldHeightWidth").asInstanceOf[Int]
    def cardHeight: Int = conf("cardHeight").asInstanceOf[Int]
    def cardWidth: Int = conf("cardWidth").asInstanceOf[Int]
}
