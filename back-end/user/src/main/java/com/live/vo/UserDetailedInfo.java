package com.live.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailedInfo {
    private long id;
    private String name;
    private String nickname;
    private String portrait;
    private String selfIntroduction;
    private Long followedNumber;
    private Boolean state;
}
