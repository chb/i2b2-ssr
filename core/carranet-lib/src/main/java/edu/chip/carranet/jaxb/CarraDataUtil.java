package edu.chip.carranet.jaxb;

import org.apache.log4j.Logger;
import org.spin.tools.JAXBUtils;

import javax.xml.bind.JAXBException;
import java.io.*;


/**
 * This is a workaround because we can't extend JAXB objects so we need to implement
 * some of their functionality here
 */
public class CarraDataUtil {

    private static Logger log = Logger.getLogger(CarraDataUtil.class);


    public static byte[] toBytes(Object o) throws IOException {
        try {
            return JAXBUtils.marshalToString(o).getBytes();
        } catch (JAXBException e) {
            throw new RuntimeException("Bad");
        }
    }



    public static Machine machineFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        try {
            return JAXBUtils.unmarshal(new String(bytes), Machine.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Bad");
        }
    }

    public static Study studyFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
            try {
            return JAXBUtils.unmarshal(new String(bytes), Study.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Bad");
        }
    }

    public static User userFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
              try {
            return JAXBUtils.unmarshal(new String(bytes), User.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Bad");
        }
    }


}
