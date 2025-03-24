package com.demo.banking.application.port.in;

import com.demo.banking.domain.model.Account;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountUseCase {
    Account createAccount(Account account);
    Optional<Account> getAccountById(Long id);
    Optional<Account> getAccountByNumber(String accountNumber);
    List<Account> getAllAccounts();
    Account updateAccount(Account account);
    void deleteAccount(Long id);
    Account deposit(String accountNumber, BigDecimal amount);
    Account withdraw(String accountNumber, BigDecimal amount);
}