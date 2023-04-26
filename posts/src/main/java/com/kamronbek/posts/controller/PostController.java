package com.kamronbek.posts.controller;

import com.kamronbek.posts.dto.PostDto;
import com.kamronbek.posts.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public PostDto getPost(@PathVariable("id") Long id) {
        return postService.getPost(id);
    }


    @PostMapping
    public PostDto createPost(@Valid @RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @PutMapping("/{id}")
    public PostDto editPost(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        return postService.editPost(id, postDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
    }
}
