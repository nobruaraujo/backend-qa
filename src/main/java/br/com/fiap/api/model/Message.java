package br.com.fiap.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    private UUID id;

    @Column(nullable = false)
    @NotEmpty(message = "Usuário não pode estar vazio")
    private String user;

    @Column(nullable = false)
    @NotEmpty(message = "Conteúdo não pode estar vazio")
    private String content;

    @Builder.Default
    private LocalDateTime creationMessageTime = LocalDateTime.now();

    @Builder.Default
    private int liked = 0;

}
