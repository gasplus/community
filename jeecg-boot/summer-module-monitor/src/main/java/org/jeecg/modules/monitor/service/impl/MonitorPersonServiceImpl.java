package org.jeecg.modules.monitor.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.monitor.entity.MonitorPerson;
import org.jeecg.modules.monitor.mapper.MonitorPersonMapper;
import org.jeecg.modules.monitor.service.IMonitorPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: monitor_person
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
@Service
public class MonitorPersonServiceImpl  extends ServiceImpl<MonitorPersonMapper, MonitorPerson> implements IMonitorPersonService {

    @Autowired
    private MonitorPersonMapper monitorPersonMapper;

    @Override
    public Map<String, Object> getTodayStat() {
        return monitorPersonMapper.getTodayStat();
    }
}
