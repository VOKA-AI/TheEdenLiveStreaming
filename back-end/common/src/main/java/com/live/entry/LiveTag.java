package com.live.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveTag {
  @TableId(type = IdType.AUTO)
  private long id;
  private String tag;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private long infoId;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private java.sql.Timestamp createTime;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private java.sql.Timestamp updateTime;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private long isDeleted;


}
