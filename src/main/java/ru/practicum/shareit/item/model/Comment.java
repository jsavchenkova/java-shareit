package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Table(name = "comments")
@Entity
@Getter
@Setter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    @OneToOne
    @JoinColumn(name="itemId")
    private Item item;

    @OneToOne
    @JoinColumn(name="authorId")
    private User author;

    LocalDateTime created;
}
