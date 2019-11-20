package org.jeecg.modules.monitor.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.monitor.entity.MonitorPerson;
import org.jeecg.modules.monitor.entity.MonitorStat;
import org.jeecg.modules.monitor.service.IMonitorPersonService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 人员监控信息
 * @Author: jeecg-boot
 * @Date:   2019-11-18
 * @Version: V1.0
 */
@Slf4j
@Api(tags="人员监控信息")
@RestController
@RequestMapping("/monitor/monitorPerson")
public class MonitorPersonController extends JeecgController<MonitorPerson, IMonitorPersonService> {
	@Autowired
	private IMonitorPersonService monitorPersonService;

	/**
	 * 分页列表查询
	 *
	 * @param monitorPerson
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "人员监控信息-分页列表查询")
	@ApiOperation(value="人员监控信息-分页列表查询", notes="人员监控信息分页,返回对象IPage<MonitorPerson>")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MonitorPerson monitorPerson,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MonitorPerson> queryWrapper = QueryGenerator.initQueryWrapper(monitorPerson, req.getParameterMap());
		queryWrapper.orderByDesc("out_in_time");
		Page<MonitorPerson> page = new Page<MonitorPerson>(pageNo, pageSize);
		IPage<MonitorPerson> pageList = monitorPersonService.page(page, queryWrapper);
		return Result.ok(pageList);
	}

	/**
	 * 添加
	 *
	 * @param monitorPerson
	 * @return
	 */
	@AutoLog(value = "人员监控信息-添加")
	@ApiOperation(value="人员监控信息-添加", notes="人员监控信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MonitorPerson monitorPerson) {
		monitorPersonService.save(monitorPerson);
		return Result.ok("添加成功！");
	}

	/**
	 * 编辑
	 *
	 * @param monitorPerson
	 * @return
	 */
	@AutoLog(value = "人员监控信息-编辑")
	@ApiOperation(value="人员监控信息-编辑", notes="人员监控信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MonitorPerson monitorPerson) {
		monitorPersonService.updateById(monitorPerson);
		return Result.ok("编辑成功!");
	}

	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "人员监控信息-通过id删除")
	@ApiOperation(value="人员监控信息-通过id删除", notes="人员监控信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		monitorPersonService.removeById(id);
		return Result.ok("删除成功!");
	}

	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "人员监控信息-批量删除")
	@ApiOperation(value="人员监控信息-批量删除", notes="人员监控信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.monitorPersonService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "人员监控信息-通过id查询")
	@ApiOperation(value="人员监控信息-通过id查询", notes="人员监控信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MonitorPerson monitorPerson = monitorPersonService.getById(id);
		return Result.ok(monitorPerson);
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param monitorPerson
	 */
	@RequestMapping(value = "/exportXls")
	public ModelAndView exportXls(HttpServletRequest request, MonitorPerson monitorPerson) {
		return super.exportXls(request, monitorPerson, MonitorPerson.class, "人员监控信息");
	}

	/**
	 * 通过excel导入数据
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
		return super.importExcel(request, response, MonitorPerson.class);
	}

	/**
	 * 获取本日查询
	 * @return
	 */
	@ApiOperation(value="获取本日查询统计", notes="获取本日查询统计" )
	@GetMapping(value = "/getTodayStat")
	public Result<MonitorStat> getTodayStat() {
		MonitorStat monitorStat= monitorPersonService.getTodayStat();
		Result r=new Result(true,CommonConstant.SC_OK_200,monitorStat);
		return r;
	}
}
