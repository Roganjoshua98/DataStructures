package cw;

/**
 * @author Joshua Reyes-Traverso (17065838)
 */

public class Delegate implements IDelegate {
    private String name;
    private String affiliation;
    private int key;

    public Delegate(String name, String affiliation) {
        this.name = name;
        this.affiliation = affiliation;
    }

    ////////////////////////////////////////////
    //cw.IDelegate methods
    @Override
    public String getName() { return this.name; }

    @Override
    public String getAffiliation() {
        return this.affiliation;
    }

    public int getKey() { return this.key; }
    public void setket(int key) { this.key = key; }

    @Override
    public String toString() {
        return "Name: " + this.name + "\nAffiliattion: " + this.affiliation;
    }

}
