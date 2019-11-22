package cw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Joshua Reyes-Traverso (17065838)
 */

public class DelegateHash implements IDelegateDB {
    private Delegate[] delegates;
    private final int range;
    private int numDelegates;
    private double loadFactor;

    public DelegateHash(int range) {
        this.range = range;
        this.loadFactor = 0;
        this.numDelegates = 0;
        delegates = new Delegate[this.range];
    }

    private int hash (String name) {    //Complete
        assert name != null && !name.equals("");
        name = name.toUpperCase();

        int key = 0;
        for (int i = 0; i < name.length(); i++) {
            if (i % 5 == 0)
                key += (int)Math.pow(i, name.charAt(i));
            else if (i % 2 == 0)
                key += i * (int) name.charAt(i);
            else
                key -= i * (int) name.charAt(i);
        }
        key *= name.length();
        key = Math.abs(key);
        key = key % this.range;

        /*/PURELY FOR TESTING
        char c = name.charAt(0);
        int key = c-5;
        key = key % this.range;*/

        return key;
    }

    public DelegateHash checkResize() {
        this.loadFactor = (double)this.numDelegates / (double)this.range;
        if (loadFactor > 0.5)
            return resize();
        else
            return this;
    }

    private DelegateHash resize() {
        System.out.println("~~STARTLOG~~");
        System.out.println("Load factor exceeded 0.5, resizing!");
        System.out.println("Old range: " + this.range);
        int newRange = this.range * 2;
        DelegateHash newHashTable = new DelegateHash(newRange);
        for (Delegate d : this.delegates) {
            if (d != null && !d.getIsDeleted())
                newHashTable.put(d);
        }
        System.out.println("New range: " + newRange);
        System.out.println("~~ENDLOG~~");
        return newHashTable;
    }

    ////////////////////////////////////////////
    //cw.IDelegateDB methods
    @Override
    public void clearDB() { //Complete
        for (Delegate d : this.delegates)
            if (d != null)
                remove(d.getName());
        numDelegates = 0;
    }

    @Override
    public boolean containsName(String name) {
        assert name != null && !name.equals("");    //@pre name not null or empty string

        if (get(name) == null)
            return false;
        else
            return (get(name).getName().equals(name));
    }

    @Override
    public Delegate get(String name) {  //Complete
        assert name != null && !name.equals("");    //@pre name not null or empty string

        int key = hash(name);
        System.out.println("~~STARTLOG~~");
        System.out.println("Getting delegate " + name);
        System.out.println("Hash value " + key);
        int probeStart = key;
        int i = 1;
        if (delegates[key] == null)
            return null;
        Delegate delegate = delegates[key];
        while (!delegates[key].getName().equals(name)) {  //Cycles one by one through the list to find an empty spot
            System.out.println("Visited index " + key);
            key = (int)((probeStart+Math.pow(i, 2)) % range);
            i++;
            delegate = delegates[key];
        }
        if (delegates[key].getIsDeleted()) {    //If space is marked to be deleted, abort
            System.out.println("Index " + key + " marked for deletion");
            System.out.println("~~ENDLOG~~");
            return null;
        }
        else {
            System.out.println("Retrieved at index " + key);
            System.out.println("~~ENDLOG~~");
            return delegate;
        }
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
        assert numDelegates != this.range;  //@pre array not full
        assert delegate != null;    //@pre delegate not null
        String name = delegate.getName();
        assert name != null && !name.equals("");    //@pre name not null or empty string

        int key = hash(name);
        System.out.println("~~STARTLOG~~");
        System.out.println("Putting delegate " + name);
        System.out.println("Hash value " + key);
        //Quadratic Probing
        int probeStart = key;
        int i = 1;
        Delegate previous = null;
        while (delegates[key] != null) {  //Cycles one by one through the list to find an empty spot
            System.out.println("Visited index " + key);
            if (delegates[key].getName().equals(name)) {    //If duplicate found, replace
                this.numDelegates--; //Counterbalance numdelegates++ later on
                previous = delegates[key];
                break;
            }
            if (delegates[key].getIsDeleted()) {    //If space is marked as to be deleted, replace (but numDelegates isn't touched)
                previous = delegates[key];
                break;
            }
            key = (int)(probeStart + Math.pow(i,2)) % this.range;
            i++;
        }
        delegates[key] = delegate;
        delegate.setKey(key);
        this.numDelegates++;
        System.out.println("Placed at index " + key);
        System.out.println("~~ENDLOG~~");
        return previous;
    }

    @Override
    public Delegate remove(String name) {
        assert name != null && !name.equals("");    //@pre name not null or empty string

        System.out.println("~~STARTLOG~~");
        System.out.println("Removing delegate " + name);
        Delegate delegate = get(name);
        delegate.setIsDeleted();
        System.out.println(name + " deleted");
        System.out.println("~~ENDLOG~~");
        numDelegates--;
        return delegate;
    }

    @Override
    public void displayDB() {   //Doesn't sort alphabetically, will add later
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
