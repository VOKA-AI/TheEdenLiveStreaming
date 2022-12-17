package com.live.vo;

import com.live.entry.LiveTag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveRecordingVo {
    private String userPortraitUrl;
    private String userName;

    private long infoId;
    private String title;
    private String introduction;
    private String coverUrl;


    private Timestamp startTime;
    private Timestamp endTime;


    private List<LiveTag> tags;
}
