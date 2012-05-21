package edu.chip.carranet.overlay.util;

import edu.chip.carranet.jaxb.Study;
import org.spin.tools.config.EndpointConfig;
import org.spin.tools.config.EndpointType;
import org.spin.tools.config.PeerGroupConfig;
import org.spin.tools.config.RoutingTableConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class implments a few static methods which take studies/machineLocations and generates a
 * Spin routing table
 */
public class OverlayCreator {

    /**
     * Takes a map of Studies to a list of their machine locations
     * and returns a peergroup file
     *
     * @param locatorMap
     * @return
     */
    public static RoutingTableConfig getRoutingTable(Map<Study, List<String>> locatorMap) {
        RoutingTableConfig rtc = new RoutingTableConfig();
        for (Study s : locatorMap.keySet()) {
            List<EndpointConfig> endpointList = new ArrayList<EndpointConfig>();


            for (String locator : locatorMap.get(s)) {
                EndpointConfig ec = new EndpointConfig(EndpointType.SOAP, locator);
                endpointList.add(ec);
            }

            PeerGroupConfig pg = new PeerGroupConfig(s.getStudyEntry().getStudyId(), null, endpointList);
            rtc = rtc.withPeerGroup(pg);

        }
        return rtc;
    }


}
