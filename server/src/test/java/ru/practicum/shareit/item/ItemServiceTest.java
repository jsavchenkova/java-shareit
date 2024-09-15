package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingJpaRepository;
import ru.practicum.shareit.booking.constant.Status;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.exception.AccessException;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserJpaRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemJpaRepository itemRepository;
    @Mock
    private CommentJpaRepository commentRepository;
    @Mock
    private BookingJpaRepository bookingRepository;
    @Mock
    private UserJpaRepository userRepository;

    @InjectMocks
    private ItemService service;

    @Test
    void createItem() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        ItemDtoCreate itemDtoCreate = ItemDtoCreate.builder()
                .name(name)
                .available(available)
                .description(description)
                .build();

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .build();

        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);

        Mockito.when(itemRepository.save(any())).thenReturn(item);

        ItemDto result = service.createItem(itemDtoCreate, userId);

        Assertions.assertEquals(itemDto, result);
    }

    @Test
    void updateItemItemNotFoundException() {
        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.empty());
        long id = 1;
        String name = "name";
        String description = "description";
        boolean available = true;
        ItemDtoUpdate itemDtoUpdate = ItemDtoUpdate.builder()
                .name(name)
                .available(available)
                .description(description)
                .build();

        Assertions.assertThrows(ItemNotFoundException.class, () -> service.updateItem(id, itemDtoUpdate));
    }

    @Test
    void updateItemNotNull() {
        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.of(new Item()));
        long id = 1;
        String name = "name";
        String description = "description";
        boolean available = true;
        ItemDtoUpdate itemDtoUpdate = ItemDtoUpdate.builder()
                .name(name)
                .available(available)
                .description(description)
                .build();

        long userId = 3;
        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);

        Mockito.when(itemRepository.save(any())).thenReturn(item);

        service.updateItem(userId, itemDtoUpdate);

        Mockito.verify(itemRepository).save(any());
    }

    @Test
    void updateItemNNull() {
        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.of(new Item()));
        long id = 1;
        String name = "name";
        String description = "description";
        boolean available = true;
        ItemDtoUpdate itemDtoUpdate = ItemDtoUpdate.builder()
                .build();

        long userId = 3;
        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);

        Mockito.when(itemRepository.save(any())).thenReturn(item);

        service.updateItem(userId, itemDtoUpdate);

        Mockito.verify(itemRepository).save(any());
    }

    @Test
    void getItems() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .build();
        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);

        Mockito.when(itemRepository.findByUserId(userId)).thenReturn(List.of(item));

        List<ItemDto> result = service.getItems(userId);

        Assertions.assertEquals(result.stream().findFirst().get(), itemDto);
    }

    @Test
    void getItemByIdItemNotFoundException() {
        Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNotFoundException.class, () -> service.getItemById(1L));
    }

    @Test
    void getItemByIdWithoutComments() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;

        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);
        item.setComments(Set.of(1L));
        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(bookingRepository.getLasBooking(id)).thenReturn(List.of());
        Mockito.when(bookingRepository.getNextBooking(id)).thenReturn(List.of());

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .comments(Set.of())
                .build();

        ItemDto result = service.getItemById(1L);

        Assertions.assertEquals(itemDto, result);

    }

    @Test
    void getItemById() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        User user = new User();
        user.setId(2L);
        user.setName("name");
        user.setEmail("email@email.dk");

        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);
        item.setComments(Set.of(1L));
        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(user);

        Mockito.when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));


        Booking lastBooking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now().minusDays(5))
                .endDate(LocalDateTime.now().minusDays(2))
                .item(item)
                .user(user)
                .id(3L)
                .build();

        Booking nextBooking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(6))
                .item(item)
                .user(user)
                .id(4L)
                .build();

        Mockito.when(bookingRepository.getLasBooking(id)).thenReturn(List.of(lastBooking));
        Mockito.when(bookingRepository.getNextBooking(id)).thenReturn(List.of(nextBooking));

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .comments(Set.of(CommentMapper.mapCommentToCommentDto(comment)))
                .lastBooking(BookingMapper.mapBookingToBoodingDto(lastBooking))
                .nextBooking(BookingMapper.mapBookingToBoodingDto(nextBooking))
                .build();

        ItemDto result = service.getItemById(1L);

        Assertions.assertEquals(itemDto, result);

    }

    @Test
    void searchItem() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;

        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);
        item.setComments(Set.of(1L));

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .build();

        Mockito.when(itemRepository.searchItem("text")).thenReturn(List.of(item));

        List<ItemDto> result = service.searchItem("text");

        Assertions.assertEquals(itemDto, result.stream().findFirst().get());
    }

    @Test
    void searchItemBlank() {
        List<Item> result = itemRepository.searchItem("");

        Assertions.assertEquals(0, result.size());
    }

    @Test
    void createComment() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        User user = new User();
        user.setId(userId);
        user.setName("name");
        user.setEmail("email@email.dk");

        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);
        item.setComments(Set.of(1L));

        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now().minusDays(5))
                .endDate(LocalDateTime.now().minusDays(2))
                .item(item)
                .user(user)
                .id(3L)
                .build();

        Mockito.when(bookingRepository.findByOwner(any())).thenReturn(List.of(booking));

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(user);

        CommentCreateDto commentDto = new CommentCreateDto();
        commentDto.setText("text");


        Assertions.assertThrows(AccessException.class, () -> service.createComment(
                commentDto, userId, 5L));
    }

    @Test
    void createCommentException() {

        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        User user = new User();
        user.setId(userId);
        user.setName("name");
        user.setEmail("email@email.dk");

        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);
        item.setComments(Set.of(1L));

        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now().plusDays(5))
                .endDate(LocalDateTime.now().plusDays(7))
                .item(item)
                .user(user)
                .id(3L)
                .build();

        Mockito.when(bookingRepository.findByOwner(any())).thenReturn(List.of(booking));

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(user);

        CommentCreateDto commentDto = new CommentCreateDto();
        commentDto.setText("text");


        Assertions.assertThrows(AccessException.class, () -> service.createComment(
                commentDto, userId, id));
    }

    @Test
    void createCommentItemNotFoundException() {

        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        User user = new User();
        user.setId(userId);
        user.setName("name");
        user.setEmail("email@email.dk");

        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);
        item.setComments(Set.of(1L));

        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now().minusDays(5))
                .endDate(LocalDateTime.now().plusDays(7))
                .item(item)
                .user(user)
                .id(3L)
                .build();

        Mockito.when(bookingRepository.findByOwner(any())).thenReturn(List.of(booking));

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(user);

        CommentCreateDto commentDto = new CommentCreateDto();
        commentDto.setText("text");

        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNotFoundException.class, () -> service.createComment(
                commentDto, userId, id));
    }

    @Test
    void createCommentUserNotFoundException() {

        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        User user = new User();
        user.setId(userId);
        user.setName("name");
        user.setEmail("email@email.dk");

        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);
        item.setComments(Set.of(1L));

        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now().minusDays(5))
                .endDate(LocalDateTime.now().plusDays(7))
                .item(item)
                .user(user)
                .id(3L)
                .build();

        Mockito.when(bookingRepository.findByOwner(any())).thenReturn(List.of(booking));

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(user);

        CommentCreateDto commentDto = new CommentCreateDto();
        commentDto.setText("text");

        Mockito.when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> service.createComment(
                commentDto, userId, id));
    }


}