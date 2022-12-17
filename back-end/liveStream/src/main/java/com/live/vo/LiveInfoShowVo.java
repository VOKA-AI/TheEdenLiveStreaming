package com.live.vo;

import com.live.entry.LiveTag;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveInfoShowVo {
    private String title;
    private String introduction;
    private long typeId;
    private String type;
    private List<LiveTag> tag;
    private String coverUrl;
}
