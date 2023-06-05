package com.kamronbek.posts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "posts_topics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostTopic {
    @Id
    private Long id;

    @Column(name = "topic", nullable = false)
    private String topic;

}
