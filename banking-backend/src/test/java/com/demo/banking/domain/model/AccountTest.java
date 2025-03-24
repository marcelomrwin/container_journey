package com.demo.banking.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AccountTest {

    private Account account;
    private final LocalDateTime testTime = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        account = new Account(1L, "123456", "John Doe", new BigDecimal("1000.00"), testTime, testTime);
    }

    @Test
    void depositPositiveAmount() {
        BigDecimal depositAmount = new BigDecimal("500.00");
        account.deposit(depositAmount);
        assertEquals(new BigDecimal("1500.00"), account.getBalance());
        assertTrue(account.getUpdatedAt().isAfter(testTime));
    }

    @Test
    void depositZeroAmount() {
        BigDecimal depositAmount = BigDecimal.ZERO;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.deposit(depositAmount));
        assertEquals("Deposit amount must be positive", exception.getMessage());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }

    @Test
    void depositNegativeAmount() {
        BigDecimal depositAmount = new BigDecimal("-100.00");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.deposit(depositAmount));
        assertEquals("Deposit amount must be positive", exception.getMessage());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }

    @Test
    void withdrawPositiveAmount() {
        BigDecimal withdrawAmount = new BigDecimal("300.00");
        account.withdraw(withdrawAmount);
        assertEquals(new BigDecimal("700.00"), account.getBalance());
        assertTrue(account.getUpdatedAt().isAfter(testTime));
    }

    @Test
    void withdrawZeroAmount() {
        BigDecimal withdrawAmount = BigDecimal.ZERO;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(withdrawAmount));
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }

    @Test
    void withdrawNegativeAmount() {
        BigDecimal withdrawAmount = new BigDecimal("-100.00");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(withdrawAmount));
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }

    @Test
    void withdrawInsufficientFunds() {
        BigDecimal withdrawAmount = new BigDecimal("1500.00");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> account.withdraw(withdrawAmount));
        assertEquals("Insufficient funds", exception.getMessage());
        assertEquals(new BigDecimal("1000.00"), account.getBalance());
    }
}