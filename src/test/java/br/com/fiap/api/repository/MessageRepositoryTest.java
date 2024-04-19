package br.com.fiap.api.repository;

import br.com.fiap.api.model.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.*;

public class MessageRepositoryTest {

    AutoCloseable openMocks;
    @Mock
    private MessageRepository messageRepository;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldAllowRegisterMessage() {
        //Arrange
        var message = Message.builder()
                .id(UUID.randomUUID())
                .user("João")
                .content("Message content")
                .build();

        when(messageRepository.save(any(Message.class))).thenReturn(message);

        //Act
        var registeredMessage = messageRepository.save(message);

        //Assert
        assertThat(registeredMessage)
                .isNotNull()
                .isEqualTo(message);

        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void shouldAllowUpdateMessage() {
        fail("Test not implemented");
    }

    @Test
    void shouldAllowRemoveMessage() {
        fail("Test not implemented");
    }

    @Test
    void shouldAllowSearchMessage() {
        fail("Test not implemented");
    }
}
