package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.jaxb.CarraDataUtil;
import edu.chip.carranet.jaxb.Machine;
import edu.chip.carranet.jaxb.Machines;
import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.ExternalExceptions.ResourceAlreadyExistsError;
import edu.chip.carranet.overlay.Util;
import edu.chip.carranet.overlay.authorization.OLSOperation;
import edu.chip.carranet.overlay.authorization.OwnerAuthorization;
import edu.chip.carranet.overlay.persistance.PersistedResource;
import edu.chip.carranet.overlay.persistance.ResourceAlreadyExistsException;
import org.apache.log4j.Logger;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * DecoratedUser: justin
 * Date: Mar 10, 2010
 * Time: 5:38:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MachinesResource extends PersistableResource {
    //TODO: investigate logger
    private static final Logger log = Logger.getLogger(MachineResource.class);

//    @Post
//    public Representation createMachine(Reader input) throws AuthorizationFailedError, JAXBException, ResourceAlreadyExistsError, IOException {
//        // most post requests have the body in
//        // application/x-www-form-urlencoded
//        // something to consider
//
//        String path = getRequest().getResourceRef().getPath(true);
//        PersistedResource pr = resourceStore.read(path);
//        String id = getIdentityOrNull();
//        OwnerAuthorization.validateAccess(OLSOperation.CREATE, null, pr);
//
//        Machine machine = JAXBUtils.unmarshal(input, Machine.class);
//        try {
//            // store machineId -> DecoratedMachine
//            this.resourceStore.create(path+machine.getMachineEntry().getMachineId(), new PersistedResource(id, CarraDataUtil.toBytes(machine)));
//        } catch (ResourceAlreadyExistsException e) {
//            throw new ResourceAlreadyExistsError();
//        }
//
//        // create response
//        AuthResponse response = new AuthResponse();
//        response.setMessage("Machine Added");
//        response.setResponseCode(200);
//        getResponse().setLocationRef("/machines/"+machine.getMachineEntry().getMachineId());
//
//        return Util.generateResponseRepresentation(response);
//
//    }
//
//    @Get
//    public Representation getMachines() throws JAXBException, ClassNotFoundException, IOException {
//        Machines machines = new Machines();
//        List<PersistedResource> resources = resourceStore.getAll("/machines/");
//        for(PersistedResource p : resources){
//            Machine newMachine = CarraDataUtil.machineFromBytes(p.getPayload());
//            machines.getMachineList().add(newMachine.getMachineEntry());
//        }
//
//        return new StringRepresentation(JAXBUtils.marshalToString(machines));
//    }

}
