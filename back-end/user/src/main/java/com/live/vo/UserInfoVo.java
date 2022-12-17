package com.live.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {
    private long id;
    private String name;
    private String nickname;
    private String portrait;
    private String selfIntroduction;

}
