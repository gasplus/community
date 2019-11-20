package org.jeecg.modules.monitor.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 人员监控信息
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
@Data
@ApiModel(value="人员监控信息", description="人员监控信息")
@TableName("monitor_person")
public class MonitorPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	/**主键*/
	@ApiModelProperty(value = "主键")
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	@Excel(name = "创建人", width = 15)
	private java.lang.String createBy;
	/**创建日期*/
	@ApiModelProperty(value = "创建日期")
	@Excel(name = "创建日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	@Excel(name = "更新人", width = 15)
	private java.lang.String updateBy;
	/**更新日期*/
	@ApiModelProperty(value = "更新日期")
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
	@Excel(name = "所属部门", width = 15)
	private java.lang.String sysOrgCode;
	/**人员姓名*/
	@ApiModelProperty(value = "人员姓名")
	@Excel(name = "人员姓名", width = 15)
	private java.lang.String personName;
	/**身份证*/
	@ApiModelProperty(value = "身份证")
	@Excel(name = "身份证", width = 15)
	private java.lang.String personIdCard;
	/**进入类型*/
	@ApiModelProperty(value = "进入类型")
	@Excel(name = "进入类型", width = 15)
	private java.lang.String outInType;
	/**进出时间*/
	@ApiModelProperty(value = "进出时间")
	@Excel(name = "进出时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date outInTime;
	/**人员类型*/
	@ApiModelProperty(value = "人员类型")
	@Excel(name = "人员类型", width = 15)
	private java.lang.String personType;
	/**图片地址*/
	@ApiModelProperty(value = "图片地址")
	@Excel(name = "图片地址", width = 15)
	private java.lang.String photoUrl;
	/**进出地址*/
	@ApiModelProperty(value = "进出地址")
	@Excel(name = "进出地址", width = 15)
	private java.lang.String address;
	/**设备编号*/
	@ApiModelProperty(value = "设备编号")
	@Excel(name = "设备编号", width = 15)
	private java.lang.String deviceCode;
}
