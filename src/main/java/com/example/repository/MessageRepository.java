package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAll();

    Optional<Message> findByMessageId(int messageId);

    @Transactional
    int deleteByMessageId(int messageId);

    @Transactional
    @Modifying
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    int updateMessageTextByMessageId(@Param("messageId") int messageId, @Param("messageText") String messageText);

    List<Message> findAllByPostedBy(int postedBy);
}
