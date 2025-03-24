package com.demo.banking.domain.service;

import com.demo.banking.application.port.in.AccountUseCase;
import com.demo.banking.application.port.out.AccountRepository;
import com.demo.banking.domain.model.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AccountService implements AccountUseCase {

    private final AccountRepository accountRepository;

    @Inject
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        if (account.getBalance() == null) {
            account.setBalance(BigDecimal.ZERO);
        }
        return accountRepository.save(account);
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public Account updateAccount(Account account) {
        account.setUpdatedAt(LocalDateTime.now());
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.delete(id);
    }

    @Override
    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.withdraw(amount);
        return accountRepository.save(account);
    }
}