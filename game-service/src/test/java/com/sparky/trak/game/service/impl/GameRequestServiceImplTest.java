package com.sparky.trak.game.service.impl;

import com.sparky.trak.game.domain.GameRequest;
import com.sparky.trak.game.repository.GameRequestRepository;
import com.sparky.trak.game.service.PatchService;
import com.sparky.trak.game.service.dto.GameRequestDto;
import com.sparky.trak.game.service.mapper.GameRequestMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.json.JsonMergePatch;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ExtendWith(MockitoExtension.class)
public class GameRequestServiceImplTest {

    @Mock
    private GameRequestRepository gameRequestRepository;

    @Spy
    private GameRequestMapper gameRequestMapper = GameRequestMapper.INSTANCE;

    @Mock
    private MessageSource messageSource;

    @Mock
    private PatchService patchService;

    @InjectMocks
    private GameRequestServiceImpl gameRequestService;

    @Test
    public void save_withNullGameRequestDto_throwsNullPointerException() {
        // Assert
        Assertions.assertThrows(NullPointerException.class, () -> gameRequestService.save(null));
    }

    @Test
    public void save_withExistingEntity_throwsEntityExistsException() {
        // Arrange
        Mockito.when(gameRequestRepository.existsById(ArgumentMatchers.anyLong()))
                .thenReturn(true);

        Mockito.when(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(Locale.class)))
                .thenReturn("");

        // Assert
        Assertions.assertThrows(EntityExistsException.class, () -> gameRequestService.save(new GameRequestDto()));
    }

    @Test
    public void save_withNewGameRequestDto_savesGameRequestDto() {
        // Arrange
        Mockito.when(gameRequestRepository.existsById(ArgumentMatchers.anyLong()))
                .thenReturn(false);

        Mockito.when(gameRequestRepository.save(ArgumentMatchers.any()))
                .thenReturn(new GameRequest());

        // Act
        gameRequestService.save(new GameRequestDto());

        // Assert
        Mockito.verify(gameRequestRepository, Mockito.atMostOnce())
                .save(ArgumentMatchers.any());
    }

    @Test
    public void findById_withEmptyOptional_throwsEntityNotFoundException() {
        // Arrange
        Mockito.when(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(Locale.class)))
                .thenReturn("");

        Mockito.when(gameRequestRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        // Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> gameRequestService.findById(0L));
    }

    @Test
    public void findById_withValidGameRequest_returnsGameRequestDto() {
        // Arrange
        GameRequest gameRequest = new GameRequest();
        gameRequest.setId(1L);
        gameRequest.setTitle("test-title");
        gameRequest.setCompleted(true);
        gameRequest.setCompletedDate(LocalDate.now());
        gameRequest.setUserId(2L);
        gameRequest.setVersion(3L);

        Mockito.when(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(Locale.class)))
                .thenReturn("");

        Mockito.when(gameRequestRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(gameRequest));

        // Act
        GameRequestDto result = gameRequestService.findById(0L);

        // Assert
        Assertions.assertEquals(result.getId(), gameRequest.getId(), "The mapped ID does not match the DTO.");
        Assertions.assertEquals(result.getTitle(), gameRequest.getTitle(), "The mapped title does not match the DTO.");
        Assertions.assertEquals(result.isCompleted(), gameRequest.isCompleted(), "The mapped completed does not match the DTO.");
        Assertions.assertEquals(result.getCompletedDate(), gameRequest.getCompletedDate(), "The mapped completed date does not match the DTO.");
        Assertions.assertEquals(result.getUserId(), gameRequest.getUserId(), "The mapped user ID does not match the DTO.");
        Assertions.assertEquals(result.getVersion(), gameRequest.getVersion(), "The mapped version does not match the DTO.");
    }

    @Test
    public void findAll_withNullPageable_throwsNullPointerException() {
        // Assert
        Assertions.assertThrows(NullPointerException.class, () -> gameRequestService.findAll(null));
    }

    @Test
    public void findAll_withNoGameRequests_returnsEmptyList() {
        // Arrange
        Mockito.when(gameRequestRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(Page.empty());

        Pageable pageable = Mockito.mock(Pageable.class);

        // Act
        List<GameRequestDto> result = StreamSupport.stream(gameRequestService.findAll(pageable).spliterator(), false)
                .collect(Collectors.toList());

        // Assert
        Assertions.assertTrue(result.isEmpty(), "The result should be empty if no pages console results were found.");
    }

    @Test
    public void findAll_withGameRequests_returnsGameRequestsAsGameRequestDtos() {
        // Arrange
        Page<GameRequest> gameRequests = new PageImpl<>(Arrays.asList(new GameRequest(), new GameRequest()));

        Mockito.when(gameRequestRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(gameRequests);

        Pageable pageable = Mockito.mock(Pageable.class);

        // Act
        List<GameRequestDto> result = StreamSupport.stream(gameRequestService.findAll(pageable).spliterator(), false)
                .collect(Collectors.toList());

        // Assert
        Assertions.assertFalse(result.isEmpty(), "The result shouldn't be empty if the repository returned game requests.");
    }

    @Test
    public void update_withNullGameRequestDto_throwsNullPointerException() {
        // Assert
        Assertions.assertThrows(NullPointerException.class, () -> gameRequestService.update(null));
    }

    @Test
    public void update_withNonExistentEntity_throwsEntityNotFoundException() {
        // Arrange
        Mockito.when(gameRequestRepository.existsById(ArgumentMatchers.anyLong()))
                .thenReturn(false);

        Mockito.when(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(Locale.class)))
                .thenReturn("");

        // Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> gameRequestService.update(new GameRequestDto()));
    }

    @Test
    public void update_withExistingGameRequestDto_updatesGameRequestDto() {
        // Arrange
        Mockito.when(gameRequestRepository.existsById(ArgumentMatchers.anyLong()))
                .thenReturn(true);

        Mockito.when(gameRequestRepository.save(ArgumentMatchers.any()))
                .thenReturn(new GameRequest());

        // Act
        gameRequestService.update(new GameRequestDto());

        // Assert
        Mockito.verify(gameRequestRepository, Mockito.atMostOnce())
                .save(ArgumentMatchers.any());
    }

    @Test
    public void patch_withNoGameRequestMatchingId_throwsEntityNotFoundException() {
        // Arrange
        Mockito.when(gameRequestRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Mockito.when(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(Locale.class)))
                .thenReturn("");

        // Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> gameRequestService.patch(0L, Mockito.mock(JsonMergePatch.class)));
    }

    @Test
    public void patch_withValidId_savesGameRequest() {
        // Arrange
        Mockito.when(gameRequestRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(new GameRequest()));

        Mockito.when(messageSource.getMessage(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(Locale.class)))
                .thenReturn("");

        Mockito.when(patchService.patch(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new GameRequestDto());

        // Act
        gameRequestService.patch(0L, Mockito.mock(JsonMergePatch.class));

        // Assert
        Mockito.verify(gameRequestRepository, Mockito.atMostOnce())
                .save(ArgumentMatchers.any());
    }

    @Test
    public void delete_withNonExistentId_throwsEntityNotFoundException() {
        // Arrange
        Mockito.when(gameRequestRepository.existsById(ArgumentMatchers.anyLong()))
                .thenReturn(false);

        // Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> gameRequestService.deleteById(0L));
    }

    @Test
    public void delete_withExistingId_invokesDeletion() {
        // Arrange
        Mockito.when(gameRequestRepository.existsById(ArgumentMatchers.anyLong()))
                .thenReturn(true);

        Mockito.doNothing().when(gameRequestRepository)
                .deleteById(ArgumentMatchers.anyLong());

        // Act
        gameRequestService.deleteById(0L);

        // Assert
        Mockito.verify(gameRequestRepository, Mockito.atMostOnce())
                .deleteById(ArgumentMatchers.anyLong());
    }
}