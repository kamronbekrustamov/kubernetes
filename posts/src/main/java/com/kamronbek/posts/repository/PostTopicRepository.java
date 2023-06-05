package com.kamronbek.posts.repository;

import com.kamronbek.posts.entity.PostTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTopicRepository extends JpaRepository<PostTopic, Long> {
}
