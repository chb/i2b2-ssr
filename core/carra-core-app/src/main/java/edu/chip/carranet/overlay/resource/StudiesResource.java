package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.jaxb.CarraDataUtil;
import edu.chip.carranet.jaxb.Studies;
import edu.chip.carranet.jaxb.Study;
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
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 19, 2010
 */
public class StudiesResource extends PersistableResource {
    private static final Logger log = Logger.getLogger(StudiesResource.class);

    @Post
    public Representation createStudy(Reader input) throws AuthorizationFailedError, JAXBException,
            ResourceAlreadyExistsError, IOException {
        // most post requests have the body in
        // application/x-www-form-urlencoded
        // something to consider

//        String path = getRequest().getResourceRef().getPath(true);
//        PersistedResource pr = resourceStore.read(path);
//        String id = getIdentityOrNull();
//        OwnerAuthorization.validateAccess(OLSOperation.CREATE, getChallengeResponse(), pr);
//        Study study = JAXBUtils.unmarshal(input, Study.class);
//        try {
//            // store studyId -> DecoratedStudy
//            this.resourceStore.create(path + study.getStudyEntry().getStudyId(), new PersistedResource(id, CarraDataUtil.toBytes(study)));
//        } catch (ResourceAlreadyExistsException e) {
//            throw new ResourceAlreadyExistsError();
//        }
//
//        // create response
//        AuthResponse response = new AuthResponse();
//        response.setMessage("Study Added");
//        response.setResponseCode(200);
//        getResponse().setLocationRef("/studies/" + study.getStudyEntry().getStudyId());
//
//        return Util.generateResponseRepresentation(response);
        return null;
    }


    @Get
    public Representation getStudies() throws JAXBException, ClassNotFoundException, IOException {
        Studies studies = new Studies();
        List<PersistedResource> resources = resourceStore.getAll("/studies/");
        for (PersistedResource p : resources) {
            Study s = CarraDataUtil.studyFromBytes(p.getPayload());
            studies.getStudyList().add(s.getStudyEntry());
        }

        return new StringRepresentation(JAXBUtils.marshalToString(studies));
    }
}
