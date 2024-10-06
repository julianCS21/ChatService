package com.example.demo.infrastructure.inbound.controlllers;

import com.example.demo.application.MessageService;
import com.example.demo.core.utils.JwtUtil;
import com.example.demo.domain.DTO.MessageDTO;
import com.example.demo.domain.models.Message;
import com.example.demo.domain.models.User;
import com.example.demo.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestBody MessageDTO messageDTO,
            HttpServletRequest request) throws Exception {
        String senderJwt = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String senderEmail = jwtUtil.getAuthenticatedUserFromJwt(senderJwt);

        if (senderEmail == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender is not authenticated");
        }

        String recipientJwt = messageDTO.getRecipientJwt();
        String recipientEmail = jwtUtil.getAuthenticatedUserFromJwt(recipientJwt);

        if (recipientEmail == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Recipient is not authenticated");
        }

        Optional<User> sender = userRepository.findByEmail(senderEmail);
        Optional<User> recipient = userRepository.findByEmail(recipientEmail);

        if (sender.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sender doesn't exist");
        }

        if (recipient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Recipient doesn't exist");
        }



        Message newMessage = messageService.createMessage(sender.get(), recipient.get(), messageDTO.getContent());

        return ResponseEntity.ok("Message sent successfully");
    }



}
