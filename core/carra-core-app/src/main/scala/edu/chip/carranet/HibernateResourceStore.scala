package edu.chip.carranet

import data.entity.OverlayResourceEntity
import ExternalExceptions.{ResourceAlreadyExistsError, BadResourceError}
import org.hibernate.SessionFactory
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.{Autowired, Autowire}
import java.util.List
import overlay.persistance.{PersistedResource, ResourceStore}
import scala.collection.JavaConversions._
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Component

@Transactional
class HibernateResourceStore(@Autowired() val sessionFactory: SessionFactory) extends ResourceStore {

  val log = Logger.getLogger(this.getClass)

  def create(key: String, pr: PersistedResource) {
    //TODO look into whether PeristedResource still needs an "owner" field
    //
    val query = sessionFactory.getCurrentSession.createQuery(
      "from OverlayResourceEntity e where e.path = :path")
    query.setString("path", key)
    val value = query.uniqueResult().asInstanceOf[OverlayResourceEntity]
    if(value != null){
      throw new ResourceAlreadyExistsError()
    }

    val entity = new OverlayResourceEntity()
    entity.setPath(key)
    entity.setPayload(pr.getPayload)
    sessionFactory.getCurrentSession.save(entity)
  }

  def read(key: String): PersistedResource = {
    val query = sessionFactory.getCurrentSession.createQuery(
      "from OverlayResourceEntity e where e.path = :path")
    query.setString("path", key)
    val value = query.uniqueResult().asInstanceOf[OverlayResourceEntity]
    if (value != null) {
      new PersistedResource("no owner", value.getPayload)
    }
    else {
      null
    }


  }

  def update(key: String, pr: PersistedResource): PersistedResource = {
    val query = sessionFactory.getCurrentSession.createQuery(
      "from OverlayResourceEntity e where e.path = :path")
    query.setString("path", key)
    val value = query.uniqueResult().asInstanceOf[OverlayResourceEntity]
    if (value != null) {
      value.setPayload(pr.getPayload)
      new PersistedResource(null, value.getPayload)
    }
    else {
      throw new BadResourceError("no such resource")
    }

  }


  def delete(key: String) {
    val query = sessionFactory.getCurrentSession.createQuery(
      "from OverlayResourceEntity e where e.path = :path")
    query.setString("path", key)
    val value = query.uniqueResult().asInstanceOf[OverlayResourceEntity]
    if (value != null) {
      sessionFactory.getCurrentSession.delete(value)
    }
    else {
      throw new BadResourceError("no such resource")
    }


  }

  def getAll(keyLike: String): List[PersistedResource] = {
    val query = sessionFactory.getCurrentSession.createQuery(
      "from OverlayResourceEntity e where e.path like :path")
    query.setString("path", keyLike + "%")
    query.list().asInstanceOf[List[OverlayResourceEntity]] map (x => new PersistedResource(null, x.getPayload))
  }

  def close() {}
}