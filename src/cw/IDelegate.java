package cw;

/**
 *
 * @author D Lightfoot 2019-06
 * DO NOT CHANGE THIS INTERFACE
 * Student must implement this interface
 *
 * objects of classes implementing this interface hold information about a delegate
 */

public interface IDelegate {

    /**
     * Retrieves the name of the delegate
     * @pre true
     * @return the name of the delegate
     */
    public String getName();

    /**
     * Retrieves the delegate's affiliation
     * @pre true
     * @return the delegate's affiliation
     */
    public String getAffiliation();

    @Override
    public String toString();
}