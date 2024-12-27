package com.itsc.votesphere.group;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired 
    private UserRepository userRepository;

    @Transactional
    public Group createGroup(String groupName, User admin) {
       

        Group group = new Group();
        group.setGroupName(groupName.trim());
        group.setAdmin(admin);
        admin.setMemberOf(groupRepository.save(group));
        userRepository.save(admin);

        return group;
    }

    public List<User> getGroup(Group group) {
        
        return userRepository.findAll().stream()
        .filter(user -> user.getMemberOf() != null && user.getMemberOf().equals(group))
        .collect(Collectors.toList());
       
    }
}
