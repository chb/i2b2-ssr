package edu.chip.carranet.server.backend;

import edu.carranet.client.auth.IAuthClient;
import edu.carranet.client.auth.RestletAuthClient;
import edu.carranet.client.auth.Token;
import edu.carranet.client.exception.AuthClientException;
import edu.chip.carranet.auth.backend.AuthCapabilityEnum;
import edu.chip.carranet.auth.backend.UserPermissions;
import edu.chip.carranet.auth.backend.UserUtil;
import edu.chip.carranet.client.data.GWTUserPermissions;
import edu.chip.carranet.client.data.UserDTO;
import edu.chip.carranet.jaxb.CarraUserInfo;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class RemoteAuthServiceBackend implements IAuthBackend {
    private static final Logger log = Logger.getLogger(RemoteAuthServiceBackend.class);

    private IAuthClient client;


    @Override
    public UserDTO getUser(Token t, String userName) throws AuthClientException {
        UserDTO returnVal = new UserDTO();
        CarraUserInfo info = client.getUserInfo(t, userName);
        return carraUserInfoToUserDTO(info);


    }

    private UserDTO carraUserInfoToUserDTO(CarraUserInfo u) throws AuthClientException {

        //TODO - Make this not have to figure out how to parse out Projects and Roles from the CarraUserInfo
        //Bean, we don't store projects/Roles in CarraUserInfo just assertsions and it sucks that we have to know
        //how to differentiate between the two right here.
        UserDTO returnVal = new UserDTO();
        returnVal.setUserName(u.getUsername());
        List<GWTUserPermissions> roles = new ArrayList<GWTUserPermissions>();
        List<String> studies = new ArrayList<String>();
        List<String> homeSites = new ArrayList<String>();

        for(String s : u.getAssertions()) {
            String[] stringArray = s.split(":");
            if(stringArray.length == 2) {
                if(stringArray[0].equals(UserUtil.STUDY_PREFIX) && stringArray[1] != null) {
                    studies.add(stringArray[1]);
                }
                if(stringArray[0].equals(UserUtil.ROLE_PREFIX) && stringArray[1] != null) {
                    roles.add(GWTUserPermissions.valueOf(stringArray[1]));
                }
                if(stringArray[0].equals(UserUtil.HOMESITE_PREFIX) && stringArray[1] != null) {
                    homeSites.add(stringArray[1]);
                }
            }
        }

        returnVal.setRoles(roles);
        returnVal.setStudies(studies);
        returnVal.setHomeSites(homeSites);
        return returnVal;

    }

    private CarraUserInfo userDTOtoCarraUserInfo(UserDTO u) {
        CarraUserInfo returnVal = new CarraUserInfo();
        returnVal.setUsername(u.getUserName());
        for(GWTUserPermissions s : u.getRoles()) {
            returnVal.getAssertions().add(s.toAssertionString());
        }

        for(String s : u.getStudies()) {
            returnVal.getAssertions().add(UserUtil.createStudyString(s));
        }

        for(String s : u.getHomeSites()) {
            returnVal.getAssertions().add(UserUtil.createHomesiteString(s));
        }
        return returnVal;
    }


    public RemoteAuthServiceBackend(String address) throws Exception {
        client = new RestletAuthClient(address);
    }

    public void setClient(IAuthClient client) {
        this.client = client;
    }

    @Override
    public Token authorizeUser(String userName, String password)
            throws AuthClientException {
        return client.getAuthorization(userName, password);
    }

    @Override
    public ArrayList<UserDTO> getUsers(Token t)
            throws AuthClientException {
        List<CarraUserInfo> userList = client.getUsers(t);

        ArrayList<UserDTO> returnList = new ArrayList<UserDTO>();

        for(CarraUserInfo userInfo : userList) {
            returnList.add(carraUserInfoToUserDTO(userInfo));
        }
        return returnList;

    }

    @Override
    public UserDTO deleteUser(Token t, UserDTO u)
            throws AuthClientException {
        client.deleteUser(t, u.getUserName());
        return u;


    }

    @Override
    public UserDTO addUser(Token t, UserDTO u, String newPassword)
            throws AuthClientException {
        client.addUser(t, u.getUserName(), newPassword);
        client.updateUser(t, u.getUserName(), userDTOtoCarraUserInfo(u));


        return u;
    }


    @Override
    public UserDTO updateUser(Token t, UserDTO dto) throws AuthClientException {
        client.updateUser(t, dto.getUserName(), userDTOtoCarraUserInfo(dto));
        return dto;
    }

    @Override
    public boolean canChangePassword(Token t) throws AuthClientException {
        return client.getCapabilities(t).getCapability().contains(AuthCapabilityEnum.CHANGE_PASSWORD.getStringValue());
    }


}
