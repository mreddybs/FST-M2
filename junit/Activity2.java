package activities;

import examples.BankAccount;
import examples.NotEnoughFundsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {

    @Test
    public void notEnoughFunds(){
        BankAccount account = new BankAccount(9);
        assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10));

    }

    @Test
    public void enoughFunds(){
        BankAccount account = new BankAccount(100);
        assertDoesNotThrow(() -> account.withdraw(100));
    }
}