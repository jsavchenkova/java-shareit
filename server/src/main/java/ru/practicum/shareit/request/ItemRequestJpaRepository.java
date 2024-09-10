package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRequestJpaRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findAllByAuthorId(Long author_id);
    ItemRequest findByIdAndAuthorId(Long id, Long author_id);
}
