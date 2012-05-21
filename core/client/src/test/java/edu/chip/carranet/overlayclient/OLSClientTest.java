package edu.chip.carranet.overlayclient;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import edu.carranet.client.overlay.IOLSClient;
import edu.carranet.client.overlay.OLSClient;
import edu.carranet.client.overlay.OLSCredentials;
import edu.chip.carranet.auth.backend.TokenUtil;
import edu.chip.carranet.jaxb.*;
import org.junit.Test;
import org.restlet.Component;
import org.spin.tools.crypto.signature.Identity;
import org.springframework.web.context.ContextLoaderListener;

import static org.junit.Assert.assertTrue;


/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 30, 2010
 */
public class OLSClientTest extends JerseyTest {
    //These Tests are really simple but it's a good idea to check

    Component olscomponent;

    public OLSClientTest() {
        super(new WebAppDescriptor.Builder("edu.chip.carranet")
                .contextPath("/test")
                .contextParam("contextConfigLocation", "/testApplicationContext.xml")
                .servletClass(SpringServlet.class)
                .contextListenerClass(ContextLoaderListener.class)
                .build());

    }


    @Test
    public void testAddMachine() throws Exception {
        IOLSClient olsClient = createClient();
        MachineEntry entry1 = new MachineEntry();
        entry1.setMachineId("test");
        entry1.setLocator("test");
        Machine m1 = new Machine();
        m1.setMachineEntry(entry1);

        olsClient.createMachine(makeCredentials(), m1);

        Machines machines = olsClient.listMachines(makeCredentials());
        assertTrue(machines.getMachineList().contains(entry1));

    }

    @Test
    public void testDeleteMachine() throws Exception {
        IOLSClient olsClient = createClient();
        MachineEntry entry1 = new MachineEntry();
        entry1.setMachineId("test");
        entry1.setLocator("test");
        Machine m1 = new Machine();
        m1.setMachineEntry(entry1);

        olsClient.createMachine(makeCredentials(), m1);

        Machines machines = olsClient.listMachines(makeCredentials());
        assertTrue(machines.getMachineList().contains(entry1));

        olsClient.deleteMachine(makeCredentials(), "test");
        machines = olsClient.listMachines(makeCredentials());
        assertTrue(!machines.getMachineList().contains(entry1));
    }

    @Test
    public void testAddStudy() throws Exception {
        IOLSClient olsClient = createClient();
        StudyEntry entry1 = new StudyEntry();
        entry1.setStudyId("test");
        entry1.getMachineIds().add("machine1");

        Study study = new Study();
        study.setStudyEntry(entry1);

        olsClient.createStudy(makeCredentials(), study);

        Studies s = olsClient.listStudies(makeCredentials());
        assertTrue(s.getStudyList().contains(entry1));


    }

    @Test
    public void testDeleteStudy() throws Exception {
        IOLSClient olsClient = createClient();
        StudyEntry entry1 = new StudyEntry();
        entry1.setStudyId("test");
        entry1.getMachineIds().add("machine1");

        Study study = new Study();
        study.setStudyEntry(entry1);

        olsClient.createStudy(makeCredentials(), study);

        Studies s = olsClient.listStudies(makeCredentials());
        assertTrue(s.getStudyList().contains(entry1));

        olsClient.deleteStudy(makeCredentials(), "test");
        s = olsClient.listStudies(makeCredentials());
        assertTrue(!s.getStudyList().contains(entry1));

    }

    @Test
    public void testDontLeakConnections() throws Exception {
        IOLSClient olsClient = createClient();
        for (int i = 0; i < 25; ++i) {
            MachineEntry me = new MachineEntry();
            me.setLocator("location");
            me.setMachineId("someId-" + System.nanoTime());
            Machine m = new Machine();
            m.setMachineEntry(me);
            olsClient.createMachine(makeCredentials(), m);
            olsClient.getMachine(makeCredentials(), me.getMachineId());
            olsClient.updateMachine(makeCredentials(), me.getMachineId(), m);
            olsClient.deleteMachine(makeCredentials(), me.getMachineId());
        }
    }


    private IOLSClient createClient() throws Exception {
        return new OLSClient("http://localhost:9998/test");
    }

    private OLSCredentials makeCredentials() throws Exception {
        CarraUserInfo info = new CarraUserInfo();
        info.setUsername("test");
        info.getAssertions().add("role:admin");

        Identity id = TokenUtil.createToken(info);
        return new OLSCredentials(TokenUtil.convertIdentityToAuthString(id));

    }


}
