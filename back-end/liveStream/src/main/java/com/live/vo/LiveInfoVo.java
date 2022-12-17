package com.live.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveInfoVo {
    private String title;
    private String introduction;
    private Integer typeId;
    private List<String> tag;
}
