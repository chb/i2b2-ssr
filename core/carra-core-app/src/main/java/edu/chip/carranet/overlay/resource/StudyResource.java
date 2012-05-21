package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.jaxb.CarraDataUtil;
import edu.chip.carranet.jaxb.Study;
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
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 19, 2010
 */
public class StudyResource extends PersistableResource {
    private static final Logger log = Logger.getLogger(StudyResource.class);
    private static final String RESOURCE_NAME = "studyName";

    @Get
    public Representation getStudy() throws
            JAXBException, AuthorizationFailedError, IOException, ClassNotFoundException, ResourceNotFoundError {
//        String path = getRequest().getResourceRef().getPath(true);
//        PersistedResource pr = resourceStore.read(path);
//        if(pr == null) {
//            throw new ResourceNotFoundError();
//        }
//
//        OwnerAuthorization.validateAccess(OLSOperation.GET, getChallengeResponse(), pr);
//
//        Study s = CarraDataUtil.studyFromBytes(pr.getPayload());
//
//        return Util.generateResponseRepresentation(s);
        return null;
    }

    @Put
    public Representation updateStudy(Reader input) throws
            JAXBException, IOException, ExternalError {
        
//        String studyName = (String) getRequest().getAttributes().get(RESOURCE_NAME);
//        String path = getRequest().getResourceRef().getPath(true);
//        PersistedResource pr = resourceStore.read(path);
//
//        if(pr == null) {
//            throw new ResourceNotFoundError();
//        }
//
//        String id = getIdentityOrNull();
//        OwnerAuthorization.validateAccess(OLSOperation.UPDATE, getChallengeResponse(), pr);
//
//        Study s = JAXBUtils.unmarshal(input, edu.chip.carranet.jaxb.Study.class);
//
//        // validate studyName matches (check at this layer?)
//        if(!s.getStudyEntry().getStudyId().equals(studyName)) {
//            throw new BadResourceError("bad request: studyIds don't match");
//        }
//
//        // the update was not made
//        if(resourceStore.update(path, new PersistedResource(id, CarraDataUtil.toBytes(s))) == null) {
//            throw new ResourceNotFoundError();
//        }
//        setStatus(Status.SUCCESS_NO_CONTENT);
        return null;
    }

    @Delete
    public Representation deleteStudy(Representation input)
            throws AuthorizationFailedError, ResourceNotFoundError {

//        String path = getRequest().getResourceRef().getPath(true);
//        PersistedResource pr = resourceStore.read(path);
//
//        OwnerAuthorization.validateAccess(OLSOperation.DELETE, getChallengeResponse(), pr);
//
//        // if the resource doesn't exist, let's just call it as good as deleted
//        if(pr == null) {
//            setStatus(Status.SUCCESS_NO_CONTENT);
//            return null;
//        }
//
//        resourceStore.delete(path);
//        setStatus(Status.SUCCESS_NO_CONTENT);
        return null;
    }
}
