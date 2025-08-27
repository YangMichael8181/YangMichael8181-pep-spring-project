// Repository layer for Messages
// manages CRUD operations for Message database

// Message Table:
// messageId integer primary key auto_increment,
// postedBy integer,
// messageText varchar(255),
// timePostedEpoch long,
// foreign key (postedBy) references Account(accountId)


package com.example.repository;

import java.util.List;

import com.example.entity.Message;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    public List<Message> findAllByPostedBy(Integer account_id);
}
