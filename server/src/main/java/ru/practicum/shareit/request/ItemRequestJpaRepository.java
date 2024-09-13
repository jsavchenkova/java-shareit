package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRequestJpaRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByAuthorId(Long authorId);

    ItemRequest findByIdAndAuthorId(Long id, Long authorId);
}
