package exotrack;

import java.util.Scanner;

import exotrack.database.UserDatabase;
import exotrack.database.exercises.Pushup;

/**
 * Runs the ExoTrack program
 * TODO:
 * Add program loop
 * Add GUI support
 */
public class ExoTrack {
    public static void main(String[] args){
        UserDatabase udb = new UserDatabase();
        User Chris = new User("Chris",false,udb);
        Pushup p =  new Pushup(Chris,20);
    }
}
