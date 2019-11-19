package cw;

/**
 *
 * @author D Lightfoot 2019-06
 * objects of classes implementing this interface hold a
 * database of delegate information

 * DO NOT CHANGE THIS INTERFACE
 * Student must implement this interface
 *
 */

public interface IDelegateDB {

    /**
     * Empties the database.
     * @pre true
     */
    public void clearDB();

    /**
     * Determines whether a delegate's name exists as a key inside the database
     * @pre name not null or empty string
     * @param name the cw.Delegate name (key) to locate
     * @return true iff the name exists as a key in the database
     */
    public boolean containsName(String name);

    /**
     * Returns a cw.Delegate object mapped to the supplied name.
     * @pre name not null or empty string
     * @param name The cw.Delegate name (key) to locate
     * @return the cw.Delegate object mapped to the key name if the name
     * exists as key in the database, otherwise null
     */
    public Delegate get(String name);

    /**
     * Returns the number of delegates in the database
     * @pre true
     * @return number of delegates in the database. 0 if empty
     */
    public int size();

    /**
     * Determines if the database is empty or not.
     * @pre true
     * @return true iff the database is empty
     */
    public boolean isEmpty();

    /**
     * Inserts a delegate object into the database, with the key of the supplied
     * delegate's name.
     * Note: If the name already exists as a key, then then the original entry
     * is overwritten. 
     * This method must return the previous associated value 
     * if one exists, otherwise null
     *
     * @pre delegate not null
     */
    public Delegate put(Delegate delegate);

    /**
     * Removes and returns a delegate from the database, with the key
     * the supplied name.
     * @param name The name (key) to remove.
     * @pre name not null or empty string
     * @return the removed delegate object mapped to the name, or null if
     * the name does not exist.
     */
    public Delegate remove(String name);

    /**
     * Prints the names and IDs of all the delegates in the database in 
     * alphabetic order.
     * @pre true
     */
    public void displayDB();
}