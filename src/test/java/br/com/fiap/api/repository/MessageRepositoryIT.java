package br.com.fiap.api.repository;

import br.com.fiap.api.model.Message;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class MessageRepositoryIT {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    void shouldCreateTable() {
        var countRegisters = messageRepository.count();
        assertThat(countRegisters).isGreaterThan(0);
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
        //Arrange
        var id = UUID.fromString("a5a7cf44-e8b0-4579-a9db-c7cab7bfbbc8");
        //Act
        messageRepository.deleteById(id);
        var messageReceivedOptional = messageRepository.findById(id);
        //Assert
        assertThat(messageReceivedOptional).isEmpty();
    }

    @Test
    void shouldSearchMessage() {
        //Arrange
        var id = UUID.fromString("e0a0fe4a-d907-4678-9691-87d8e07d41fc");

        //Act
        var receivedMessageOptional = messageRepository.findById(id);

        //Assert
        assertThat(receivedMessageOptional).isPresent();

        receivedMessageOptional.ifPresent(receivedMessage -> {
            assertThat(receivedMessage.getId()).isEqualTo(id);

        });

    }

    @Test
    void shouldListAllMessages() {
        //Act
        List<Message> messagesOptional = messageRepository.findAll();
        //Assert
        assertThat(messagesOptional).asList().hasSizeGreaterThan(0);
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
