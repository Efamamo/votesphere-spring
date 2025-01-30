package com.itsc.votesphere.dislikes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsc.votesphere.likes.Like;
import com.itsc.votesphere.likes.LikeRepository;
import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.users.User;

@Service
public class DislikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private DislikeRepository dislikeRepository;

    public boolean dislike(Poll poll, User user){

        Like like = likeRepository.findByPollAndUser(poll, user);

        if (like != null){
            likeRepository.delete(like);
        }
        
        Dislike dislike = new Dislike();
        dislike.setPoll(poll);
        dislike.setUser(user);
        dislikeRepository.save(dislike);
        return true;
    }

    public boolean undislike(Poll poll, User user){
        Dislike dislike = dislikeRepository.findByPollAndUser(poll, user);
        if (dislike == null){
            return false;
        }

        dislikeRepository.delete(dislike);
        return true;
    }
    
}
