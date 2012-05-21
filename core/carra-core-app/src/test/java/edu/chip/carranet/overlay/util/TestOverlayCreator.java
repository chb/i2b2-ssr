package edu.chip.carranet.overlay.util;

import edu.chip.carranet.jaxb.Study;
import edu.chip.carranet.jaxb.StudyEntry;
import org.junit.Test;
import org.spin.tools.config.PeerGroupConfig;
import org.spin.tools.config.RoutingTableConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TestOverlayCreator {


    

    @Test
    public void testGetPeerGroups() throws Exception {
        Map<Study, List<String>> paramMap1 = new HashMap<Study,List<String>>();

        List<String> machines1 = new ArrayList<String>();
        machines1.add("localhost");
        machines1.add("duke");
        machines1.add("harvard");
        machines1.add("calvin");
        machines1.add("hobbes");

        List<String> machines2 = new ArrayList<String>();
        machines2.add("test1");
        machines2.add("test2");
        machines2.add("test3");

        Study s1 = new Study();
        StudyEntry entry1 = new StudyEntry();
        entry1.setStudyId("testStudy1");
        s1.setStudyEntry(entry1);


        Study s2 = new Study();
        StudyEntry studyEntry2 = new StudyEntry();
        s2.setStudyEntry(studyEntry2);
        studyEntry2.setStudyId("testStudy2");
        

        paramMap1.put(s1, machines1);
        paramMap1.put(s2, machines2);

        RoutingTableConfig rtc = OverlayCreator.getRoutingTable(paramMap1);

        assertNotNull(findStudy(rtc.getPeerGroups(), "testStudy1"));
        assertNotNull(findStudy(rtc.getPeerGroups(), "testStudy2"));

        PeerGroupConfig pgc1 = findStudy(rtc.getPeerGroups(), "testStudy1");
        assertEquals(5, pgc1.getChildren().size());

    }

      private PeerGroupConfig findStudy(List<PeerGroupConfig> pgc, String studyName) {

        for (PeerGroupConfig config : pgc) {
            if (config.getGroupName().equals(studyName)) {
                return config;
            }
        }
        return null;


    }

}
