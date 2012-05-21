package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.auth.backend.UserPermissions;
import edu.chip.carranet.jaxb.CarraUserInfo;
import edu.chip.carranet.jaxb.Machine;
import edu.chip.carranet.jaxb.MachineEntry;
import edu.chip.carranet.jaxb.Machines;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Client;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.spin.tools.JAXBUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by IntelliJ IDEA.
 * DecoratedUser: justinquan
 * Date: Mar 29, 2010
 * Time: 2:05:34 AM
 * To change this template use File | Settings | File Templates.
 */

public class MachineResourceTest extends ResourceTestHarness {

    private Client client;
    private CarraUserInfo carraUser;


    public Client getClient() {
        return client;
    }

    @Before
    public void mySetup() throws Exception {
        this.client = new Client(Protocol.HTTP);
        this.carraUser = new CarraUserInfo();
        carraUser.getAssertions().add("role:admin");

        this.carraUser.setUsername("captain-planet");

    }

    /**
     * This test creates a machine and validates that
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testCreate() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base);

        Machine u = new Machine();
        MachineEntry entry = new MachineEntry();
        entry.setMachineId(machineId);
        entry.setLocator("location1");
        u.setMachineEntry(entry);
        Representation machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create machine
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, machineRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + machineId, response.getLocationRef().getPath());

    }

    /**
     * This test creates a machine validating:
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     * It then recreates the machine validating:
     * 1. The HTTP response code is correct
     * 2. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testCreateExisting() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base);

        Machine u = new Machine();
        MachineEntry entry = new MachineEntry();
        entry.setMachineId(machineId);
        entry.setLocator("location1");
        u.setMachineEntry(entry);
        Representation machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create machine
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, machineRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + machineId, response.getLocationRef().getPath());


        // attempt a recreate
        Representation recreateRep = new StringRepresentation(JAXBUtils.marshalToString(u));
        Response recreateResponse = sendAuthenticatedRequest(Method.POST, carraUser, ref, recreateRep);

        assertEquals(Status.CLIENT_ERROR_FORBIDDEN, recreateResponse.getStatus());
    }

    /**
     * This test creates a machine and validates that
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     * Then we fetch the machine and validate that
     * 1. The HTTP response code is correct
     * 2. The entity is what we previously created
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testGet() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base);

        Machine u = new Machine();
        MachineEntry entry = new MachineEntry();
        entry.setMachineId(machineId);
        entry.setLocator("location1");
        u.setMachineEntry(entry);
        Representation machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create machine
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, machineRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + machineId, response.getLocationRef().getPath());


        // get machine
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, response.getLocationRef(), null);
        assertEquals(Status.SUCCESS_OK, getResponse.getStatus());
        Machine m = JAXBUtils.unmarshal(getResponse.getEntityAsText(), Machine.class);
        assertEquals(u, m);
    }

    /**
     * This test attempts to fetch a DecoratedMachine that's not there and validates that
     * 1. The HTTP response code is correct
     * 2. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testGetNonExistent() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base + machineId);

        // get machine
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, ref, null);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, getResponse.getStatus());


    }

    /**
     * This test creates a machine and validates that
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     * Then we update the machine and validate that
     * 1. The HTTP response code is correct
     * 2. The entity is empty
     * Then we fetch the machine and validate that
     * 1. The HTTP response code is correct
     * 2. The response body is the updated machine
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testUpdate() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base);

        Machine u = new Machine();
        MachineEntry entry = new MachineEntry();
        entry.setMachineId(machineId);
        entry.setLocator("location1");
        u.setMachineEntry(entry);
        Representation machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create machine
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, machineRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + machineId, response.getLocationRef().getPath());


        // update machine
        ref.setPath(base + machineId);
        Machine newMachine = new Machine();
        MachineEntry entry2 = new MachineEntry();
        entry2.setMachineId(machineId);
        entry2.setLocator("location2");
        newMachine.setMachineEntry(entry2);
        Representation newMachineRep = new StringRepresentation(JAXBUtils.marshalToString(newMachine));
        Response newMachineResponse = sendAuthenticatedRequest(Method.PUT, carraUser, ref, newMachineRep);

        assertEquals(Status.SUCCESS_OK, newMachineResponse.getStatus());
        assertNull(newMachineResponse.getEntityAsText());

        // get machine
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, response.getLocationRef(), null);
        assertEquals(Status.SUCCESS_OK, getResponse.getStatus());

        Machine m = JAXBUtils.unmarshal(getResponse.getEntityAsText(), Machine.class);

        assertEquals(newMachine, m);
    }


    /**
     * This test updates a machine that doesn't exist and we validate that
     * 1. The HTTP response code is correct
     * 2. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testUpdateNonExistent() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base + machineId);

        Machine u = new Machine();
        MachineEntry entry = new MachineEntry();
        entry.setMachineId(machineId);
        entry.setLocator("location1");
        u.setMachineEntry(entry);
        Representation machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // update machine
        Response putResponse = sendAuthenticatedRequest(Method.PUT, carraUser, ref, machineRep);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, putResponse.getStatus());


    }

    /**
     * This test creates a machine and validates that
     * 1. The HTTP response code is correct
     * 2. The response includes the Location header with the appropriate value
     * 3. The response body includes a valid xml response and correct result code
     * Then we delete the machine and validate that
     * 1. The HTTP response code is correct
     * 2. The entity is empty
     * Then we fetch the machine and validate that
     * 1. The HTTP response code is correct
     * 2. The response body includes a valid xml response and correct result code
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testDelete() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base);

        Machine u = new Machine();
        MachineEntry entry = new MachineEntry();
        entry.setMachineId(machineId);
        entry.setLocator("location1");
        u.setMachineEntry(entry);
        Representation machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create machine
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, machineRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + machineId, response.getLocationRef().getPath());

        // delete machine
        ref.setPath(base + machineId);
        Response newMachineResponse = sendAuthenticatedRequest(Method.DELETE, carraUser, ref, null);

        assertEquals(Status.SUCCESS_NO_CONTENT, newMachineResponse.getStatus());
        assertNull(newMachineResponse.getEntityAsText());

        // get machine
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, response.getLocationRef(), null);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, getResponse.getStatus());

    }

    /**
     * This test deletes a machine that doesn't exist and we validate that
     * 1. The HTTP response code is correct
     * 2. The response body is empty
     *
     * @throws Exception when the unexpected happens.
     */
    @Test
    public void testDeleteNonExistent() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base + machineId);

        // get machine
        Response getResponse = sendAuthenticatedRequest(Method.DELETE, carraUser, ref, null);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, getResponse.getStatus());
        assertNull(getResponse.getEntityAsText());
    }


    @Test
    public void testGetMachines() throws Exception {

        String base = "/test/machines/";
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base);
        final String machineId = carraUser.getUsername();

        Machine u = new Machine();
        MachineEntry entry = new MachineEntry();
        entry.setMachineId(machineId);
        entry.setLocator("location1");
        u.setMachineEntry(entry);
        Representation machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));
        sendAuthenticatedRequest(Method.POST, carraUser, ref, machineRep);

        u = new Machine();
        MachineEntry entry2 = new MachineEntry();
        entry2.setMachineId(machineId + 2);
        entry2.setLocator("location1");
        u.setMachineEntry(entry2);
        machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));
        sendAuthenticatedRequest(Method.POST, carraUser, ref, machineRep);

        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, ref, null);
        Machines machines = JAXBUtils.unmarshal(getResponse.getEntityAsText(), Machines.class);

        assertEquals(2, machines.getMachineList().size());
        assertEquals(true, machines.getMachineList().contains(entry));
        assertEquals(true, machines.getMachineList().contains(entry2));

    }

    @Test
    public void testAdmin() throws Exception {
        final String base = "/test/machines/";
        final String machineId = carraUser.getUsername();
        Reference ref = new Reference(Protocol.HTTP, "localhost", 9998);
        ref.setPath(base);

        Machine u = new Machine();
        MachineEntry entry = new MachineEntry();
        entry.setMachineId(machineId);
        entry.setLocator("location1");
        u.setMachineEntry(entry);
        Representation machineRep = new StringRepresentation(JAXBUtils.marshalToString(u));

        // create machine
        Response response = sendAuthenticatedRequest(Method.POST, carraUser, ref, machineRep);

        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        assertEquals(base + machineId, response.getLocationRef().getPath());


        // create 2 users, another joe, and an admin to try deletions
        CarraUserInfo joe = new CarraUserInfo();
        joe.setUsername("joe");
        CarraUserInfo superJoe = new CarraUserInfo();
        superJoe.setUsername("superJoe");
        superJoe.getAssertions().add(UserPermissions.admin.toAssertionString());

        // delete machine as joe
        ref.setPath(base + machineId);
        Response newMachineResponse = sendAuthenticatedRequest(Method.DELETE, joe, ref, null);

        assertEquals(Status.CLIENT_ERROR_FORBIDDEN, newMachineResponse.getStatus());


        // delete machine as super joe
        ref.setPath(base + machineId);
        newMachineResponse = sendAuthenticatedRequest(Method.DELETE, superJoe, ref, null);

        assertEquals(Status.SUCCESS_NO_CONTENT, newMachineResponse.getStatus());
        assertNull(newMachineResponse.getEntityAsText());

        // get machine
        Response getResponse = sendAuthenticatedRequest(Method.GET, carraUser, response.getLocationRef(), null);
        assertEquals(Status.CLIENT_ERROR_NOT_FOUND, getResponse.getStatus());

    }
}

