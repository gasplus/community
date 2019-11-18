package org.jeecg.modules.monitor.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.monitor.entity.MonitorPerson;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 人员监控信息
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
public interface MonitorPersonMapper extends BaseMapper<MonitorPerson> {

    /**
     * 获取当日统计
     * @return
     */
    @Select(" SELECT COUNT(*) AS totalCount,SUM( CASE  WHEN `out_in_type` ='0' THEN 1 ELSE 0 END ) AS inCount ,SUM( CASE  WHEN `out_in_type` ='1' THEN 1 ELSE 0 END ) AS outCount,SUM( CASE  WHEN `person_type` ='0' THEN 1 ELSE 0 END ) AS djCount ,SUM( CASE  WHEN `person_type` ='1' THEN 1 ELSE 0 END ) AS wdjCount  FROM `monitor_person`  where DATE_FORMAT(`out_in_time` , '%Y-%m-%d')  =DATE_FORMAT(NOW() , '%Y-%m-%d')")
     Map<String,Object> getCurrentDayStat();
}
