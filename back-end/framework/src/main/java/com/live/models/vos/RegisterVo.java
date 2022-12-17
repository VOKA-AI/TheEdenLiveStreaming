package com.live.models.vos;

import lombok.Data;

@Data
public class RegisterVo {
    private String userName;
    private String password;
    private String mail;
    private String authCode;
}
