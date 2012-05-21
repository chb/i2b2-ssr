package edu.chip.carranet.auth.backend;

import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.LDAPConnection;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


@Ignore
public class TestLDAPBackend {


    @Test
    public void testAuthenticateAdmin() throws Exception {
        //LDAPAuthenticator store = new LDAPAuthenticator();

        final List<DN> searchResults = new ArrayList<DN>();

        final DN dave = new DN("uid=dave,ou=people,dc=carranet,dc=org");
        searchResults.add(dave);

        LDAPConnection con = new LDAPConnection("ldap.chboston.org",
                389, "CN=CarraNetSvc,OU=Special Accounts,DC=CHBDir,DC=Org", "D87kdRs1");

        System.out.println("Hi");


        //LDAPAuthenticator ldapAuth = new LDAPAuthenticator();

        //ldapAuth.setAdminDNs(searchResults);

        //ldapAuth.authenticate("dave", "test");


        //Boolean result = null;
        //Identity id = store.authenticate("dave", "password");
        //assertNotNull(id);
    }
}
