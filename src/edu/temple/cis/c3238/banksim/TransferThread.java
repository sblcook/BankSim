package edu.temple.cis.c3238.banksim;
/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 */
class TransferThread extends Thread {

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;
    private boolean running;
    private boolean finished;

    public TransferThread(Bank b, int from, int max) {
        bank = b;
        fromAccount = from;
        maxAmount = max;
        running = true;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            if(running) {
                finished = false;
                int toAccount = (int) (bank.size() * Math.random());
                int amount = (int) (maxAmount * Math.random());
                bank.transfer(fromAccount, toAccount, amount);
                finished = true;
            }
        }
        bank.closeBank();
    }

    public void tellToPause(){
        this.running = false;
    }
    public void tellToResume(){
        this.running = true;
    }
    public boolean isFinished(){
        return finished;
    }
}
