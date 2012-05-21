package edu.chip.carranet.overlay.persistence;

import edu.chip.carranet.ExternalExceptions.ResourceAlreadyExistsError;
import edu.chip.carranet.jaxb.*;
import edu.chip.carranet.overlay.persistance.PersistedResource;
import edu.chip.carranet.overlay.persistance.ResourceStore;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 15, 2010
 */
public abstract class OverlayStoreTest {

    private ResourceStore rs;

    public abstract ResourceStore createResourceStore();

    @Before
    public final void setup() {
        this.rs = createResourceStore();
    }

    @Test
    public void testCRUD() throws Exception {
        final String machinePrefix = "machines/";
        final String studyPrefix = "studies/";
        final String owner = "me";

        final String machineName = "foo";
        Machine m1 = new Machine();
        MachineEntry me1 = new MachineEntry();
        me1.setMachineId("alpha");
        me1.setLocator("one");
        m1.setMachineEntry(me1);

        Machine m2 = new Machine();
        MachineEntry me2 = new MachineEntry();
        me2.setMachineId("beta");
        me2.setLocator("two");
        m2.setMachineEntry(me2);

        rs.create(machinePrefix + machineName, new PersistedResource(owner, CarraDataUtil.toBytes(m1)));
        assertEquals(m1, CarraDataUtil.machineFromBytes(rs.read(machinePrefix + machineName).getPayload()));
        rs.update(machinePrefix + machineName, new PersistedResource(owner, CarraDataUtil.toBytes(m2)));
        assertEquals(m2, CarraDataUtil.machineFromBytes(rs.read(machinePrefix + machineName).getPayload()));
        rs.delete(machinePrefix + machineName);
        assertEquals(null, rs.read(machinePrefix + machineName));

        final String studyName = "foo";
        Study s1 = new Study();
        StudyEntry se1 = new StudyEntry();
        se1.setStudyId("alpha");
        se1.getMachineIds().add("one");
        s1.setStudyEntry(se1);

        Study s2 = new Study();
        StudyEntry se2 = new StudyEntry();
        se2.setStudyId("beta");
        se2.getMachineIds().add("two");
        s2.setStudyEntry(se2);

        rs.create(studyPrefix + studyName, new PersistedResource(owner, CarraDataUtil.toBytes(s1)));
        assertEquals(s1, CarraDataUtil.studyFromBytes(rs.read(studyPrefix + studyName).getPayload()));
        rs.update(studyPrefix + studyName, new PersistedResource(owner, CarraDataUtil.toBytes(s2)));
        assertEquals(s2, CarraDataUtil.studyFromBytes(rs.read(studyPrefix + studyName).getPayload()));
        rs.delete(studyPrefix + studyName);
        assertEquals(null, rs.read(studyPrefix + studyName));

        final String userName = "foo";
        User u1 = new User();
        UserEntry ue1 = new UserEntry();
        ue1.setUserId("alpha");
        ue1.getStudyIds().add("one");
        u1.setUserEntry(ue1);

        User u2 = new User();
        UserEntry ue2 = new UserEntry();
        ue2.setUserId("beta");
        ue2.getStudyIds().add("two");
        u2.setUserEntry(ue2);
    }

    @Test(expected = ResourceAlreadyExistsError.class)
    public void testResourceAlreadyExists() throws Exception {
        rs.create("m", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine())));
        rs.create("m", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine())));
    }

    @Test
    public void testGetAll() throws Exception {
        Machine m1 = new Machine();

        Machine m2 = new Machine();
        Machine m3 = new Machine();
        Machine m4 = new Machine();


        rs.create("/machine/m1", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine())));
        rs.create("/machine/m2", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine())));
        rs.create("/machine/m3", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine())));
        rs.create("/machine/m4", new PersistedResource("owner", CarraDataUtil.toBytes(new Machine())));
        List<PersistedResource> returnList = rs.getAll("/machine/");

        assertEquals(returnList.size(), 4);


    }

}
