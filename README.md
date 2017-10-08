# BankSim
Bank Simulation Lab for CIS 3238

An exercising in multithreading.

Problems existed because one thread was summing up the account balances, and all the other threads were still transferring.

Now, to test(sum up account balances), there is a seperate thread that executes. This testing thread signals to all other threads that
they should pause, and then waits for them to finish their current transaction. It runs its tests, and then tells the all threads
that they may resume.

In the future, I would probably like to use something more advanced than the method I am currently using.
