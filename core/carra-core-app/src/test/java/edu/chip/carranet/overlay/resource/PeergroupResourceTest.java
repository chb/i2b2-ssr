package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.jaxb.*;
import edu.chip.carranet.overlay.persistance.PersistedResource;
import edu.chip.carranet.overlay.persistance.ResourceStore;
import edu.chip.carranet.overlay.persistance.ResourceStoreInMemory;
import org.junit.*;
import org.restlet.Client;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.spin.tools.JAXBUtils;
import org.spin.tools.config.PeerGroupConfig;
import org.spin.tools.config.RoutingTableConfig;

import javax.xml.bind.JAXBException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@Ignore
public class PeergroupResourceTest {
//    protected static final int port = 9998;
//
//
//    private Client client;
//    private CarraUserInfo carraUser;
//
//    private Component component;
//    Study s1;
//    Study s2;
//
//
//
//    @Before
//    public void mySetup() throws Exception {
//
//        this.client = new Client(Protocol.HTTP);
//        this.carraUser = new CarraUserInfo();
//        this.carraUser.setUsername("me");
//
////        OverlayStoreFactory.getInstance().setResourceStore(new ResourceStoreInMemory());
////
////        ResourceStore resourceStore = OverlayStoreFactory.getInstance().getResourceStore();
////
////        component = new Component();
////        component.getServers().add(Protocol.HTTP, port);
////        component.getDefaultHost().attach(new OverlayMain());
////        component.start();
//
//        List<String> machines1 = new ArrayList<String>();
//        machines1.add("localhost");
//        machines1.add("duke");
//        machines1.add("harvard");
//        machines1.add("calvin");
//        machines1.add("hobbes");
//
//
//        List<String> machines2 = new ArrayList<String>();
//        machines2.add("test1");
//        machines2.add("test2");
//        machines2.add("test3");
//
//        s1 = new Study();
//        StudyEntry entry1 = new StudyEntry();
//        entry1.setStudyId("testStudy1");
//        s1.setStudyEntry(entry1);
//        entry1.getMachineIds().addAll(machines1);
//
//        resourceStore.create("/studies/testStudy1",
//                new PersistedResource("me", CarraDataUtil.toBytes(s1)));
//
//        s2 = new Study();
//        StudyEntry studyEntry2 = new StudyEntry();
//        s2.setStudyEntry(studyEntry2);
//        studyEntry2.setStudyId("testStudy2");
//        studyEntry2.getMachineIds().addAll(machines2);
//
//        resourceStore.create("/studies/testStudy2",
//                new PersistedResource("me", CarraDataUtil.toBytes(s2)));
//
//        //Machines frmo testStudy1
//        Machine localhost = new Machine();
//        Machine duke = new Machine();
//        Machine harvard = new Machine();
//        Machine calvin = new Machine();
//        Machine hobbes = new Machine();
//
//
//        //Machines from testStudy2
//        Machine test1 = new Machine();
//        Machine test2 = new Machine();
//        Machine test3 = new Machine();
//
//        //Data for localhost
//        MachineEntry localHostEntryMachineEntry = new MachineEntry();
//        localHostEntryMachineEntry.setLocator("localhost.com");
//        localHostEntryMachineEntry.setMachineId("localhost");
//        localhost.setMachineEntry(localHostEntryMachineEntry);
//
//        resourceStore.create("/machines/localhost",
//                new PersistedResource("me", CarraDataUtil.toBytes(localhost)));
//
//        MachineEntry dukeEntry = new MachineEntry();
//        dukeEntry.setMachineId("duke");
//        dukeEntry.setLocator("duke.com");
//        duke.setMachineEntry(dukeEntry);
//
//        resourceStore.create("/machines/duke",
//                new PersistedResource("me", CarraDataUtil.toBytes(duke)));
//
//        MachineEntry harvardEntry = new MachineEntry();
//        harvardEntry.setMachineId("harvard");
//        harvardEntry.setLocator("harvard.com");
//        harvard.setMachineEntry(harvardEntry);
//
//        resourceStore.create("/machines/harvard",
//                new PersistedResource("me", CarraDataUtil.toBytes(harvard)));
//
//        MachineEntry calvinEntry = new MachineEntry();
//        calvinEntry.setMachineId("calvin");
//        calvinEntry.setLocator("calvin.com");
//        calvin.setMachineEntry(calvinEntry);
//
//        resourceStore.create("/machines/calvin",
//                new PersistedResource("me", CarraDataUtil.toBytes(calvin)));
//
//        MachineEntry hobbesEntry = new MachineEntry();
//        hobbesEntry.setMachineId("hobbes");
//        hobbesEntry.setLocator("hobbes.calvin.com");
//        hobbes.setMachineEntry(hobbesEntry);
//
//        resourceStore.create("/machines/hobbes",
//                new PersistedResource("me", CarraDataUtil.toBytes(hobbes)));
//
//        MachineEntry test1Entry = new MachineEntry();
//        test1Entry.setMachineId("test1");
//        test1Entry.setLocator("test1.com");
//        test1.setMachineEntry(test1Entry);
//        resourceStore.create("/machines/test1",
//                new PersistedResource("me", CarraDataUtil.toBytes(test1)));
//
//        MachineEntry test2Entry = new MachineEntry();
//        test2Entry.setLocator("test2.com");
//        test2Entry.setMachineId("test2");
//        test2.setMachineEntry(test2Entry);
//        resourceStore.create("/machines/test2",
//                new PersistedResource("me", CarraDataUtil.toBytes(test2)));
//
//        MachineEntry test3Entry = new MachineEntry();
//        test3Entry.setMachineId("test3");
//        test3Entry.setLocator("test3.com");
//        test3.setMachineEntry(test3Entry);
//
//        resourceStore.create("/machines/test3",
//                new PersistedResource("me", CarraDataUtil.toBytes(test3)));
//    }
//
//    @Test
//    public void doTest() throws Exception {
//        final String base = "test/integration/peergroups";
//        final String machineId = carraUser.getUsername();
//        Reference ref = new Reference(Protocol.HTTP, "localhost", port);
//        ref.setPath(base);
//
//        Response rep = sendAuthenticatedRequest(Method.GET, carraUser, ref, null);
//        RoutingTableConfig rtc = JAXBUtils.unmarshal(rep.getEntityAsText(), RoutingTableConfig.class);
//
//
//        assertNotNull(findStudy(rtc.getPeerGroups(), "testStudy1"));
//        assertNotNull(findStudy(rtc.getPeerGroups(), "testStudy2"));
//        assertEquals(5, findStudy(rtc.getPeerGroups(), "testStudy1").getChildren().size());
//        assertEquals(3, findStudy(rtc.getPeerGroups(), "testStudy2").getChildren().size());
//        return;
//
//
//    }
//
//    private Response sendAuthenticatedRequest(Method m, CarraUserInfo actor, Reference ref, Representation machineRep)
//            throws JAXBException, UnsupportedEncodingException {
//        // create the request and send it off
//
//
//        Request request = new Request();
//        request.setMethod(m);
//        request.setResourceRef(ref);
//        request.setEntity(machineRep);
//
//        return client.handle(request);
//    }
//
//
//    private PeerGroupConfig findStudy(List<PeerGroupConfig> pgc, String studyName) {
//
//        for (PeerGroupConfig config : pgc) {
//            if (config.getGroupName().equals(studyName)) {
//                return config;
//            }
//        }
//        return null;
//
//
//    }
//
//    @After
//    public final void tearDown() throws Exception {
//        component.stop();
//    }


}
