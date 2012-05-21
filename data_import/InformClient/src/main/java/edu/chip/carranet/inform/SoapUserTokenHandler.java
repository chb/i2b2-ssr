package edu.chip.carranet.inform;

import org.apache.ws.security.WSConstants;
import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.WSSecTimestamp;
import org.apache.ws.security.message.WSSecUsernameToken;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

/**
 * This class is used to insert WSSecurity UserToken
 *
 * @author Justin Quan
 * @version %I% Date: 2/18/11
 */
public class SoapUserTokenHandler implements SOAPHandler<SOAPMessageContext> {

    private final String user;
    private final String pass;

    public SoapUserTokenHandler(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    @Override
    public Set<QName> getHeaders() {
        // TODO: log?
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            SOAPMessage soapMsg = context.getMessage();
            SOAPPart soappart = soapMsg.getSOAPPart();
            SOAPEnvelope soapEnv = soappart.getEnvelope();
            SOAPHeader soapHeader = soapEnv.getHeader();

            //if no header, add one
            if (soapHeader == null){
                soapHeader = soapEnv.addHeader();

            }

            WSSecHeader wsheader = new WSSecHeader();
            wsheader.insertSecurityHeader(soappart);

            // timestamp
            WSSecTimestamp timestamp = new WSSecTimestamp();
            timestamp.build(soappart, wsheader);

            // user token
            WSSecUsernameToken token = new WSSecUsernameToken();
            token.setPasswordType(WSConstants.PW_TEXT);
            token.setUserInfo(this.user, this.pass);
            token.addNonce();
            token.addCreated();
            token.build(soappart, wsheader);

            // action
            // MessageId?

            // ReplyTo

            // To

        } catch (SOAPException se) {
            // TODO: log
        }

        return true;

    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        // TODO: log?
        return true;
    }

    @Override
    public void close(MessageContext context) {
        // TODO: log?
    }
}
