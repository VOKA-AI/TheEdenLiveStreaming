package com.live.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveRecording {
  @TableId(type = IdType.AUTO)
  private long id;
  private java.sql.Timestamp liveStartTime;
  private java.sql.Timestamp liveEndTime;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;
  private String coverUrl;
  private long isDeleted;
  private long infoId;

}
