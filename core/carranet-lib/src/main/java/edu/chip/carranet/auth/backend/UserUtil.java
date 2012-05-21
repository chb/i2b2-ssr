package edu.chip.carranet.auth.backend;

import edu.chip.carranet.jaxb.CarraUserInfo;
import org.spin.tools.crypto.signature.Identity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserUtil {

    public final static String ROLE_PREFIX = "role";
    public final static String STUDY_PREFIX = "study";
    public final static String HOMESITE_PREFIX = "homesite";

    public static List<String> getStudiesFromUser(CarraUserInfo user) {
        return getValuesForPrefix(STUDY_PREFIX, user.getAssertions());
    }

    public static List<UserPermissions> getsRolesFromUser(CarraUserInfo user) {
        List<UserPermissions> ret = new ArrayList<UserPermissions>();
        for(String s : getValuesForPrefix(ROLE_PREFIX, user.getAssertions())) {
            ret.add(UserPermissions.valueOf(s));
        }
        return ret;
    }

    public static List<UserPermissions> getRolesFromIdentity(Identity id) {
        List<UserPermissions> ret = new ArrayList<UserPermissions>();
        for(String s : getValuesForPrefix(ROLE_PREFIX, id.getAssertion())) {
            ret.add(UserPermissions.valueOf(s));
        }
        return ret;
    }

    public static List<String> getStudiesFromIdentity(Identity id) {
        return getValuesForPrefix(STUDY_PREFIX, id.getAssertion());
    }



    public static String createStudyString(String study) {
        return STUDY_PREFIX + ":" + study;
    }

    public static String createHomesiteString(String homesSite) {
        return HOMESITE_PREFIX + ":" + homesSite;
    }


    public static List<String> getValuesForPrefix(String prefix, Collection<String> strings) {
        List<String> returnStringList = new ArrayList<String>();
        for (String s : strings) {
            String[] tokens = s.split(":");
            if (tokens.length == 2) {
                if (tokens[0].equals(prefix)) {
                    returnStringList.add(tokens[1]);
                }
            }
        }
        return returnStringList;
    }


}
