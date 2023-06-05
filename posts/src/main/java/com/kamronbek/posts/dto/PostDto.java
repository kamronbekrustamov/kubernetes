package com.kamronbek.posts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class PostDto {
    private Long id;
    @NotNull
    private Long authorId;
    @NotEmpty
    private String text;
    private LocalDate postedAt;
    @NotEmpty
    private String topic;
}
