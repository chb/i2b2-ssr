package edu.chip.carranet.overlay.resource;

import edu.chip.carranet.overlay.persistance.ResourceStore;
import org.restlet.resource.ServerResource;

/**
 * @author Justin Quan
 * @version %I%
 *          Date: Mar 16, 2010
 */
public abstract class PersistableResource extends ServerResource {
    protected final ResourceStore resourceStore = null; //OverlayStoreFactory.getInstance().getResourceStore();


    //TODO: doesn't really belong here as a persistable resource ability,
    // but more a convenient helper for authorizing resource access.
    protected String getIdentityOrNull() {
        return getChallengeResponse() == null ? null : getChallengeResponse().getIdentifier(); 
    }

}
