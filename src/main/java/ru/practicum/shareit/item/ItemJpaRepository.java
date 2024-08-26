package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<Item, Long> {
    List<Item> findByUserId(Long userId);

    @Query("select new ru.practicum.shareit.item.model.Item(i.id, i.name, i.description, i.available, i.userId) " +
            "from Item as i " +
            "where (lower(i.name) like lower(concat('%', ?1,'%')) " +
            "or lower(i.description) like lower(concat('%', ?1,'%')))" +
            "and i.available = true "
            )
    List<Item> searchItem(String text);
}

