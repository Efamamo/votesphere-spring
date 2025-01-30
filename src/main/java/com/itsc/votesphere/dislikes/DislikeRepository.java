package com.itsc.votesphere.dislikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.itsc.votesphere.polls.Poll;
import com.itsc.votesphere.users.User;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, Long>{
        Dislike findByPollAndUser(Poll poll, User user);
;
}
