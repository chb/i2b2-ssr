package edu.chip.carranet.overylay.persistence

import org.junit.{Test, Ignore}
import org.springframework.transaction.annotation.Transactional
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.ContextConfiguration
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.{Autowired}
import reflect.BeanProperty
import edu.chip.carranet.overlay.persistance.{PersistedResource, ResourceStoreInMemory, ResourceStore}
import junit.framework.Assert._
import edu.chip.carranet.jaxb._
import edu.chip.carranet.ExternalExceptions.ResourceAlreadyExistsError

import scala.collection.JavaConversions._

/**
 * A Test for the hibernate backed session factory
 */
@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(locations = Array("/testApplicationContext.xml"))
class OverlayStoreHibernateTest {

  @Autowired
  @BeanProperty
  val rs: ResourceStore = null


  @Test
  @Transactional
  def testCRUD(): Unit = {
    val machinePrefix: String = "machines/"
    val studyPrefix: String = "studies/"
    val owner: String = "me"
    val machineName: String = "foo"
    var m1: Machine = new Machine
    var me1: MachineEntry = new MachineEntry
    me1.setMachineId("alpha")
    me1.setLocator("one")
    m1.setMachineEntry(me1)
    var m2: Machine = new Machine
    var me2: MachineEntry = new MachineEntry
    me2.setMachineId("beta")
    me2.setLocator("two")
    m2.setMachineEntry(me2)
    rs.create(machinePrefix + machineName, new PersistedResource(owner, CarraDataUtil.toBytes(m1)))
    assertEquals(m1, CarraDataUtil.machineFromBytes(rs.read(machinePrefix + machineName).getPayload))
    rs.update(machinePrefix + machineName, new PersistedResource(owner, CarraDataUtil.toBytes(m2)))
    assertEquals(m2, CarraDataUtil.machineFromBytes(rs.read(machinePrefix + machineName).getPayload))
    rs.delete(machinePrefix + machineName)
    assertEquals(null, rs.read(machinePrefix + machineName))
    val studyName: String = "foo"
    var s1: Study = new Study
    var se1: StudyEntry = new StudyEntry
    se1.setStudyId("alpha")
    se1.getMachineIds.add("one")
    s1.setStudyEntry(se1)
    var s2: Study = new Study
    var se2: StudyEntry = new StudyEntry
    se2.setStudyId("beta")
    se2.getMachineIds.add("two")
    s2.setStudyEntry(se2)
    rs.create(studyPrefix + studyName, new PersistedResource(owner, CarraDataUtil.toBytes(s1)))
    assertEquals(s1, CarraDataUtil.studyFromBytes(rs.read(studyPrefix + studyName).getPayload))
    rs.update(studyPrefix + studyName, new PersistedResource(owner, CarraDataUtil.toBytes(s2)))
    assertEquals(s2, CarraDataUtil.studyFromBytes(rs.read(studyPrefix + studyName).getPayload))
    rs.delete(studyPrefix + studyName)
    assertEquals(null, rs.read(studyPrefix + studyName))


  }

  @Transactional
  @Test(expected = classOf[ResourceAlreadyExistsError])
  def testResourceAlreadyExists(): Unit = {
    rs.create("m", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine)))
    rs.create("m", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine)))
  }

  @Test
  @Transactional
  def testGetAll(): Unit = {
    rs.create("/machine/m1", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine)))
    rs.create("/machine/m2", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine)))
    rs.create("/machine/m3", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine)))
    rs.create("/machine/m4", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine)))
    var returnList = rs.getAll("/machine/")
    assertEquals(returnList.size, 4)  }
}