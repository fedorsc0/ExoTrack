package exotrack.database.exercises;

/**
 * Abstract class that all exerises use.
 * All functions are wrappers to for the String variables that help initialize and add to tables
 * TODO: Add function that automatically parse sql to String or variables to sql
 */
public abstract class Exercise {
    abstract public String getName(); //Name of the exercises
    abstract public String[] initColumns(); //Initialize columns of table
    abstract public String[] getColumns(); //Name of each column
    abstract public String[] getValues(); //Values for each column
}
