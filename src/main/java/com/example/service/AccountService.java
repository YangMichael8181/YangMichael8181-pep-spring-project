package com.example.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.example.entity.Account;
import com.example.exception.ClientErrorException;
import com.example.exception.ConflictException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    AccountRepository accRepo;

    @Autowired
    public AccountService(AccountRepository _accRepo)
    {
        accRepo = _accRepo;
    }

    public Account registerUser(Account jsonBody) throws ConflictException, ClientErrorException
    {
        if (jsonBody.getUsername().length() == 0) throw new ClientErrorException("No username provided");
        if (jsonBody.getPassword().length() < 4) throw new ClientErrorException("Password must be at least 4 characters long");
        if (accRepo.findByUsername(jsonBody.getUsername()).isPresent()) throw new ConflictException("Username already exists");
        
        return accRepo.save(jsonBody);
    }
    public Account loginUser(Account jsonBody) throws UnauthorizedException
    {
        Optional<Account> returned_acc = accRepo.findByUsernameAndPassword(jsonBody.getUsername(), jsonBody.getPassword());
        if (returned_acc.isEmpty()) throw new UnauthorizedException("Incorrect username or password");
        return returned_acc.get();
    }
}
