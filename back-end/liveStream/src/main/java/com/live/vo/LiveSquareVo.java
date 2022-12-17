package com.live.vo;

import com.live.entry.LiveTag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveSquareVo {
    private long userId;
    private String userPortraitUrl;
    private String userName;

    private long roomId;
    private String livePath;

    private long infoId;
    private String title;
    private String introduction;

    private List<LiveTag> tag;
}
