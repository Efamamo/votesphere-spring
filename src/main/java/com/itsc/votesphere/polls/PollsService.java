package com.itsc.votesphere.polls;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsc.votesphere.group.Group;
import com.itsc.votesphere.polls.dto.CreatePollDto;
import com.itsc.votesphere.users.User;

@Service
public class PollsService {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private ChoiceRepository choiceRepository;


    public Poll addPoll(CreatePollDto p, Group group , User pollOwner, List<String> choices){

        Poll poll = new Poll();
        poll.setGroup(group);
        poll.setPollOwner(pollOwner);
        poll.setQuestion(p.getQuestion());

        Poll savedPoll = pollRepository.save(poll);

        List<Choice> choiceEntities = choices.stream()
        .map(choiceContent -> {
            if (!choiceContent.trim().isEmpty()) {
                Choice choice = new Choice();
                choice.setContent(choiceContent.trim());
                choice.setPoll(savedPoll); 
                return choice;
            }
            return null; 
        })
        .filter(Objects::nonNull) 
        .collect(Collectors.toList());


        choiceRepository.saveAll(choiceEntities);

        savedPoll.setChoices(choiceEntities);
        return savedPoll;
    }

    public Poll findPollById(Long id) {
        return pollRepository.findById(id).orElse(null);
    }
    
    
}
