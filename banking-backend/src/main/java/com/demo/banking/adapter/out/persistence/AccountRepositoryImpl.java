package com.demo.banking.adapter.out.persistence;

import com.demo.banking.application.port.out.AccountRepository;
import com.demo.banking.domain.model.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class AccountRepositoryImpl implements AccountRepository {

    @Override
    @Transactional
    public Account save(Account account) {
        AccountEntity entity;

        if (account.getId() != null) {
            entity = AccountEntity.findById(account.getId());
            if (entity == null) {
                entity = new AccountEntity();
            }
        } else {
            entity = new AccountEntity();
        }

        entity.accountNumber = account.getAccountNumber();
        entity.ownerName = account.getOwnerName();
        entity.balance = account.getBalance();
        entity.createdAt = account.getCreatedAt();
        entity.updatedAt = account.getUpdatedAt();

        entity.persist();

        account.setId(entity.id);
        return account;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(AccountEntity.findById(id))
                .map(this::mapToDomain);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return AccountEntity.find("accountNumber", accountNumber)
                .firstResultOptional()
                .map(this::mapToDomain);
    }

    @Override
    public List<Account> findAll() {
        return AccountEntity.listAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        AccountEntity.deleteById(id);
    }

    private Account mapToDomain(Object entityObj) {
        AccountEntity entity = (AccountEntity) entityObj;
        return new Account(
                entity.id,
                entity.accountNumber,
                entity.ownerName,
                entity.balance,
                entity.createdAt,
                entity.updatedAt
        );
    }
}