package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRepositoryTest {

    private final ItemJpaRepository itemRepository;

    @Test
    void createItem() {
        Item item = new Item();
        item.setName("name");
        item = itemRepository.save(item);

        Assertions.assertNotNull(item.getId());
    }

    @Test
    void updateItem() {
        Item item = new Item();
        item.setName("name");
        item = itemRepository.save(item);
        item.setName("name739");

        Item result = itemRepository.save(item);

        Assertions.assertEquals(item.getName(), result.getName());

    }

    @Test
    void getItems() {
        Item item = new Item();
        item.setName("name");
        item = itemRepository.save(item);

        List<Item> result = itemRepository.findAll();

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getItemById() {
        Item item = new Item();
        item.setName("name");
        item = itemRepository.save(item);

        Optional<Item> result = itemRepository.findById(item.getId());

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    void searchItem() {
        Item item = new Item();
        item.setName("name");
        item.setAvailable(true);
        item = itemRepository.save(item);

        List<Item> result = itemRepository.searchItem("ame");

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void deleteItem() {
        Item item = new Item();
        item.setName("name");
        item.setAvailable(true);
        item = itemRepository.save(item);

        itemRepository.deleteById(item.getId());

        Optional<Item> result = itemRepository.findById(item.getId());
        Assertions.assertTrue(result.isEmpty());
    }

}