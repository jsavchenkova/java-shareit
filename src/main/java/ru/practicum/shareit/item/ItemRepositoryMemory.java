package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryMemory implements ItemRepostory {

    private Map<Long, Item> items = new HashMap<>();
    private long id = 0;

    @Override
    public Item createItem(Item item) {
        item.setId(id);
        items.put(id, item);
        id++;
        return item;
    }

    @Override
    public Item updateItem(long id, Item item) {
        Item oldItem = items.get(id);
        if (item.getName() != null && !item.getName().isBlank()) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }
        items.put(id, oldItem);
        return oldItem;
    }

    @Override
    public List<Item> searchItem(String text, long userId) {
        if (text == null || text.isBlank()) {
            return List.of();
        }
        return items.values().stream()
                .filter(x -> x.getName().toLowerCase().contains(text.toLowerCase())
                        || x.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(x -> x.getAvailable())
                .toList();
    }

    @Override
    public List<Item> getItems(long id) {
        return items.values().stream()
                .filter(x -> x.getUserId() == id)
                .toList();
    }

    @Override
    public Item getItemById(long id) {
        if (!items.containsKey(id)) {
            throw new ItemNotFoundException("Пользователь не найден");
        }
        return items.get(id);
    }

    @Override
    public void deleteItem(long id) {

    }
}
