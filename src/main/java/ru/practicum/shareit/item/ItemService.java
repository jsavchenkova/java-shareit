package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemJpaRepository itemRepostory;

    public ItemDto createItem(ItemDtoCreate itemDto, Long userId) {
        Item item = ItemMapper.mapItemDtoCreateToItem(itemDto);
        item.setUserId(userId);

        return ItemMapper.mapItemToItemDto(itemRepostory.save(item));
    }

    public ItemDto updateItem(long id, ItemDtoUpdate itemDto) {
        Item item = ItemMapper.mapItemDtoUpdateToItem(itemDto);
        Optional<Item> oItem = itemRepostory.findById(id);
        if (oItem.isEmpty()){
            throw new ItemNotFoundException("Предмет не найден.");
        }
        Item oldItem = oItem.get();
        if(item.getAvailable() != null){
            oldItem.setAvailable(item.getAvailable());
        }
        if(item.getDescription() != null && !item.getDescription().isBlank()){
            oldItem.setDescription(item.getDescription());
        }
        if(item.getName() != null && ! item.getName().isBlank()){
            oldItem.setName(item.getName());
        }

        return ItemMapper.mapItemToItemDto(itemRepostory.save(oldItem));
    }

    public List<ItemDto> getItems(Long userId) {

        return itemRepostory.findByUserId(userId).stream()
                .map(ItemMapper::mapItemToItemDto)
                .toList();
    }

    public ItemDto getItemById(long id) {
        Optional<Item> oItem = itemRepostory.findById(id);
        if (oItem.isEmpty()){
            throw new ItemNotFoundException("Предмет не найден.");
        }
        return ItemMapper.mapItemToItemDto(oItem.get());
    }

    public List<ItemDto> searchItem(String text) {
        if(text == null || text.isBlank()){
            return List.of();
        }
        return itemRepostory.searchItem(text).stream()
                .map(ItemMapper::mapItemToItemDto)
                .toList();
    }

    public void deleteItem(long id) {
        itemRepostory.deleteById(id);
    }
}
