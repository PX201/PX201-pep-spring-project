package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messagesRepo;

    @Autowired
    private AccountService accService;

    public Message addMessage(Message message){
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255 || !accService.isExist(message.getPostedBy()))
            return null;
        return messagesRepo.save(message);
    }

    public List<Message> getAllMessages(){
        return messagesRepo.findAll();
    }

    public Message getMessage(int messageId){
        return messagesRepo.findByMessageId(messageId).orElse(null);
    }

    public int deleteMessage(int messageId){
        return messagesRepo.deleteByMessageId(messageId);
    }

    public int updateMessageText(int messageId, String messageText){
        boolean isMessageExist = messagesRepo.findByMessageId(messageId).isPresent();
        if(!isMessageExist || messageText.isBlank() || messageText.length() > 255)
            return 0;

        return messagesRepo.updateMessageTextByMessageId(messageId, messageText);
    }

    public List<Message> getAllAccountMessages(int accountId){
        return messagesRepo.findAllByPostedBy( accountId);
    }
}
