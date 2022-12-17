package com.live.entry;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiveAccount implements Serializable {

  @TableId(type = IdType.AUTO)
  private Long id;

  private Long onlineNumber;
  private String livePath;
  private long state;
  @TableField(fill = FieldFill.INSERT)
  private java.sql.Timestamp createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private java.sql.Timestamp updateTime;
  private long isDeleted;
  private long userId;
  private String coverUrl;


}
