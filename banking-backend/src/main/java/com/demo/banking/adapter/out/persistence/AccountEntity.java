package com.demo.banking.adapter.out.persistence;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "accounts")
public class AccountEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="ACCOUNT_GEN")
    @SequenceGenerator(name = "ACCOUNT_GEN", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    public Long id;

    @Column(unique = true, nullable = false)
    public String accountNumber;

    @Column(nullable = false)
    public String ownerName;

    @Column(nullable = false)
    public BigDecimal balance;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;
}