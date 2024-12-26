package com.itsc.votesphere.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class CreateGroupDto {

    @NotBlank(message = "Group Name is required")
    private String groupName;

}
