package exotrack.database;

import java.io.*; //Used for writing to/from files
import java.sql.*; //Used for managing user profiles in a database;
import java.util.Arrays;

import exotrack.database.exercises.*;

/**
 * Interface from program to the database. Allows users to be added and loaded and exercises to be loggged.
 * TODO
 *  Add methods to get exercise data
 *  Add method to delete users
 */
public class UserDatabase{
    String url = "jdbc:sqlite:Profiles/UserData.db"; //url to the database
    File dataFile; //Database file

    /**
     * Construct a database if one doesn't exist.
     */
    public UserDatabase(){
        dataFile = new File("./Profiles/UserData.db");
        if(!dataFile.exists()){ //Check if database file currently exists
            createUserTable(); //If not, make a new database
        }
    }

    /**
     * Creates a connection to the database
     * @return the Connection object
     */
    private Connection connect(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Creates a table of users in the database. Each user has a unique id and possibly non-unique name
     * Columns: user_id, name
     * TODO
     * Add passwords to users
     * Add email to users
     */
    private void createUserTable(){
        String[] columns = {"user_id integer PRIMARY KEY","name text NOT NULL"};
        createTable("users", columns);
    }

    /**
     * Creates a table, if one does not already exist, with a given name and given columns. Each element in column[] is
     * made into a column. All elements of column[] are processed in order.
     * @param name String, name of table
     * @param columns String[], list of columns
     */
    private void createTable(String name, String[] columns){
        String sql = "CREATE TABLE IF NOT EXISTS " + name + " (\n"; //Check if table already exists
        int i;
        //Add all elements of columns as arguments to CREATE TABLE
        for(i=0; i < columns.length-1; i++){
            sql = sql + " " + columns[i] + ",\n";
        }
        sql = sql + " " + columns[i] + "\n);";
        System.out.println(sql); //Print out SQL command for debug purposes
        try(Connection conn = connect();Statement stmt = conn.createStatement()){//Connect to database and execute command
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a row to a table. All values are directly parsed by sql. Be sure all strings have single guotes around them.
     * Use "'Chris'" instead of "Chris" for Strings, numbers should be fine without modification.
     * @param tableName Name of table rows are added to
     * @param columns Name of columns to be added to
     * @param values Values added to each column
     */
    private void addToTable(String tableName, String[] columns, String[] values){
        String columnString = Arrays.toString(columns);
        String sql = "INSERT INTO " + tableName + "(" + columnString.substring(1,columnString.length()-1) + ") VALUES(";
        String valuesString = Arrays.toString(values);
        sql = sql + valuesString.substring(1,valuesString.length()-1) + ")";
        System.out.println(sql);
        try(Connection conn = connect();PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.executeUpdate();
            System.out.println("Wrote to database");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds a user to the database.
     * @param inputName Name of user being added
     * @return id of new user
     */
    public int addUser(String inputName){
        String[] column = {"name"};
        String inputNameS = "'" + inputName + "'";
        String[] nameList = {inputNameS};
        addToTable("users",column, nameList);
        return getUserID(inputName);
    }

    /**
     * Get user_id for user with a given name. Returns highest user_id associated with name.
     * @param inputName Name of user
     * @return user_id
     */
    public int getUserID(String inputName){
        String sql = "SELECT user_id, name FROM users";
        int user_id = 0;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            // Loop through results, TODO find a way to skip to the end
            while(rs.next()){
                String check = rs.getString("name"); //Find name and id for each entry
                int id = rs.getInt("user_id");
                if(check.equals(inputName)) { //Check if name matches searched name
                    user_id = id;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user_id;
    }

    /**
     * Adds and exercise to the database. Creates a new table for the exercise before trying to add to it.
     * @param exercise Exercise being added to database.
     */
    public void addExercise(Exercise exercise){
        createTable(exercise.getName(),exercise.initColumns());
        addToTable(exercise.getName(),exercise.getColumns(),exercise.getValues());
    }

    /**
     * This is old, needs to be updated to delete any row
     * TODO: make this work again
     */
    public void deleteUser(String input_name){
        String sql = "DELETE FROM users WHERE name = ?";
        try(Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, input_name);
            pstmt.executeUpdate();
            System.out.println("Removed user");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * TODO: have this check for a profile, and return the id
     */
    boolean loadUser(String input_name){
        //Loads a profile from a file
        return false;
    }
}
