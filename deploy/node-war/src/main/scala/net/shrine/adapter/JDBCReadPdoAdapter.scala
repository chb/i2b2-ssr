package net.shrine.adapter

import net.shrine.protocol._
import net.shrine.adapter.dao.AdapterDAO
import net.shrine.config.I2B2HiveCredentials
import scala.xml.{XML, NodeSeq}
import org.spin.tools.crypto.signature.Identity
import net.shrine.serializers.HTTPClient
import net.noerd.prequel.{StringFormattable, LongFormattable, DatabaseConfig}
import scala.None
import java.text.{ParseException, SimpleDateFormat}
import org.joda.time.format.ISODateTimeFormat
import java.util.{GregorianCalendar, Date}
import org.joda.time.DateTime
import org.slf4j.LoggerFactory
import javax.xml.datatype.{DatatypeFactory, XMLGregorianCalendar}

/**
 * @author David Ortiz
 * @date 8/15/13
 * @link http://cbmi.med.harvard.edu
 * @link http://chip.org
 *       <p/>
 *       NOTICE: This software comes with NO guarantees whatsoever and is
 *       licensed as Lgpl Open Source
 * @link http://www.gnu.org/licenses/lgpl.html
 *
 *       An adapter that streams results right out of i2b2 instead of sending queries to the CRC
 *
 */
class JDBCReadPdoAdapter[T <: ReadPdoRequest, V <: ReadPdoResponse](
                                                                     override protected val crcUrl: String,
                                                                     override protected val dao: AdapterDAO,
                                                                     override protected val hiveCredentials: I2B2HiveCredentials,
                                                                     protected val driverName: String,
                                                                     protected val jdbcUrl: String,
                                                                     protected val username: String,
                                                                     protected val password: String) extends Adapter(crcUrl, dao, hiveCredentials) {

  import JDBCReadPdoAdapter._


  val database = DatabaseConfig(
    driver = driverName,
    jdbcURL = jdbcUrl,
    username = username,
    password = password
  )


  protected def processRequest(identity: Identity, message: BroadcastMessage): ShrineResponse = {
    //insert stuff to grab db stuff
    time {
      val pdoRequest = message.request.asInstanceOf[ReadPdoRequest]
      val localPatientCollId = dao.findLocalResultID(java.lang.Long.parseLong(pdoRequest.patientSetCollId))
      val shrineResponse = new ReadPdoResponse(fetchEvents(localPatientCollId), fetchPatients(localPatientCollId), fetchObservations(localPatientCollId))


      shrineResponse
    }
  }

  private def fetchObservations(setId: String): Seq[ObservationResponse] = {
    database.transaction {
      tx =>
        tx.select("select o.encounter_num, o.patient_num, d.name_char," +
          "o.concept_cd, o.valtype_cd, o.tval_char, o.nval_num," +
          "o.start_date, o.modifier_cd, o.units_cd, o.valueflag_cd, o.end_date, o.location_cd, v.location_path from observation_fact o, " +
          "concept_dimension d, " +
          "visit_dimension v " +
          "WHERE o.PATIENT_NUM IN (select distinct t.patient_num from qt_patient_set_collection t where t.result_instance_id = ?) " +
          "and o.concept_cd = d.concept_cd " +
          "and v.encounter_num = o.encounter_num", StringFormattable(setId)) {
          r => {
            val eventIdSource = Some("HIVE")
            val eventId = r.nextString
            val patientIdSource = Some("HIVE")
            val patientId = r.nextString
            val conceptCodeName = r.nextString
            val conceptCd = r.nextString
            val valueTypeCode = r.nextString
            val tvalChar = r.nextString
            val nvalNum = r.nextString
            val startDate = r.nextDate
            val modifierCode = r.nextString
            val unitsCode = r.nextString
            val valFlagCode = r.nextString
            val endDate = r.nextDate
            val locationCodeName = r.nextString
            val locationCode = r.nextString




            new ObservationResponse(eventIdSource, eventId.getOrElse(""),
              patientIdSource, patientId.getOrElse(""), conceptCodeName,
              conceptCd, None, "", fixPDODate(startDate).getOrElse(""), modifierCode,
              valueTypeCode.getOrElse(""), tvalChar, nvalNum, valFlagCode,
              unitsCode, fixPDODate(endDate), locationCodeName, locationCode, Seq[ParamResponse]())

          }


        }
    }

  }

  private def fetchPatients(setId: String): Seq[PatientResponse] = {
    database.transaction {
      tx =>
        tx.select("select distinct d.patient_num, d.vital_status_cd, d.birth_date, d.sex_cd, " +
          "d.age_in_years_num, d.language_cd, d.race_cd, d.religion_cd, d.marital_status_cd, " +
          "d.statecityzip_path  from PATIENT_DIMENSION d WHERE d.PATIENT_NUM IN " +
          "(select distinct t.patient_num from qt_patient_set_collection t where t.result_instance_id = ?)", StringFormattable(setId)) {
          r => {

            /*
           <patient>
              <patient_id source="i2b2">725836</patient_id>
              <param column="vital_status_cd">N</param>
              <param name="birth_date" column="birth_date">1997-05-24T00:00:00.000-04:00</param>
              <param column="sex_cd">F</param>
              <param name="age_in_years_num" column="age_in_years_num"/>
              <param column="language_cd"/>
              <param column="race_cd">white</param>
              <param column="religion_cd"/>
              <param column="marital_status_cd"/>
              <param name="statecityzip_path_char" column="statecityzip_path_char">49345</param>
          </patient>
       */
            val patiientId = r.nextString
            val vitalStatusCd = new ParamResponse("vital_status_cd", "", r.nextString.getOrElse(""))
            val birthDate = new ParamResponse("birth_date", "birth_date", r.nextString.getOrElse(""))
            val sexCode = new ParamResponse("", "sex_cd", r.nextString.getOrElse(""))
            val ageInYears = new ParamResponse("age_in_years_num", "age_in_years_num", r.nextString.getOrElse(""))
            val language = new ParamResponse("", "language_cd", r.nextString.getOrElse(""))
            val race = new ParamResponse("", "race_cd", r.nextString.getOrElse(""))
            val religion = new ParamResponse("", "religion_cd", r.nextString.getOrElse(""))
            val maritalStatus = new ParamResponse("", "marital_status_cd", r.nextString.getOrElse(""))
            val stateCityZipPathChar = new ParamResponse("statecityzip_path_char", "statecityzip_path_char", r.nextString.getOrElse(""))
            new PatientResponse(patiientId.getOrElse(""), Seq(vitalStatusCd, birthDate, sexCode, ageInYears, language, race, religion, maritalStatus, stateCityZipPathChar))
          }
        }

    }
  }

  private def fetchEvents(setId: String): Seq[EventResponse] = {
    database.transaction {
      tx =>
        tx.select("select distinct v.encounter_num, v.patient_num, v.inout_cd, v.location_cd, v.location_path, v.active_status_cd, v.start_date, v.end_date from observation_fact o, " +
          "concept_dimension d, " +
          "visit_dimension v " +
          "WHERE o.PATIENT_NUM IN (select distinct t.patient_num from qt_patient_set_collection t where t.result_instance_id = ?) " +
          "and o.concept_cd = d.concept_cd " +
          "and v.encounter_num = o.encounter_num", StringFormattable(setId)) {
          r => {

            /*
                 <event>
                        <event_id source="i2b2">197</event_id>
                        <patient_id source="i2b2">817261</patient_id>
                        <param column="inout_cd">@</param>
                        <param column="location_cd">@</param>
                        <param name="CRNT_REGST\evFU\390405843778050\" column="location_path">CRNT_REGST\evFU\390405843778050\</param>
                        <param column="active_status_cd">-</param>
                        <start_date>2013-05-16T00:00:00.000-04:00</start_date>
                    </event>
            */

            val encounterNum = r.nextString
            val patientNum = r.nextString
            val inoutCd = r.nextString
            val locationCd = r.nextString
            val locationPath = r.nextString
            val activeStatusCode = r.nextString
            val startDate = r.nextDate
            val endDate = r.nextDate

            val params = Seq[ParamResponse](new ParamResponse("", "inout_cd", inoutCd.getOrElse("")),
              new ParamResponse("", "location_cd", locationCd.getOrElse("")),
              new ParamResponse(locationPath.getOrElse(""), "location_path", locationPath.getOrElse("")),
              new ParamResponse("", "active_status_cd", activeStatusCode.getOrElse("")))


            new EventResponse(encounterNum.getOrElse(""), patientNum.getOrElse(""), toXmlGregorianCalendar(startDate), toXmlGregorianCalendar(endDate), params)


          }
        }
    }


  }
}


object JDBCReadPdoAdapter {
  val log = LoggerFactory.getLogger(JDBCReadPdoAdapter.getClass)

  protected def fixPDODate(date: Option[Date]): Option[String] = {

    date match {
      case None => None
      case Some(x) => {
        val dt = new DateTime(x)
        Some(dt.toString(ISODateTimeFormat.dateTime()))
      }

    }
  }

  def toXmlGregorianCalendar(d: Option[Date]): Option[XMLGregorianCalendar] = {
    d match {
      case None => None
      case Some(x) => {
        val gc = new GregorianCalendar()
        gc.setTime(x)
        Some(DatatypeFactory.newInstance().newXMLGregorianCalendar(gc))
      }
    }
  }


  def time[R](block: => R): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    log.info("Elapsed time: " + (t1 - t0) + "ns")
    result
  }


}