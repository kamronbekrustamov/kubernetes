package com.kamronbek.posts.service;

import com.kamronbek.posts.dto.PostDto;
import com.kamronbek.posts.entity.Post;
import com.kamronbek.posts.entity.PostTopic;
import com.kamronbek.posts.error.BadRequestException;
import com.kamronbek.posts.error.NotFoundException;
import com.kamronbek.posts.repository.PostRepository;
import com.kamronbek.posts.repository.PostTopicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostTopicRepository postTopicRepository;
    private final UserApiService userApiService;
    private final String postNotFoundMessage = "Post with id %d does not exist";
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(postNotFoundMessage, id)));
        PostTopic postTopic = postTopicRepository.findById(post.getId()).orElse(null);
        String topic = postTopic == null ? null : postTopic.getTopic();
        return new PostDto(post.getId(), post.getAuthorId(), post.getText(), post.getPostedAt(), topic);
    }

    @Transactional
    public PostDto createPost(PostDto postDto) {
        Post post = new Post(null, postDto.getAuthorId(), postDto.getText(), LocalDate.now());
        userApiService.updateNumberOfPosts(postDto.getAuthorId(), true);
        postRepository.save(post);
        PostTopic postTopic = new PostTopic(post.getId(), postDto.getTopic());
        postTopicRepository.save(postTopic);
        return new PostDto(post.getId(), post.getAuthorId(), post.getText(), post.getPostedAt(), postTopic.getTopic());
    }

    @Transactional
    public PostDto editPost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(postNotFoundMessage, id)));
        if (postDto.getText() != null && !postDto.getText().equals("")) {
            post.setText(postDto.getText());
        }
        PostTopic postTopic = postTopicRepository.findById(post.getId()).orElse(new PostTopic());
        if (postDto.getTopic() != null && !postDto.getTopic().equals("")) {
            postTopic.setTopic(postDto.getTopic());
            postTopic.setId(post.getId());
            postTopicRepository.save(postTopic);
        }
        return new PostDto(post.getId(), post.getAuthorId(), post.getText(), post.getPostedAt(), postTopic.getTopic());
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(postNotFoundMessage, id)));
        try {
            userApiService.updateNumberOfPosts(post.getAuthorId(), false);
        } catch (BadRequestException ignored) {}
        postRepository.deleteById(post.getId());
        postTopicRepository.deleteById(post.getId());
    }
}
