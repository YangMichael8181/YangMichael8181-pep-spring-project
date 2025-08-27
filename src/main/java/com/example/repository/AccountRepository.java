// Repository layer for Accounts
// manages CRUD operations for Account database

// Account table:
// accountId integer primary key auto_increment,
// username varchar(255) not null unique,
// password varchar(255)

package com.example.repository;

import java.util.Optional;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>{

    public Optional<Account> findByUsername(String username);
    public Optional<Account> findByUsernameAndPassword(String username, String password);

}
