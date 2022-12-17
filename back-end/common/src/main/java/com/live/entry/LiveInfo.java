package com.live.entry;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveInfo {

  @TableId(type = IdType.AUTO)
  private long id;
  private String title;
  private String introduction;
  private String notice;
  private long typeId;
  private long userId;
  @TableField(fill = FieldFill.INSERT)
  private java.sql.Timestamp createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private java.sql.Timestamp updateTime;
  private long isDeleted;

}
