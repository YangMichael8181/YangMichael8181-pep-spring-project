package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.*;
import com.example.exception.ClientErrorException;
import com.example.exception.ConflictException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    AccountRepository accRepo;
    MessageRepository msgRepo;

    @Autowired
    public MessageService(MessageRepository _msgRepo, AccountRepository _accRepo)
    {
        accRepo = _accRepo;
        msgRepo = _msgRepo;
    }

    public Message messageCreation(Message jsonBody) throws ClientErrorException
    {
        if (jsonBody.getMessageText().length() == 0) throw new ClientErrorException("No message provided");
        if (jsonBody.getMessageText().length() >= 255) throw new ClientErrorException("Message too long, must be under 255 characters");
        if (!accRepo.existsById(jsonBody.getPostedBy())) throw new ClientErrorException("Posting user does not exist");

        return msgRepo.save(jsonBody);
    }

    public List<Message> messageRetrieveAll()
    {
        return msgRepo.findAll();
    }

    public Message messageRetrieveById(Integer message_id)
    {
        return msgRepo.findById(message_id).orElse(null);
    }

    public Integer messageDeleteById(Integer message_id)
    {
        Integer count = msgRepo.existsById(message_id) ? 1 : null;
        if (count != null) msgRepo.deleteById(message_id);
        return count;
    }
    public Integer messageUpdateById(Integer message_id, Message jsonBody) throws ClientErrorException
    {
        if (jsonBody.getMessageText().length() == 0) throw new ClientErrorException("No message provided");
        if (jsonBody.getMessageText().length() >= 255) throw new ClientErrorException("Message too long, must be under 255 characters");
        if (msgRepo.findById(message_id).isEmpty()) throw new ClientErrorException("Message to be updated does not exist");
        return 1;
    }

    public List<Message> messageRetrieveAllByUserId(Integer account_id)
    {
        return msgRepo.findAllByPostedBy(account_id);
    }
}
