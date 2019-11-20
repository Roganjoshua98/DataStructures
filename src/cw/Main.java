package cw;

import java.util.Scanner;
import java.lang.*;

/**
 * @author Joshua Reyes-Traverso (17065838)
 */

public class Main {
    public static void main(String args[]) {
        final int range = 4;
        DelegateHash hashTable = new DelegateHash(range);
        Delegate delegate;
        Scanner input = new Scanner(System.in);
        boolean x = true;
        String name;
        String affiliation;


        while (x) {
            System.out.println("(D)isplay, (P)ut, (G)et, (C)ontains, (S)ize, (R)emove, (Q)uit?");
            char command = input.next().charAt(0);
            command = Character.toUpperCase(command);
            switch(command) {
                case 'D':       //Completed
                    hashTable.displayDB();
                    break;
                case 'P':       //Completed
                    hashTable = hashTable.checkResize();
                    System.out.println("Enter name of new delegate: ");
                    name = input.next();
                    System.out.println("Enter affiliation of new delegate: ");
                    input.nextLine();
                    affiliation = input.nextLine();
                    delegate = new Delegate(name, affiliation);
                    hashTable.put(delegate);
                    break;
                case 'G':   //Completed
                    System.out.println("Enter delegate name to get: ");
                    name = input.next();
                    delegate = hashTable.get(name);
                    if (delegate == null)
                        System.out.println("The delegate you tried to get does not exist");
                    else
                        System.out.println(delegate.toString());
                    break;
                case 'C':   //Completed
                    System.out.println("Enter delegate name to find: ");
                    name = input.next();
                    if (hashTable.containsName(name))
                        System.out.println("The hashtable contains this person");
                    else
                        System.out.println("The hashtable does not contain this person");
                    break;
                case 'S':   //Completed
                    System.out.println("There are " + hashTable.size() + " delegates registered");
                    break;
                case 'R':
                    System.out.println("Enter delegate name to remove: ");
                    name = input.next();
                    hashTable.remove(name);
                    System.out.println(name + " removed");
                    break;
                case 'Q':   //Completed
                    System.out.println("Closing program");
                    x = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}
