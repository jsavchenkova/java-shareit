package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO Sprint add-item-requests.
 */
@Table(name = "requests")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Long authorId;
    private LocalDateTime created;

    @ElementCollection
    @CollectionTable(name = "items", joinColumns = @JoinColumn(name = "request_id"))
    @Column(name = "id")
    private Set<Long> items = new HashSet<>();

}
