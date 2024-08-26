package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingJpaRepository;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemJpaRepository itemRepostory;
    private final BookingJpaRepository bookingRepository;
    private final CommentJpaRepository commentRepository;

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

    public CommentDto createComment(CommentCreateDto commentDto, Long userId, Long itemId){
        Comment comment = CommentMapper.mapCommentCreateDtotoComment(commentDto);
        Optional<Item> item = itemRepostory.findById(itemId);
        if(item.isEmpty()){
            throw new ItemNotFoundException("Вещь не найдена");
        }
        comment.setItem(item.get());
        List<Booking> bookingList = bookingRepository.findByOwner(userId);
        Optional<Booking> booking = bookingList.stream()
                .filter(x -> x.getItem().getId() == itemId)
                .findAny();
        if(booking.isEmpty()){
            throw new AccessException("У пользователя нет прав писать отзыв об этой вещи");
        }
        if(booking.get().getStartDate().isAfter(LocalDateTime.now())){
            throw new AccessException("У пользователя нет прав писать отзыв об этой вещи");
        }
        return CommentMapper.mapCommentToCommentDto(commentRepository.save(comment));

    }
}
