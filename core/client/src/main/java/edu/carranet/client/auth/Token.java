package edu.carranet.client.auth;


import org.apache.log4j.Logger;
import org.restlet.engine.util.Base64;
import org.spin.tools.JAXBUtils;
import org.spin.tools.crypto.signature.Identity;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * This is just a generic token object for the CarraClient API.  We're abstracting away the actual
 * Spin implemenation of the Idenity for simplicity's sake and so we're not tied to it
 * on the client side.
 */
public class Token {

    private Identity id;
    private static Logger log = Logger.getLogger(Token.class);

    public Token(Identity id) {
        this.id = id;
    }


    public String getUserName() {
        if (id == null) {
            return "";
        }
        return id.getUsername();
    }


    public String getAuthString() {
        String xmlString = "";
        try {
            xmlString = JAXBUtils.marshalToString(id);
        } catch (JAXBException e) {
            log.fatal("Error unmarshalling ID, shouldn't happen");
        }

        String returnString = Base64.encode(xmlString.getBytes(), false);
        return returnString;

    }

   public List<String> getUserStudies(){
       ArrayList<String> studies = new ArrayList<String>();
       for(String s : id.getAssertion()){
         String[] tokens = s.split(":");
         if(tokens.length == 2 && tokens[0].equals("project")){
            studies.add(tokens[1]);
         }
       }
       return studies;
   }

    public Date getExpirationDate() {
        if (id == null) {
            return new Date();
        }

        XMLGregorianCalendar gregCal = id.getTimestamp();
        Calendar cal = gregCal.toGregorianCalendar();
        return cal.getTime();
    }


}
