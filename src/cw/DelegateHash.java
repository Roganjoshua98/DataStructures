package cw;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Joshua Reyes-Traverso (17065838)
 */

public class DelegateHash implements IDelegateDB {
    private Delegate[] delegates;
    private static int range;
    private int numDelegates;

    public DelegateHash(int range) {
        this.range = range;
        delegates = new Delegate[this.range];
        clearDB();
    }

    int hash (String name) {    //Complete
        name = name.toUpperCase();
        assert name != null && !name.equals("");
        /*
        int key = 0;
        for (int i = 0; i < name.length(); i++) {
            if (i % 5 == 0)
                key += (int)Math.pow((double)i, (double)name.charAt(i));
            if (i % 2 == 0)
                key += i * (int) name.charAt(i);
            else
                key -= i * (int) name.charAt(i);
        }
        key *= name.length();
        key = Math.abs(key);
        key = key % this.range;*/

        //PURELY FOR TESTING
        char c = name.charAt(0);
        int key = c-5;
        key = key % this.range;
        return key;
    }
    ////////////////////////////////////////////
    //cw.IDelegateDB methods
    @Override
    public void clearDB() { //Complete
        numDelegates = 0;
    }

    @Override
    public boolean containsName(String name) {
        return (get(name).getName().equals(name));
    }

    @Override
    public Delegate get(String name) {  //Complete
        int key = hash(name);
        return delegates[key];
    }

    @Override
    public int size() {
        return this.numDelegates;
    }

    @Override
    public boolean isEmpty() {
        return this.numDelegates == 0;
    }

    @Override
    public Delegate put(Delegate delegate) {
        assert numDelegates != this.range;
        assert delegate != null;
        String name = delegate.getName();
        assert name != null && !name.contains("");

        int key = hash(name);
        //Linear probing
        Delegate previous = null;
        while (delegates[key] != null) {  //Cycles one by one through the list to find an empty spot
            if (delegates[key].getName().equals(name)) {    //If duplicate found, replace
                previous = delegates[key];
                this.numDelegates--; //Counterbalance numdelegates++ later on
                break;
            }
            key++;
        }
        delegates[key] = delegate;
        delegate.setket(key);
        this.numDelegates++;
        return previous;
    }

    @Override
    public Delegate remove(String name) {
        System.out.println("Remove");
        return null;
    }

    @Override
    public void displayDB() {   //Doesn't sort alphabetically, otherwise complete
        if (this.numDelegates == 0) {
            System.out.println("Database is currently empty");
            return;
        }
        for (Delegate delegate : this.delegates) {
            if (delegate != null) {
                System.out.println(delegate.toString());
                System.out.println("Hash Key: " + delegate.getKey());
                System.out.println("------------------------");
            }
        }
        System.out.println("Total number of delegates: " + this.numDelegates);
    }

}
