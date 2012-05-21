package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.jaxb.CarraDataUtil;
import edu.chip.carranet.jaxb.Machine;
import edu.chip.carranet.ExternalExceptions.AuthorizationFailedError;
import edu.chip.carranet.ExternalExceptions.BadResourceError;
import edu.chip.carranet.ExternalExceptions.ExternalError;
import edu.chip.carranet.ExternalExceptions.ResourceNotFoundError;
import edu.chip.carranet.overlay.Util;
import edu.chip.carranet.overlay.authorization.OLSOperation;
import edu.chip.carranet.overlay.authorization.OwnerAuthorization;
import edu.chip.carranet.overlay.persistance.PersistedResource;
import org.apache.log4j.Logger;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by IntelliJ IDEA.
 * DecoratedUser: justin
 * Date: Mar 10, 2010
 * Time: 7:00:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MachineResource extends PersistableResource {
    private static final Logger log = Logger.getLogger(MachineResource.class);
    private static final String RESOURCE_NAME = "machineName";


    @Get
    public Representation getMachine() throws
            JAXBException, AuthorizationFailedError, IOException,
            ClassNotFoundException, ResourceNotFoundError {
//        String path = getRequest().getResourceRef().getPath(true);
//        PersistedResource pr = resourceStore.read(path);
//        if (pr == null) {
//            throw new ResourceNotFoundError();
//        }
//
//        OwnerAuthorization.validateAccess(OLSOperation.GET, getChallengeResponse(), pr);
//
//        Machine m = CarraDataUtil.machineFromBytes(pr.getPayload());
//        return Util.generateResponseRepresentation(m);

        return null;
    }

    @Put
    public Representation updateMachine(Reader input) throws
            ExternalError, JAXBException, IOException {

//        String machineName = (String) getRequest().getAttributes().get(RESOURCE_NAME);
//        String path = getRequest().getResourceRef().getPath(true);
//        PersistedResource pr = resourceStore.read(path);
//
//        if (pr == null) {
//            throw new ResourceNotFoundError();
//        }
//
//        String id = getIdentityOrNull();
//        OwnerAuthorization.validateAccess(OLSOperation.UPDATE, getChallengeResponse(), pr);
//
//
//        Machine u = JAXBUtils.unmarshal(input, Machine.class);
//
//        // validate machineName matches (check at this layer?)
//        if (!u.getMachineEntry().getMachineId().equals(machineName)) {
//            throw new BadResourceError("bad request: machineIds don't match");
//        }
//
//        // the update was not made
//
//        if (resourceStore.update(path, new PersistedResource(id, CarraDataUtil.toBytes(u))) == null) {
//            throw new ResourceNotFoundError();
//        }
//        setStatus(Status.SUCCESS_NO_CONTENT);
        return null;
    }

    @Delete
    public Representation deleteMachine()
            throws AuthorizationFailedError, ResourceNotFoundError {
//        String path = getRequest().getResourceRef().getPath(true);
//        PersistedResource pr = resourceStore.read(path);
//
//        OwnerAuthorization.validateAccess(OLSOperation.DELETE, getChallengeResponse(), pr);
//
//        // if the resource doesn't exist, let's just call it as good as deleted
//        if (pr == null) {
//            setStatus(Status.SUCCESS_NO_CONTENT);
//            return null;
//        }
//
//        resourceStore.delete(path);
//        setStatus(Status.SUCCESS_NO_CONTENT);
        return null;
    }
}
