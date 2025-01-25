package com.itsc.votesphere.comments;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class CommentData {
    @NotBlank(message = "Content is required")
    private String content;
}
