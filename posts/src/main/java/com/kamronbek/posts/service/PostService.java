package com.kamronbek.posts.service;

import com.kamronbek.posts.dto.PostDto;
import com.kamronbek.posts.entity.Post;
import com.kamronbek.posts.error.BadRequestException;
import com.kamronbek.posts.error.NotFoundException;
import com.kamronbek.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserApiService userApiService;
    private final String postNotFoundMessage = "Post with id %d does not exist";
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(postNotFoundMessage, id)));
        return new PostDto(post.getId(), post.getAuthorId(), post.getText(), post.getPostedAt());
    }

    @Transactional
    public PostDto createPost(PostDto postDto) {
        Post post = new Post(null, postDto.getAuthorId(), postDto.getText(), LocalDate.now());
        userApiService.updateNumberOfPosts(postDto.getAuthorId(), true);
        postRepository.save(post);
        return new PostDto(post.getId(), post.getAuthorId(), post.getText(), post.getPostedAt());
    }

    @Transactional
    public PostDto editPost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(postNotFoundMessage, id)));
        if (postDto.getText() == null || "".equals(postDto.getText())) {
            throw new BadRequestException("text cannot be null or empty");
        }
        post.setText(postDto.getText());
        return new PostDto(post.getId(), post.getAuthorId(), post.getText(), post.getPostedAt());
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(postNotFoundMessage, id)));
        try {
            userApiService.updateNumberOfPosts(post.getAuthorId(), false);
        } catch (BadRequestException ignored) {}
        postRepository.deleteById(post.getId());
    }
}
