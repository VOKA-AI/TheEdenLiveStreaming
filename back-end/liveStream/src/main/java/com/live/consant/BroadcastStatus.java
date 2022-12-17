package com.live.consant;

import lombok.Data;
import com.live.webapi.ResultCode;

@Data
public class BroadcastStatus {
    private Long time;
    private ResultCode resultCode;
}
