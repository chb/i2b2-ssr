package edu.chip.carranet.server.backend;

import edu.carranet.client.auth.Token;
import edu.carranet.client.exception.AuthClientException;
import edu.chip.carranet.client.data.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MockAuthServiceBackend implements IAuthBackend {

    public static Map<String, UserDTO> users = new HashMap<String, UserDTO>();

    static {
        UserDTO user = new UserDTO();
        user.setUserName("Dave");
        UserDTO user2 = new UserDTO();
        user2.setUserName("Justin");

        users.put("Dave", user);
        users.put("Justin", user2);
    }


    @Override
    public UserDTO getUser(Token t, String userName) throws AuthClientException {
        UserDTO dto = users.get(userName);
        if (dto != null) {
            return dto;
        }
        throw new RuntimeException("Unknown User");

    }


    public MockAuthServiceBackend() {

    }

    @Override
    public Token authorizeUser(String userName, String password) {
        return new Token(null);
    }

    @Override
    public ArrayList<UserDTO> getUsers(Token t) {
        return new ArrayList(users.values());
    }

    @Override
    public UserDTO deleteUser(Token t, UserDTO u) {
        if (users.containsKey(u.getUserName())) {
            users.remove(u.getUserName());
            return u;
        }


        throw new RuntimeException("No Such UserDTO");

    }


    @Override
    public UserDTO addUser(Token t, UserDTO u, String password) {

        users.put(u.getUserName(), u);
        return u;


    }

    @Override
    public UserDTO updateUser(Token t, UserDTO dto) throws AuthClientException {
        if (dto != null) {
            users.put(dto.getUserName(), dto);
        }

        throw new RuntimeException("Can't add empty user");

    }

    @Override
    public boolean canChangePassword(Token t) throws AuthClientException {
        return true;
    }
}
