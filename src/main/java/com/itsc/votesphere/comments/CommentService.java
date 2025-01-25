package com.itsc.votesphere.comments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.polls.PollsService;
import com.itsc.votesphere.users.User;
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PollsService pollsService;

    public Object getComments(Long pollId){
        Poll poll = pollsService.findPollById(pollId);
        return commentRepository.findByPoll(poll);
    }

    public Comment getCommentById(Long id){
        return commentRepository.findById(id).orElse(null);
    }

    public Comment addComment(String content, Poll poll, User user){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPoll(poll);
        comment.setUser(user);

        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }

    
}
