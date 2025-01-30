package com.itsc.votesphere.likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.users.User;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{
    Like findByPollAndUser(Poll poll, User user);

}
