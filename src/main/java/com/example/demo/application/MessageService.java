package com.example.demo.application;

import com.example.demo.core.config.ValuesConfig;
import com.example.demo.domain.models.Message;
import com.example.demo.domain.models.User;
import com.example.demo.infrastructure.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ValuesConfig valuesConfig;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Message createMessage(User sender, User recipient, String content) throws Exception {
        String secretKey = valuesConfig.getAesSecret();
        String encryptedContent = encryptMessage(content, secretKey);

        Message message = new Message();
        message.setContent(encryptedContent);
        message.setSender(sender.getId());
        message.setRecipient(recipient.getId());
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);


        sendMessageToTopic(message,encryptedContent);

        return message;
    }

    private void sendMessageToTopic(Message message, String encryptedContent) {

        String topic = "/topic/messages/" + message.getRecipient();
        messagingTemplate.convertAndSend(topic, encryptedContent);
    }

    private String encryptMessage(String content, String secretKey) throws Exception {
        String algorithm = valuesConfig.getAesAlgorithm();
        String transformation = valuesConfig.getAesTransformation();

        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(content.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String receiveMessage(Message message) throws Exception {
        return decryptMessage(message.getContent(), valuesConfig.getAesSecret());
    }

    private String decryptMessage(String encryptedContent, String secretKey) throws Exception {
        String algorithm = valuesConfig.getAesAlgorithm();
        String transformation = valuesConfig.getAesTransformation();

        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), algorithm);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedContent));
        return new String(decryptedBytes);
    }
}
