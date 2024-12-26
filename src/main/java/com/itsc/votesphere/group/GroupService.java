package com.itsc.votesphere.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itsc.votesphere.users.User;
import com.itsc.votesphere.users.UserRepository;
import jakarta.transaction.Transactional;
import java.util.*;

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
}
