package edu.chip.carranet.overlay;

import org.restlet.data.MediaType;
import org.restlet.representation.InputRepresentation;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;

/**
 * I hate classes called Util...but this'll be my holding ground
 * for useful helper methods that span classes.
 *
 * Created by IntelliJ IDEA.
 * DecoratedUser: justin
 * Date: Mar 10, 2010
 * Time: 9:12:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    /**
     * TODO: how do i constraint T to be something from jaxb?
     */
    public static <T> InputRepresentation generateResponseRepresentation(T response) throws JAXBException {
        JAXBContext responseContext = JAXBContext.newInstance(response.getClass());
        Marshaller marshaller = responseContext.createMarshaller();

        // yuck, buffer it all in memory.  ok so long as our xml representations are small.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter out = new OutputStreamWriter(baos);
        marshaller.marshal(response, out);
        return new InputRepresentation(new ByteArrayInputStream(baos.toByteArray()), MediaType.APPLICATION_XML);
    }
}
