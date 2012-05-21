package edu.chip.carranet.auth.backend;

import edu.chip.carranet.auth.CarraIdentityService;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.spin.query.message.identity.IdentityServiceException;
import org.spin.tools.crypto.signature.Identity;

import static org.junit.Assert.*;


@Ignore
public class TestCarraIdentityService {


    @Test
    public void testCarraIdentityService() throws Exception{
        CarraUserInfo user = new CarraUserInfo();
        user.setUsername("Dave");
        Identity id = TokenUtil.createToken(user);

        CarraIdentityService service = new CarraIdentityService();

        service.certify(null, "Dave", TokenUtil.convertIdentityToAuthString(id));
        assertEquals(id,id);

        boolean worked = true;
        try{
            service.certify(null,"dave", "JUNK");
        }
        catch (IdentityServiceException e){
            worked = false;
        }


        assertEquals(false, worked);

        //Testing Username mismatch between param and token
        worked = true;
         try{

            service.certify(null,"justin", TokenUtil.convertIdentityToAuthString(id));
        }
        catch (IdentityServiceException e){
            worked = false;
        }


         assertEquals(false, worked);

    }



}
