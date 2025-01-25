package com.itsc.votesphere.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itsc.votesphere.polls.Poll;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Poll findByPoll(Poll poll);
}

