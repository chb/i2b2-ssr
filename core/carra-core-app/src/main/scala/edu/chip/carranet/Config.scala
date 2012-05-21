package edu.chip.carranet

import java.net.URL
import com.typesafe.config.ConfigFactory


object Config {

  val config = ConfigFactory.load


  def ontologyURI : URL = {
    new URL(config.getString("i2b2.ont_cell_uri"))
  }

  def crcURI : URL = {
    new URL(config.getString("i2b2.crc_cell_uri"))
  }



}