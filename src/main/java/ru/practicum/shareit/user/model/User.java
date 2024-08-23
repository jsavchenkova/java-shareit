package ru.practicum.shareit.user.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.shareit.item.model.Item;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO Sprint add-controllers.
 */
@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @ElementCollection
    @CollectionTable(name="items", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="name")
    private Set<String> items = new HashSet<>();
}
