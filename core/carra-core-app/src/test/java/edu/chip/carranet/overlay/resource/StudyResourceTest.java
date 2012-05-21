package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.auth.exception.AuthException;
import edu.chip.carranet.jaxb.*;
import edu.chip.carranet.overlay.shared.CarranetAuthenticatorHelper;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by IntelliJ IDEA.
 * DecoratedUser: justinquan
 * Date: Mar 29, 2010
 * Time: 1:54:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudyResourceTest extends ResourceTestHarness {

    private Client client;
    private CarraUserInfo carraUser;


    public Client getClient() {
        return client;
    }

    @Before
    public void mySetup() throws Exception {
        this.client = new Client(Protocol.HTTP);
        this.carraUser = new CarraUserInfo();
        this.carraUser.setUsername("captain-planet");
        this.carraUser.getAssertions().add("role:admin");

    }

    /**
     * This test creates a study and validates that
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testCreate() throws Exception {
        final String base = "/test/studies/";
        final String studyId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base);

        Study u = new Study();
        StudyEntry se = new StudyEntry();
        se.setStudyId(studyId);
        se.getMachineIds().add("machine1");
        u.setStudyEntry(se);
        Representation studyRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create study
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, studyRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + studyId, response.getLocationRef().getPath());


    }

    /**
     * This test creates a study validating:
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     * It then recreates the study validating:
     * 1. The HTTP response code is correct
     * 2. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testCreateExisting() throws Exception {
        final String base = "/test/studies/";
        final String studyId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base);

        Study u = new Study();
        StudyEntry se = new StudyEntry();
        se.setStudyId(studyId);
        se.getMachineIds().add("machine1");
        u.setStudyEntry(se);
        Representation studyRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create study
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, studyRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + studyId, response.getLocationRef().getPath());

        // attempt a recreate
        Representation recreateRep = new StringRepresentation(JAXBUtils.marshalToString(u));
        Response recreateResponse = sendAuthenticatedRequest(Method.POST, carraUser, ref, recreateRep);

        assertEquals(Status.CLIENT_ERROR_FORBIDDEN, recreateResponse.getStatus());

    }

    /**
     * This test creates a study and validates that
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     * Then we fetch the study and validate that
     * 1. The HTTP response code is correct
     * 2. The entity is what we previously created
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testGet() throws Exception {
        final String base = "/test/studies/";
        final String studyId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base);

        Study u = new Study();
        StudyEntry se = new StudyEntry();
        se.setStudyId(studyId);
        se.getMachineIds().add("machine1");
        u.setStudyEntry(se);
        Representation studyRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create study
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, studyRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + studyId, response.getLocationRef().getPath());


        // get study
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, response.getLocationRef(), null);
        assertEquals(Status.SUCCESS_OK, getResponse.getStatus());

        Study s = JAXBUtils.unmarshal(getResponse.getEntityAsText(), Study.class);
        assertEquals(u, s);
    }

    /**
     * This test attempts to fetch a DecoratedStudy that's not there and validates that
     * 1. The HTTP response code is correct
     * 2. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testGetNonExistent() throws Exception {
        final String base = "/test/studies/";
        final String studyId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base + studyId);

        // get study
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, ref, null);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, getResponse.getStatus());


    }

    /**
     * This test creates a study and validates that
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     * Then we update the study and validate that
     * 1. The HTTP response code is correct
     * 2. The entity is empty
     * Then we fetch the study and validate that
     * 1. The HTTP response code is correct
     * 2. The response body is the updated study
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testUpdate() throws Exception {
        final String base = "/test/studies/";
        final String studyId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base);

        Study u = new Study();
        StudyEntry se = new StudyEntry();
        se.setStudyId(studyId);
        se.getMachineIds().add("machine1");
        u.setStudyEntry(se);
        Representation studyRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create study
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, studyRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + studyId, response.getLocationRef().getPath());


        // update study
        ref.setPath(base + studyId);
        Study newStudy = new Study();
        StudyEntry se2 = new StudyEntry();
        se2.setStudyId(studyId);
        se2.getMachineIds().add("machine2");
        newStudy.setStudyEntry(se2);
        Representation newStudyRep = new StringRepresentation(JAXBUtils.marshalToString(newStudy));
        Response newStudyResponse = sendAuthenticatedRequest(Method.PUT, carraUser, ref, newStudyRep);

        assertEquals(Status.SUCCESS_OK, newStudyResponse.getStatus());
        assertNull(newStudyResponse.getEntityAsText());

        // get study
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, response.getLocationRef(), null);
        assertEquals(Status.SUCCESS_OK, getResponse.getStatus());
        Study s = JAXBUtils.unmarshal(getResponse.getEntityAsText(), Study.class);

        assertEquals(newStudy, s);
    }


    /**
     * This test updates a study that doesn't exist and we validate that
     * 1. The HTTP response code is correct
     * 2. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testUpdateNonExistent() throws Exception {
        final String base = "/test/studies/";
        final String studyId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base + studyId);

        Study u = new Study();
        StudyEntry se = new StudyEntry();
        se.setStudyId(studyId);
        se.getMachineIds().add("machine1");
        u.setStudyEntry(se);
        Representation studyRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // update study
        Response putResponse = sendAuthenticatedRequest(Method.PUT, carraUser, ref, studyRep);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, putResponse.getStatus());
    }

    /**
     * This test creates a study and validates that
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     * Then we delete the study and validate that
     * 1. The HTTP response code is correct
     * 2. The entity is empty
     * Then we fetch the study and validate that
     * 1. The HTTP response code is correct
     * 2. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testDelete() throws Exception {
        final String base = "/test/studies/";
        final String studyId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base);

        Study u = new Study();
        StudyEntry se = new StudyEntry();
        se.setStudyId(studyId);
        se.getMachineIds().add("machine1");
        u.setStudyEntry(se);
        Representation studyRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create study
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, studyRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + studyId, response.getLocationRef().getPath());

        // delete study
        ref.setPath(base + studyId);
        Response newStudyResponse = sendAuthenticatedRequest(Method.DELETE, carraUser, ref, null);

        assertEquals(Status.SUCCESS_NO_CONTENT, newStudyResponse.getStatus());
        assertNull(newStudyResponse.getEntityAsText());

        // get study
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, response.getLocationRef(), null);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, getResponse.getStatus());
    }

    /**
     * This test deletes a study that doesn't exist and we validate that
     * 1. The HTTP response code is correct
     * 2. The response body is empty
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testDeleteNonExistent() throws Exception {
        final String base = "/test/studies/";
        final String studyId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base + studyId);

        // get study
        Response getResponse = sendAuthenticatedRequest(Method.DELETE, carraUser, ref, null);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, getResponse.getStatus());
        assertNull(getResponse.getEntityAsText());
    }


    @Test
    public void testGetStudies() throws Exception {
        final String base = "/test/studies/";
        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
        ref.setPath(base);
        final String studyId = carraUser.getUsername();

        Study u = new Study();
        StudyEntry se = new StudyEntry();
        se.setStudyId(studyId);
        se.getMachineIds().add("machine1");
        u.setStudyEntry(se);
        Representation studyRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create study
        sendAuthenticatedRequest(Method.POST, carraUser, ref, studyRep);

        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, ref, null);
        Studies studies = JAXBUtils.unmarshal(getResponse.getEntityAsText(), Studies.class);
        assertEquals(1, studies.getStudyList().size());
        assertEquals(true, studies.getStudyList().contains(se));

        return;
    }

}                             
