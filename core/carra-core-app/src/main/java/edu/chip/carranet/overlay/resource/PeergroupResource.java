package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.jaxb.CarraDataUtil;
import edu.chip.carranet.jaxb.Machine;
import edu.chip.carranet.jaxb.Study;
import edu.chip.carranet.overlay.persistance.PersistedResource;
import edu.chip.carranet.overlay.util.OverlayCreator;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PeergroupResource extends PersistableResource {


    @Get
    public Representation getRoutingTable() throws ClassNotFoundException, IOException, JAXBException {
        List<PersistedResource> resourceList = resourceStore.getAll("/studies/");


        Map<Study, List<String>> locatorMap = new HashMap<Study, List<String>>();
        for (PersistedResource p : resourceList) {
            Study s = CarraDataUtil.studyFromBytes(p.getPayload());
            locatorMap.put(s, new ArrayList<String>());

            for (String machineId : s.getStudyEntry().getMachineIds()) {
                PersistedResource res = resourceStore.read("/machines/" + machineId);
                if (res != null) {
                    Machine m = CarraDataUtil.machineFromBytes(res.getPayload());
                    locatorMap.get(s).add(m.getMachineEntry().getLocator());
                }
            }

        }
        return new StringRepresentation(JAXBUtils.marshalToString(
                OverlayCreator.getRoutingTable(locatorMap)));
    }
}
