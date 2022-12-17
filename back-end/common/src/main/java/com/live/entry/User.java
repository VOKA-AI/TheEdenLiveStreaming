package com.live.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User {

  @TableId(type = IdType.AUTO)
  private long id;
  private String name;
  private String pwd;
  private String nickname;
  private String portrait;
  private String phone;
  private String mail;
  private String selfIntroduction;
  private long isDeleted;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;

}
