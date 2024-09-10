package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepostory {
    Item createItem(Item item);

    Item updateItem(long id, Item item);

    List<Item> getItems(long id);

    Item getItemById(long id);

    List<Item> searchItem(String text, long userId);

    void deleteItem(long id);
}
