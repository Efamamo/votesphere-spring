package com.itsc.votesphere.likes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsc.votesphere.dislikes.Dislike;
import com.itsc.votesphere.dislikes.DislikeRepository;
import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.users.User;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private DislikeRepository dislikeRepository;

    public boolean like(Poll poll, User user){

        Dislike dislike = dislikeRepository.findByPollAndUser(poll, user);

        if (dislike != null){
            dislikeRepository.delete(dislike);
        }
        
        Like like = new Like();
        like.setPoll(poll);
        like.setUser(user);
        likeRepository.save(like);
        return true;
    }

    public boolean unlike(Poll poll, User user){
        Like like = likeRepository.findByPollAndUser(poll, user);
        if (like == null){
            return false;
        }


        likeRepository.delete(like);
        return true;
    }
    
}
