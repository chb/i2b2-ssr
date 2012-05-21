package edu.chip.carranet.auth.resource;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

/**
 * Simple health check resource.  That's it!
 *
 * @author Justin Quan
 * @link http://chip.org Date: 6/23/11
 */
public class HealthCheckResource extends AbstractAuthResource {

    @Get
    public Representation healthCheck() {
        return new StringRepresentation("Healthy");
    }
}
