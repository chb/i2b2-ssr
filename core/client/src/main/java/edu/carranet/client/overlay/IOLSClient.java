package edu.carranet.client.overlay;

import edu.chip.carranet.jaxb.Machine;
import edu.chip.carranet.jaxb.Machines;
import edu.chip.carranet.jaxb.Studies;
import edu.chip.carranet.jaxb.Study;

import java.util.List;

/**
 * This is the client interface!  It is meant as a client lib to interface with
 * the OverlayService which is responsible for storing relationships in carranet.
 *
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 30, 2010
 */
public interface IOLSClient {

    public void createMachine(OLSCredentials creds, Machine m) throws OLSException;

    public void createStudy(OLSCredentials creds, Study s) throws OLSException;

    public void createMachine(OLSCredentials creds, String machineId, String locator) throws OLSException;

    public void createStudy(OLSCredentials creds, String studyId, List<String> machines) throws OLSException;

    public Machine getMachine(OLSCredentials creds, String machineId) throws OLSException;

    public Study getStudy(OLSCredentials creds, String studyId) throws OLSException;

    public void updateMachine(OLSCredentials creds, String machineId, Machine m) throws OLSException;

    public void updateStudy(OLSCredentials creds, String studyId, Study s) throws OLSException;

    public void deleteMachine(OLSCredentials creds, String machineId) throws OLSException;

    public void deleteStudy(OLSCredentials creds, String studyId) throws OLSException;

    public Machines listMachines(OLSCredentials creds) throws OLSException;

    public Studies listStudies(OLSCredentials creds) throws OLSException;

}
