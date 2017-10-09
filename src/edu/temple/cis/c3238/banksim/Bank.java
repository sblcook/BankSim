package edu.temple.cis.c3238.banksim;

import java.util.concurrent.Semaphore;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Project by Sam Cook
 */
public class Bank {

    public static final int NTEST = 10;
    private final Account[] accounts;
    private long ntransacts = 0;
    private final int initialBalance;
    private final int numAccounts;
    private boolean open;
    Semaphore semaphore;
    private static int countTransactions = 0;

    public Bank(int numAccounts, int initialBalance) {
        open = true;
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new Account[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(this, i, initialBalance);
        }
        ntransacts = 0;
        semaphore = new Semaphore(numAccounts);
    }

    public synchronized void incCountTransactions(){
        countTransactions++;
    }

    public void transfer(int from, int to, int amount) {
        incCountTransactions();
        System.out.println("count transactions: " + countTransactions);
        accounts[from].waitForAvailableFunds(amount);
        if (!open) return;
        try {
            semaphore.acquire();
            if (accounts[from].withdraw(amount)) {
                accounts[to].deposit(amount);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
        if (shouldTest()) test();
    }

    public synchronized void test() {

        Thread testingThread = new TestingThread(this, accounts, initialBalance, numAccounts);
        testingThread.start();

    }

    public int size() {
        return accounts.length;
    }
    
    public synchronized boolean isOpen() {return open;}
    
    public void closeBank() {
        synchronized (this) {
            open = false;
        }
        for (Account account : accounts) {
            synchronized(account) {
                account.notifyAll();
            }
        }
    }
    
    public synchronized boolean shouldTest() {
        return ++ntransacts % NTEST == 0;
    }
}
