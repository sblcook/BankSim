package edu.temple.cis.c3238.banksim;

public class TestingThread extends Thread {

    private final Bank bank;
    private TransferThread[] threads;
    private final Account[] accounts;
    private final int initialBalance;
    private final int numAccounts;

    public TestingThread(Bank bank, Account[] accounts, TransferThread[] threads, int initialBalance, int numAccounts){
        this.bank = bank;
        this.accounts = accounts;
        this.threads = threads;
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
    }

    @Override
    public synchronized void run() {

        //signal to all threads

        for (int i = 0; i < threads.length; i++){
            threads[i].tellToPause();
        }

        int sum = 0;
        for (Account account : accounts) {
            System.out.printf("%s %s%n",
                    Thread.currentThread().toString(), account.toString());
            sum += account.getBalance();
        }
        System.out.println(Thread.currentThread().toString() +
                " Sum: " + sum);
        if (sum != numAccounts * initialBalance) {
            System.out.println(Thread.currentThread().toString() +
                    " Money was gained or lost");
            System.exit(1);
        } else {
            System.out.println(Thread.currentThread().toString() +
                    " The bank is in balance");
        }

        for (int i = 0; i < threads.length; i++){
            threads[i].tellToResume();
        }
    }
}
