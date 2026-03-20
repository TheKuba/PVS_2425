package threads.synced;

public class Banking {
    public static void main(String[] args) {
        BankAccountUnsafe unsafeA = new BankAccountUnsafe(1000);
        BankAccountUnsafe unsafeB = new BankAccountUnsafe(1000);
    }
}

class BankAccountUnsafe{
    private int balance;
    BankAccountUnsafe(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {

    }
}

class BankAccountSafe{}
