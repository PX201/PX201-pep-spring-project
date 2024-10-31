package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accRepo;

    public Account addAccount(Account account){
        if(account == null || account.getUsername().isEmpty() || account.getPassword().length() < 4)
            return null;
        return accRepo.save(account);
    }


    public Account findAccount(Account account){
        if(account.getUsername() == null)
            return null;

        Account userAccount;
        Optional<Account> retrievedAcc = accRepo.findByUsername(account.getUsername());
        if(retrievedAcc.isPresent()){
            userAccount = retrievedAcc.get();
            if(userAccount.getPassword().equals(account.getPassword()))
                return userAccount;

        }

        return null;
    }

    public boolean isExist(String username){
        return accRepo.findByUsername(username).isPresent();
    }
    public boolean isExist(int accountId){
        return accRepo.findByAccountId(accountId).isPresent();
    }
}
