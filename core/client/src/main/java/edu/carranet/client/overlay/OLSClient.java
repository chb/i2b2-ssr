package edu.carranet.client.overlay;

import edu.chip.carranet.jaxb.*;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Restlet based OLS client.
 *
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 30, 2010
 */
public class OLSClient implements IOLSClient {


    private final String machinesPath = "machines";
    private final String studiesPath = "studies";
    private Client client;
    private Reference baseRef;
    URL serviceUrl;


    public OLSClient(String endpoint) throws MalformedURLException {
        serviceUrl = new URL(endpoint);
        client = new Client(serviceUrl.getProtocol());

        baseRef = new Reference(endpoint);
    }

    private String sendAuthenticatedRequest(Method m, String tokenString, Reference ref, Representation machineRep) {
        // create the request and send it off

        Form f = new Form();
        f.add("x-carra-auth", tokenString);
        Request request = new Request();
        request.getAttributes().put("org.restlet.http.headers", f);
        request.setMethod(m);
        request.setResourceRef(ref);
        request.setEntity(machineRep);

        Response r = client.handle(request);

        if (r != null && r.isEntityAvailable()) {
            return r.getEntityAsText();
        } else {
            return "no_response";
        }
    }


    @Override
    public void createMachine(OLSCredentials creds, Machine m) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(machinesPath);
        try {
            Representation rep = new StringRepresentation(JAXBUtils.marshalToString(m));
            sendAuthenticatedRequest(Method.POST, creds.getCreds(), ref, rep);
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }

    @Override
    public void createStudy(OLSCredentials creds, Study s) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(studiesPath);
        try {
            Representation rep = new StringRepresentation(JAXBUtils.marshalToString(s));
            sendAuthenticatedRequest(Method.POST, creds.getCreds(), ref, rep);
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }


    @Override
    public void createMachine(OLSCredentials creds, String machineId, String locator) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(machinesPath);
        try {
            Machine m = new Machine();
            MachineEntry me = new MachineEntry();
            me.setLocator(locator);
            me.setMachineId(machineId);
            m.setMachineEntry(me);
            Representation rep = new StringRepresentation(JAXBUtils.marshalToString(m));
            sendAuthenticatedRequest(Method.POST, creds.getCreds(), ref, rep);
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }

    @Override
    public void createStudy(OLSCredentials creds, String studyId, List<String> machines) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(studiesPath);

        try {
            Study s = new Study();
            StudyEntry se = new StudyEntry();
            se.setStudyId(studyId);
            for (String machine : machines) {
                se.getMachineIds().add(machine);
            }
            s.setStudyEntry(se);

            Representation rep = new StringRepresentation(JAXBUtils.marshalToString(s));
            sendAuthenticatedRequest(Method.POST, creds.getCreds(), ref, rep);
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }

    }


    @Override
    public Machine getMachine(OLSCredentials creds, String machineId) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(machinesPath);
        ref.addSegment(machineId);

        String response = sendAuthenticatedRequest(Method.GET, creds.getCreds(), ref, null);

        try {
            Machine m = JAXBUtils.unmarshal(response, Machine.class);
            return m;
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }

    @Override
    public Study getStudy(OLSCredentials creds, String studyId) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(studiesPath);
        ref.addSegment(studyId);

        String response = sendAuthenticatedRequest(Method.GET, creds.getCreds(), ref, null);

        try {
            Study s = JAXBUtils.unmarshal(response, Study.class);
            return s;
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }


    @Override
    public void updateMachine(OLSCredentials creds, String machineId, Machine m) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(machinesPath);
        ref.addSegment(machineId);

        try {
            Representation rep = new StringRepresentation(JAXBUtils.marshalToString(m));
            sendAuthenticatedRequest(Method.PUT, creds.getCreds(), ref, rep);
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }

    @Override
    public void updateStudy(OLSCredentials creds, String studyId, Study s) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(studiesPath);
        ref.addSegment(studyId);

        try {
            Representation rep = new StringRepresentation(JAXBUtils.marshalToString(s));
            sendAuthenticatedRequest(Method.PUT, creds.getCreds(), ref, rep);
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }


    @Override
    public void deleteMachine(OLSCredentials creds, String machineId) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(machinesPath);
        ref.addSegment(machineId);

        sendAuthenticatedRequest(Method.DELETE, creds.getCreds(), ref, null);
    }

    @Override
    public void deleteStudy(OLSCredentials creds, String studyId) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(studiesPath);
        ref.addSegment(studyId);
        sendAuthenticatedRequest(Method.DELETE, creds.getCreds(), ref, null);
    }


    @Override
    public Machines listMachines(OLSCredentials creds) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(machinesPath);
        String response = sendAuthenticatedRequest(Method.GET, creds.getCreds(), ref, null);

        try {
            Machines list = JAXBUtils.unmarshal(response, Machines.class);
            return list;
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }

    @Override
    public Studies listStudies(OLSCredentials creds) throws OLSException {
        Reference ref = new Reference(baseRef);
        ref.addSegment(studiesPath);
        String response = sendAuthenticatedRequest(Method.GET, creds.getCreds(), ref, null);

        try {
            Studies list = JAXBUtils.unmarshal(response, Studies.class);
            return list;
        } catch (JAXBException e) {
            throw new OLSException("JAXB fail", e);
        }
    }

}
