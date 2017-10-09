package edu.temple.cis.c3238.banksim;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Project by Sam Cook
 *
 * This project fixes the old BankSim where concurrently testing the balance of accounts and transferring would
 * yield different balances
 */
public class BankSimMain {

    public static final int NACCOUNTS = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) {
        Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        TransferThread[] threads = new TransferThread[NACCOUNTS];

        // Start a thread for each account
        for (int i = 0; i < NACCOUNTS; i++) {
            threads[i] = new TransferThread(b, i, INITIAL_BALANCE);
        }
        for(int i = 0; i < NACCOUNTS; i++){
            threads[i].start();
        }
    }
}


