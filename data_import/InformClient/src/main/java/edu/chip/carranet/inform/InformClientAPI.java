package edu.chip.carranet.inform;

import com.phaseforward.informadapter.odm._2.ResponseODM;

import java.io.IOException;

/**
 * @author Justin Quan
 * @link http://chip.org
 * Date: 3/3/11
 */
public interface InformClientAPI {
    public String getSiteList() throws IOException;
    public String getPatientList(String site) throws IOException;
    public String getPatientForms(String patient) throws IOException;
    public String getMetadata() throws IOException;
    public String getAdminMetadata() throws IOException;
    public ResponseODM getTransaction(String bookmark) throws IOException;
}
