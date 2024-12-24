package com.itsc.votesphere.polls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PollsService {
    @Autowired
    private PollRepository pollRepository;

    public Poll addPoll(Poll poll){
        Poll p = pollRepository.save(poll);
        return p;
    }
    
}
