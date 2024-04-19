package br.com.fiap.api.repository;

import br.com.fiap.api.model.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
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
    void shouldRegisterMessage() {
        //Arrange
        Message message = messageMock();

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
    void shouldRemoveMessage() {
        //Arrange
        var id = UUID.randomUUID();

        // Faça nada quando no meu repositório for chamado o método deleteById sendo informado qualquer UUID
        doNothing().when(messageRepository).deleteById(any(UUID.class));

        //Act - Faça a deleção do ID id
        messageRepository.deleteById(id);

        //Assert - Verifique no meu repositório se pelo menos passa uma vez o método deleteById sendo informado qualquer um UUID
        verify(messageRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void shouldSearchMessage() {
        //Arrange
        var id = UUID.randomUUID();
        var message = messageMock();
        message.setId(id);

        when(messageRepository.findById(any(UUID.class))).thenReturn(Optional.of(message));

        //Act
        var messageRegisteredOptional = messageRepository.findById(id);


        //Assert
        assertThat(messageRegisteredOptional)
                .isPresent()
                .containsSame(message);

        messageRegisteredOptional.ifPresent(messageRegistered -> {
            assertThat(messageRegistered.getId()).isEqualTo(message.getId());
        });

        verify(messageRepository, times(1)).findById(any(UUID.class));

    }

    @Test
    void shouldListMessages() {
        //Arrange
        Message message1 = messageMock();
        Message message2 = messageMock();
        var messageList = Arrays.asList(
                message1,
                message2
        );
        when(messageRepository.findAll()).thenReturn(messageList);

        //Act
        var messagesReturned = messageRepository.findAll();

        //Assert

        // Cheque se a variável que armazena o consumo do banco tem 2 índices e contém em qualquer ordem o conteúdo da mensagem1 e mensagem2
        assertThat(messagesReturned)
                .hasSize(2)
                .containsExactlyInAnyOrder(message1, message2);

        // Verifique se no repositório, foi chamado uma vez o método findAll()
        verify(messageRepository, times(1)).findAll();
    }

    private Message messageMock() {
        return Message.builder()
                .user("João")
                .content("Message content")
                .build();
    }
}
