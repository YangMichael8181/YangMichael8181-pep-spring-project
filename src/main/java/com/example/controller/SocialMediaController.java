// Controller layer; handles all endpoints
// will work with service layer to handle user requests

package com.example.controller;

// Java imports
import java.util.List;
import java.util.ArrayList;

// Local imports
import com.example.entity.*;
import com.example.exception.ClientErrorException;
import com.example.exception.ConflictException;
import com.example.exception.UnauthorizedException;
import com.example.service.*;

// Spring Imports
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
public class SocialMediaController {

    AccountService accService;
    MessageService msgService;

    @Autowired
    public SocialMediaController(AccountService _accService, MessageService _msgService)
    {
        accService = _accService;
        msgService = _msgService;
    }

    // Account Endpoints
    // user registration
    @PostMapping(value = "/register")
    public ResponseEntity<Account> userRegistration(@RequestBody Account jsonBody) throws ConflictException, ClientErrorException
    {
        return ResponseEntity.status(200).body(accService.registerUser(jsonBody));
    }

    // user login
    @PostMapping(value = "/login")
    public ResponseEntity<Account> userLogin(@RequestBody Account jsonBody) throws UnauthorizedException
    {
        return ResponseEntity.status(200).body(accService.loginUser(jsonBody));
    }

    // Message Endpoints
    // create new message
    @PostMapping(value = "/messages")
    public ResponseEntity<Message> messageCreation(@RequestBody Message jsonBody) throws ClientErrorException
    {
        return ResponseEntity.status(200).body(msgService.messageCreation(jsonBody));
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<List<Message>> messageRetrieveAll()
    {
        return ResponseEntity.status(200).body(msgService.messageRetrieveAll());
    }

    @GetMapping(value = "/messages/{message_id}")
    public ResponseEntity<Message> messageRetrieveById(@PathVariable Integer message_id)
    {
        return ResponseEntity.status(200).body(msgService.messageRetrieveById(message_id));
    }

    @DeleteMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> messageDeleteById(@PathVariable Integer message_id)
    {
        return ResponseEntity.status(200).body(msgService.messageDeleteById(message_id));
    }

    @PatchMapping(value = "/messages/{message_id}")
    public ResponseEntity<Integer> messageUpdateById(@PathVariable Integer message_id, @RequestBody Message jsonBody) throws ClientErrorException
    {
        return ResponseEntity.status(200).body(msgService.messageUpdateById(message_id, jsonBody));
    }

    @GetMapping(value = "/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> messageRetrieveAllByUserId(@PathVariable Integer account_id)
    {
        return ResponseEntity.status(200).body(msgService.messageRetrieveAllByUserId(account_id));
    }






    // Exception Handling
    // Handles 400 errors
    @ExceptionHandler(ClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleClientErrorException(ClientErrorException ex)
    {
        return ex.getMessage();
    }

    // Handles 401 errors
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedException(UnauthorizedException ex)
    {
        return ex.getMessage();
    }

    // Handles 409 errors
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConflictException(ConflictException ex)
    {
        return ex.getMessage();
    }


}
