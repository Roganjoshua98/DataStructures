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
        delegates = new Delegate[this.range-1];
    }

    private int hash (String name) {    //Complete
        name = name.toUpperCase();
        assert name != null && !name.equals("");

        int key = 0;
        for (int i = 0; i < name.length(); i++) {
            if (i % 5 == 0)
                key += (int)Math.pow((double)i, (double)name.charAt(i));
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
        int newRange = this.range * 2;
        DelegateHash newHashTable = new DelegateHash(newRange);
        for (Delegate d : this.delegates) {
            if (d != null && !d.getIsDeleted())
                newHashTable.put(d);
        }
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
        if (get(name) == null)
            return false;
        else
            return (get(name).getName().equals(name));
    }

    @Override
    public Delegate get(String name) {  //Complete
        int key = hash(name);
        int probeStart = key;
        int i = 1;
        if (delegates[key] == null)
            return null;
        Delegate delegate = delegates[key];
        while (!delegates[key].getName().equals(name)) {  //Cycles one by one through the list to find an empty spot
            if (delegates[key].getIsDeleted()) {    //If space is marked to be deleted, abort
                delegate = null;
                break;
            }
            key = (int)((probeStart+Math.pow(i, 2)) % range);
            i++;
            delegate = delegates[key];
        }
        return delegate;
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
        //Quadratic Probing
        int probeStart = key;
        int i = 1;
        Delegate previous = null;
        while (delegates[key] != null) {  //Cycles one by one through the list to find an empty spot
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
        return previous;
    }

    @Override
    public Delegate remove(String name) {
        Delegate delegate = get(name);
        delegate.setIsDeleted();
        numDelegates--;
        return delegate;
    }

    @Override
    public void displayDB() {   //Doesn't sort alphabetically, otherwise complete
        if (this.numDelegates == 0) {
            System.out.println("Database is currently empty");
            return;
        }
        ArrayList<String> disaplyList = new ArrayList<>();
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
