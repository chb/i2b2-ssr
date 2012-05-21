package edu.chip.carranet

import org.scalatest.matchers.ShouldMatchers
import org.junit.Test


class TestConfig extends ShouldMatchers {

  @Test
  def getCRCUri {
    Config.ontologyURI.toExternalForm should equal("http://carra-i2b2:9090/i2b2/rest/OntologyService/")

  }

  @Test
  def getOntUri {
    Config.crcURI.toExternalForm should equal("http://CRC")

  }

}