package exotrack;

import exotrack.database.UserDatabase;

/**
 * User object. Has all methods nessecary to uniquely identify user and manage user accountsContains name of the
 * user, ID of user, and database object associated with user.
 */
public class User {
    public int userID;
    public String name;
    public UserDatabase userDatabase;

    /**
     * Creates a new user. This can be done by either loading an old profile or creating a new one.
     * @param inputName Name of user
     * @param newProfile true = create new user; false = load old user profile
     * @param database Database holding user data
     */
    User(String inputName, boolean newProfile, UserDatabase database){
        userDatabase = database;
        if(newProfile) {
            createProfile(inputName);
        }else{
            loadProfile(inputName);
        }
    }

    /**
     * Creates a new user profile
     * @param inputName name of user
     * TODO: password/email support
     */
    void createProfile(String inputName){
        name = inputName;
        userID = userDatabase.addUser(inputName);
        System.out.println("Profile successfully created!");
        System.out.println("UserID: " + String.valueOf(userID));
    }

    /**
     * Loads an old user profile
     * @param inputName name of user
     * TODO: password/email support
     */
    void loadProfile(String inputName){
        userID = userDatabase.getUserID(inputName);
        System.out.println("UserID: " + String.valueOf(userID));
    }

}
