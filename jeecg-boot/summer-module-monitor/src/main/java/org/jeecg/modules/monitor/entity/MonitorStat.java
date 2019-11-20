package org.jeecg.modules.monitor.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="监控统计", description="监控统计")
public class MonitorStat implements Serializable {

    @ApiModelProperty(value = "总数", dataType = "Long")
    private Long totalCount;

    @ApiModelProperty(value = "进入数量", dataType = "Long")
    private Long inCount;

    @ApiModelProperty(value = "外出数量", dataType = "Long")
    private Long outCount;

    @ApiModelProperty(value = "登记数量", dataType = "Long")
    private Long djCount;

    @ApiModelProperty(value = "未登记数量", dataType = "Long")
    private Long wdjCount;
}
