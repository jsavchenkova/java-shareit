package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepostory itemRepostory;

    public ItemDto createItem(ItemDtoCreate itemDto, Long userId) {
        Item item = ItemMapper.mapItemDtoCreateToItem(itemDto);
        item.setUserId(userId);

        return ItemMapper.mapItemToItemDto(itemRepostory.createItem(item));
    }

    public ItemDto updateItem(long id, ItemDtoUpdate itemDto) {
        return ItemMapper.mapItemToItemDto(itemRepostory.updateItem(id, ItemMapper.mapItemDtoUpdateToItem(itemDto)));
    }

    public List<ItemDto> getItems(Long userId) {
        return itemRepostory.getItems(userId).stream()
                .map(ItemMapper::mapItemToItemDto)
                .toList();
    }

    public ItemDto getItemById(long id) {
        return ItemMapper.mapItemToItemDto(itemRepostory.getItemById(id));
    }

    public List<ItemDto> searchItem(String text, long userId) {
        return itemRepostory.searchItem(text, userId).stream()
                .map(ItemMapper::mapItemToItemDto)
                .toList();
    }

    public void deleteItem(long id) {
        itemRepostory.deleteItem(id);
    }
}
