package org.jeecg.modules.monitor.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.monitor.entity.MonitorCar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.monitor.entity.MonitorStat;

/**
 * @Description: monitor_car
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
public interface MonitorCarMapper extends BaseMapper<MonitorCar> {

    @Select(" SELECT COUNT(*) AS totalCount,SUM( CASE  WHEN `out_in_type` ='0' THEN 1 ELSE 0 END ) AS inCount ,SUM( CASE  WHEN `out_in_type` ='1' THEN 1 ELSE 0 END ) AS outCount,SUM( CASE  WHEN `car_type` ='0' THEN 1 ELSE 0 END ) AS djCount ,SUM( CASE  WHEN `car_type` ='1' THEN 1 ELSE 0 END ) AS wdjCount  FROM `monitor_car`  where DATE_FORMAT(`out_in_time` , '%Y-%m-%d')  =DATE_FORMAT(NOW() , '%Y-%m-%d')")
    MonitorStat getTodayStat();
}
