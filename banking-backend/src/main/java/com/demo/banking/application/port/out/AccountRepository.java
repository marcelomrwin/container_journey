package com.demo.banking.application.port.out;

import com.demo.banking.domain.model.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findById(Long id);
    Optional<Account> findByAccountNumber(String accountNumber);
    List<Account> findAll();
    void delete(Long id);
}