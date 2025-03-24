package com.demo.banking.domain.service;

import com.demo.banking.application.port.out.AccountRepository;
import com.demo.banking.domain.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testAccount = new Account(1L, "123456", "John Doe", new BigDecimal("1000.00"),
                LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void createAccountSuccessfully() {
        Account newAccount = new Account(null, "654321", "Jane Doe", null, null, null);
        when(accountRepository.save(any(Account.class))).thenAnswer(i -> {
            Account a = i.getArgument(0);
            a.setId(2L);
            return a;
        });

        Account created = accountService.createAccount(newAccount);

        assertNotNull(created.getId());
        assertEquals(BigDecimal.ZERO, created.getBalance());
        assertNotNull(created.getCreatedAt());
        assertNotNull(created.getUpdatedAt());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void getAccountByIdFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        Optional<Account> found = accountService.getAccountById(1L);

        assertTrue(found.isPresent());
        assertEquals(testAccount.getId(), found.get().getId());
    }

    @Test
    void getAccountByIdNotFound() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Account> found = accountService.getAccountById(99L);

        assertFalse(found.isPresent());
    }

    @Test
    void getAllAccounts() {
        Account anotherAccount = new Account(2L, "654321", "Jane Doe", new BigDecimal("2000.00"),
                LocalDateTime.now(), LocalDateTime.now());
        when(accountRepository.findAll()).thenReturn(Arrays.asList(testAccount, anotherAccount));

        List<Account> accounts = accountService.getAllAccounts();

        assertEquals(2, accounts.size());
        verify(accountRepository).findAll();
    }

    @Test
    void depositSuccessfully() {
        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        Account updated = accountService.deposit("123456", new BigDecimal("500.00"));

        assertEquals(new BigDecimal("1500.00"), updated.getBalance());
        verify(accountRepository).findByAccountNumber("123456");
        verify(accountRepository).save(testAccount);
    }

    @Test
    void depositAccountNotFound() {
        when(accountRepository.findByAccountNumber("999999")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> accountService.deposit("999999", new BigDecimal("500.00")));

        assertEquals("Account not found", exception.getMessage());
    }

    @Test
    void withdrawSuccessfully() {
        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

        Account updated = accountService.withdraw("123456", new BigDecimal("300.00"));

        assertEquals(new BigDecimal("700.00"), updated.getBalance());
        verify(accountRepository).findByAccountNumber("123456");
        verify(accountRepository).save(testAccount);
    }

    @Test
    void withdrawInsufficientFunds() {
        when(accountRepository.findByAccountNumber("123456")).thenReturn(Optional.of(testAccount));

        assertThrows(IllegalArgumentException.class,
                () -> accountService.withdraw("123456", new BigDecimal("2000.00")));

        verify(accountRepository, never()).save(any(Account.class));
    }
}