package edu.chip.carranet.auth.backend;

import java.util.HashSet;
import java.util.Set;


/**
 * Quick data type that specifies a userName and Password pair
 */
public class User {
    private String userName;
    private int salt;
    private String hashedPassword;

    private Set<String> assertions = new HashSet<String>();


    public User(String userName, String password) {
        this.userName = userName;
        this.hashedPassword = password;
    }

    public User() {
    }


    public void setUserName(String userName) {

        this.userName = userName;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setAssertions(Set<String> assertions) {
        this.assertions = assertions;
    }

    public String getUserName() {
        return userName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Set<String> getAssertions() {
        return assertions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (hashedPassword != null ? !hashedPassword.equals(user.hashedPassword) : user.hashedPassword != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (hashedPassword != null ? hashedPassword.hashCode() : 0);
        return result;
    }
}
