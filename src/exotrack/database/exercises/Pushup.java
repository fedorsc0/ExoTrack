package exotrack.database.exercises;

import java.time.LocalDateTime;

import exotrack.User;

/**
 * Pushup exercise. All modifications to the database are done through the UserDatabase. This class stores all the
 * SQL commands to required by UserDatabase to create and handle the pushup table
 * Table: pushup
 * Columns: user_id, time, reps
 * TODO: Add methods to get total pushups, average pushups per day, total calories burned, etc.
 */
public class Pushup extends Exercise{

    double calories = 0.34; //Calories burned per pushup
    String exerciseName = "pushup"; //Name of table
    String[] columns = {"user_id","time","reps"}; //Name of columns
    String[] initColumns = {"user_id integer NOT NULL","time text NOT NULL",
            "reps integer NOT NULL","FOREIGN KEY (user_id) REFERENCES users"}; //sql commands to intialize columns
    String[] values; //Values to be stored in columns
    User user;

    /**
     * Adds the givens reps to the given user
     * @param inputUser User to add reps to
     * @param reps Reps completed
     */
    public Pushup(User inputUser, int reps){
        String timeString = "'" + LocalDateTime.now().toString() + "'"; //Change date to SQL String
        String IDString = String.valueOf(inputUser.userID);
        values = new String[3];
        values[0] = IDString;
        values[1] = timeString;
        values[2] = String.valueOf(reps);
        inputUser.userDatabase.addExercise(this); //Write data to database
    }

    public String getName(){
        return exerciseName;
    }

    public String[] initColumns(){
        return initColumns;
    }

    public String[] getColumns(){
        return columns;
    }

    public String[] getValues(){
        return values;
    }

}
