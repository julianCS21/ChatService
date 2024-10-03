package com.example.demo.domain.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

    private String recipientJwt;
    private String content;

    public void setRecipientJwt(String recipientJwt) {
        this.recipientJwt = recipientJwt;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
