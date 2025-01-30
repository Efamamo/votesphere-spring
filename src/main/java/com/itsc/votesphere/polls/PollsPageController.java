package com.itsc.votesphere.polls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsc.votesphere.users.UserService;
import com.itsc.votesphere.dislikes.Dislike;
import com.itsc.votesphere.group.Group;
import com.itsc.votesphere.likes.Like;
import com.itsc.votesphere.users.User;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/polls")
public class PollsPageController {

     @Autowired
    private UserService userService ;

    @Autowired
    private PollsService pollsService;


    @GetMapping("")
    public String getPolls(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");
        String username = claims.getSubject();  

        User user = userService.findUserByUsername(username);

        if (user == null){
            response.sendRedirect("/auth/login");  
            return null;
        }

        if (!user.getIsVerified()){
            response.sendRedirect("/auth/otp");  
            return null;
        }

        Group userGroup = user.getMemberOf();

        if (userGroup == null){
            model.addAttribute("polls", new ArrayList<>());
            model.addAttribute("isAdmin", user.getIsAdmin());
            model.addAttribute("hasGroup", false);
            return "polls";
        }

        

        List<Map<String, Object>> pollsData = pollsService.findAll()
        .stream()
        .filter(poll -> poll.getGroup().equals(user.getMemberOf()))
        .map(poll -> {
            Map<String, Object> pollData = new HashMap<>();

            Boolean userLiked = false;
            Boolean userDisliked = false;
            for (Like like: poll.getLikes()){
                if(like.getUser().getId().equals(user.getId()) ){
                    userLiked = true;
                }
            }

            for (Dislike dislike: poll.getDislikes()){
                if(dislike.getUser().getId().equals(user.getId()) ){
                    userDisliked = true;
                }
            }
            System.out.println(userLiked);
            pollData.put("id", poll.getId());
            pollData.put("question", poll.getQuestion());
            pollData.put("comments", poll.getComments().size());
            pollData.put("userLiked", userLiked);
            pollData.put("userDisliked", userDisliked);
    
            // Calculate the total votes for the poll
            final int total = poll.getChoices().stream()
                .mapToInt(choice -> choice.getVotes().size())
                .sum();
           
    
            // Map the choices
            List<Map<String, String>> choiceContents = poll.getChoices().stream()
                .map(choice -> {
                     Choice userVote = null;

            


                    for (Vote vote: user.getVotes()){
                        if (vote.getPoll() == poll){
                            userVote = vote.getChoice();
                            break;
                        }
                    }


            
                    Map<String, String> choiceData = new HashMap<>();
                    choiceData.put("content", choice.getContent());
                    choiceData.put("checked", choice == userVote ? "yes": "no");


                    if (total == 0) {
                        choiceData.put("count", "0");
                    } else {
                        choiceData.put("count", String.valueOf((choice.getVotes().size()) / (double) total * 100));
                    }                     choiceData.put("id", choice.getId().toString());
                    return choiceData;
                })
                .collect(Collectors.toList());


                pollData.put("choices", choiceContents);
                pollData.put("total", total == 0 ? 1 : total);
                pollData.put("likes", poll.getLikes().size());
                pollData.put("dislikes", poll.getDislikes().size());
            return pollData;
        })
        .collect(Collectors.toList());




            model.addAttribute("polls", pollsData);
            model.addAttribute("isAdmin", user.getIsAdmin());
            model.addAttribute("hasGroup", true);



    return "polls";

    }


    @GetMapping("/{id}")
    public String getPollById(Model model, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");
        String username = claims.getSubject();  

        User user = userService.findUserByUsername(username);

        if (user == null){
            response.sendRedirect("/auth/login");  
            return null;
        }

        if (!user.getIsVerified()){
            response.sendRedirect("/auth/otp");  
            return null;
        }

        Group userGroup = user.getMemberOf();

        if (userGroup == null){
            response.sendRedirect("/polls");  
            return null;

        }


        Poll poll = pollsService.findPollById(id);

        if (poll == null){
            response.sendRedirect("/polls");  
            return null;
        }

        Map<String, Object> pollData = new HashMap<>();
            Boolean userLiked = false;
            Boolean userDisliked = false;
            for (Like like: poll.getLikes()){
                if(like.getUser().getId().equals(user.getId()) ){
                    userLiked = true;
                }
            }

            for (Dislike dislike: poll.getDislikes()){
                if(dislike.getUser().getId().equals(user.getId()) ){
                    userDisliked = true;
                }
            }

            pollData.put("id", poll.getId());
            pollData.put("question", poll.getQuestion());
            pollData.put("userLiked", userLiked);
            pollData.put("userDisliked", userDisliked);

            
    
            // Calculate the total votes for the poll
            final int total = poll.getChoices().stream()
                .mapToInt(choice -> choice.getVotes().size())
                .sum();
           
    
            // Map the choices
            List<Map<String, String>> choiceContents = poll.getChoices().stream()
                .map(choice -> {
                     Choice userVote = null;

                    for (Vote vote: user.getVotes()){
                        if (vote.getPoll() == poll){
                            userVote = vote.getChoice();
                            break;
                        }
                    }

                   


            
                    Map<String, String> choiceData = new HashMap<>();
                    choiceData.put("content", choice.getContent());
                    choiceData.put("checked", choice == userVote ? "yes": "no");


                    if (total == 0) {
                        choiceData.put("count", "0");
                    } else {
                        choiceData.put("count", String.valueOf((choice.getVotes().size()) / (double) total * 100));
                    }                     choiceData.put("id", choice.getId().toString());
                    return choiceData;
                })
                .collect(Collectors.toList());

                List<Map<String, Object>> comments = poll.getComments().stream()
                .map(comment -> {
                    
                    Map<String, Object> commentData = new HashMap<>();
                    commentData.put("content", comment.getContent());
                    commentData.put("owner", comment.getUser().getUsername());
                    commentData.put("isOwner", user.getId().equals(comment.getUser().getId()));
                    commentData.put("id", comment.getId().toString());
                    return commentData;
                })
                .collect(Collectors.toList());
            
           

                pollData.put("choices", choiceContents);
                pollData.put("comments", comments);
                pollData.put("total", total == 0 ? 1 : total);
                pollData.put("likes", poll.getLikes().size());
                pollData.put("dislikes", poll.getDislikes().size());

                model.addAttribute("isAdmin", user.getIsAdmin());
                model.addAttribute("hasGroup", true);

                model.addAttribute("poll", pollData);

                return "poll";


    }

    @GetMapping("/add")
    public String getAddPolls(Model model,HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException{
        Claims claims = (Claims) request.getAttribute("user");

        String username = claims.getSubject(); 

        User user = userService.findUserByUsername(username);

       
        if (user == null){
            response.sendRedirect("/auth/login"); 
            return null;

        }

        if (!user.getIsVerified()){
            response.sendRedirect("/auth/otp");  
            return null;
        }

        if (!user.getIsAdmin()){
            response.sendRedirect("/polls");  
            return null;
        }

        model.addAttribute("isAdmin", true);
        model.addAttribute("hasGroup", user.getMemberOf() != null);


        return  "add_poll";
    }


    
}

