package com.example.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
//@RequestMapping("api/v1/")
public class SocialMediaController {

    @Autowired
    private AccountService accService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account){
        if(account.getUsername() != null && accService.isExist(account.getUsername()))
            return ResponseEntity.status(409).build();
        
        Account registeredAccount = accService.addAccount(account);
        if(registeredAccount != null)
            return   ResponseEntity.ok(registeredAccount);
        else
            return ResponseEntity.status(400).build();
        
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account retrievedAcc = accService.findAccount(account);
        if(retrievedAcc != null)
            return ResponseEntity.ok(retrievedAcc);
        return  ResponseEntity.status(401).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createAMesssage(@RequestBody Message message){
    
        Message createdMessage = messageService.addMessage(message);
        if(createdMessage != null)
            return ResponseEntity.ok(createdMessage);
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMesssage(@PathVariable("messageId") int messageId){
        Message message = messageService.getMessage(messageId);
        if(message != null)
            return ResponseEntity.ok(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<String> deleteMesssage(@PathVariable("messageId") int messageId){
       
        int rowsEffected = messageService.deleteMessage(messageId);
        if(rowsEffected > 0)
            return ResponseEntity.ok().body( ""+rowsEffected);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<String> updateMesssage(@RequestBody Message message, @PathVariable("messageId") int messageId){
        
        int rowsEffected = messageService.updateMessageText(messageId, message.getMessageText());

        if(rowsEffected >  0)
            return ResponseEntity.ok().body(""+ rowsEffected);
        return ResponseEntity.status(400).build();
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> messages(){
       
        return ResponseEntity.ok(messageService.getAllMessages());
    }
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllAccountMessages(@PathVariable("accountId") int accountId){
        return ResponseEntity.ok(messageService.getAllAccountMessages(accountId));
    }
}
