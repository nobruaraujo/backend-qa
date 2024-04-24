package br.com.fiap.api.repository;

import br.com.fiap.api.model.Message;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MessageRepositoryIT {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void shouldCreateTable() {
        var countRegisters = messageRepository.count();
        assertThat(countRegisters).isNotNegative();
    }

    @Test
    void shouldRegisterMessage() {
        //Arrange
        var id = UUID.randomUUID();
        var message = messageMock();
        message.setId(id);

        //Act
        var messageReceived = messageRepository.save(message);

        //Assert
        assertThat(messageReceived)
                .isInstanceOf(Message.class)
                .isNotNull();

        assertThat(messageReceived.getId()).isEqualTo(id);
        assertThat(messageReceived.getContent()).isEqualTo(message.getContent());
        assertThat(messageReceived.getUsername()).isEqualTo(message.getUsername());
    }

    @Test
    void shouldRemoveMessage() {
        fail("Test not implemented");
    }

    @Test
    void shouldSearchMessage() {
        //Arrange
        var id = UUID.randomUUID();
        var message = messageMock();
        message.setId(id);
        registerMessage(message);

        //Act
        var receivedMessageOptional = messageRepository.findById(id);

        //Assert
        assertThat(receivedMessageOptional).isPresent();

        receivedMessageOptional.ifPresent(receivedMessage -> {
            assertThat(receivedMessage.getId()).isEqualTo(message.getId());
            assertThat(receivedMessage.getContent()).isEqualTo(message.getContent());

        });

    }

    @Test
    void shouldListAllMessages() {
        fail("Test not implemented");
    }

    private Message messageMock() {
        return Message.builder()
                .username("João")
                .content("Message content")
                .build();
    }

    private Message registerMessage(Message message) {
        return messageRepository.save(message);
    }
}
