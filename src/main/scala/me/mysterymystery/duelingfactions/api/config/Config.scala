package me.mysterymystery.duelingfactions.api.config

import java.io.{File, FileInputStream, InputStream, InputStreamReader}

import scala.collection.JavaConverters._
import org.yaml.snakeyaml.Yaml

import scala.collection.mutable

object Config {
    private val configFile: File = new File(getClass.getResource("/config/config.yml").getPath)
    private val conf: mutable.Map[String, Object] = new Yaml().load(new FileInputStream(configFile)).asInstanceOf[java.util.Map[String, Object]].asScala

    def fieldZoneHeightWidth: Int = conf("cardFieldHeightWidth").asInstanceOf[Int]
    def cardHeight: Int = conf("cardHeight").asInstanceOf[Int]
    def cardWidth: Int = conf("cardWidth").asInstanceOf[Int]
}
