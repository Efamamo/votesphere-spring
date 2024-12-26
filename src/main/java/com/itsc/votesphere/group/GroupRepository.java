package com.itsc.votesphere.group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByGroupName(String groupName);
    Group findByGroupName (String groupName);
    
}
