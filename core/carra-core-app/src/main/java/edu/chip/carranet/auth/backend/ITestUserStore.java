package edu.chip.carranet.auth.backend;


public interface ITestUserStore extends IUserStore {
    public void dropAllUsers();

}
