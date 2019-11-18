package org.jeecg.modules.monitor.service;

import org.jeecg.modules.monitor.entity.MonitorPerson;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 人员监控信息
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
public interface IMonitorPersonService extends IService<MonitorPerson> {
    public Map<String,Object> getTodayStat();
}
