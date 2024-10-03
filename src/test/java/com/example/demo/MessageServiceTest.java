package com.example.demo;

import com.example.demo.application.MessageService;
import com.example.demo.core.config.ValuesConfig;
import com.example.demo.domain.models.Message;
import com.example.demo.domain.models.User;
import com.example.demo.infrastructure.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @InjectMocks
    private MessageService messageService;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ValuesConfig valuesConfig; // Mock para ValuesConfig

    @Mock
    private SimpMessagingTemplate messagingTemplate; // Mock para SimpMessagingTemplate

    private User sender;
    private User recipient;

    @BeforeEach
    public void setUp() {
        sender = new User();
        sender.setId(1L);
        recipient = new User();
        recipient.setId(2L);


        when(valuesConfig.getAesSecret()).thenReturn("1234567890123456");
        when(valuesConfig.getAesAlgorithm()).thenReturn("AES");
        when(valuesConfig.getAesTransformation()).thenReturn("AES/ECB/PKCS5Padding");
    }

    @Test
    public void testCreateMessage() throws Exception {
        String content = "Hello, World!";
        String encryptedContent = "s1aiR0qHAayxg11CyTDX1Q==";

        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> {
            Message message = invocation.getArgument(0);
            message.setContent(encryptedContent);
            return message;
        });


        Message createdMessage = messageService.createMessage(sender, recipient, content);

        assertEquals(sender.getId(), createdMessage.getSender());
        assertEquals(recipient.getId(), createdMessage.getRecipient());


        verify(messagingTemplate).convertAndSend("/topic/messages/" + recipient.getId(), encryptedContent);
    }

    @Test
    public void testDecryptMessage() throws Exception {
        String encryptedContent = "s1aiR0qHAayxg11CyTDX1Q==";
        Message message = new Message();
        message.setContent(encryptedContent);


        String decryptedContent = messageService.receiveMessage(message);


        assertEquals("Hello, World!", decryptedContent);
    }

}
