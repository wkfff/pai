package com.pai.app.admin.common.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pai.base.api.model.Page;
import com.pai.biz.frame.repository.IRepository;
import com.pai.base.core.constants.ActionMsgCode;
import com.pai.base.core.entity.CommonResult;
import com.pai.app.web.core.framework.util.PageUtil;
import com.pai.app.web.core.framework.web.controller.AdminController;
import com.pai.app.web.core.framework.web.entity.QueryBuilder;
import com.pai.base.core.util.string.StringUtils;
import com.pai.base.db.mybatis.impl.domain.PageList;
import com.pai.service.image.utils.RequestUtil;
import com.pai.biz.common.domain.JobTask;
import com.pai.biz.common.repository.JobTaskRepository;
import com.pai.biz.common.persistence.entity.JobTaskPo;

/**
 * 对象功能:任务调度 控制类
 * 开发公司:π
 * 开发人员:FUHAO
 * 创建时间:2017-02-10 10:21:06
 */
@Controller
@RequestMapping("/admin/pai/common/jobTask")
public class JobTaskController extends AdminController<String, JobTaskPo, JobTask>{
	 
	@Resource
	private JobTaskRepository jobTaskRepository;
	
	@Override
	protected IRepository<String, JobTaskPo, JobTask> getRepository() {
		return jobTaskRepository;
	}

	@Override
	protected String getPoEntityComment() {		
		return "任务调度";
	}

	/**
	 * 查询【任务调度】列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("listData")	
	@ResponseBody
	public String listData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//构造分页对象
		QueryBuilder queryBuilder = new QueryBuilder(request);
		Page page = PageUtil.buildPage(request);
		//查询任务调度列表
		PageList<JobTaskPo> jobTaskPoList = (PageList<JobTaskPo>) getRepository().findPaged(queryBuilder.buildMap(),page);
		//构造返回数据
		String listData = buildListData(jobTaskPoList,jobTaskPoList.getPageResult().getTotalCount());
		
		return listData;
	}
	
	/**
	 * 进入【任务调度】编辑页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 * @exception 
	 * @since  1.0.0
	 */	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取主键
		String id = RequestUtil.getParameterNullSafe(request, "id");
		
		//装载领域对象
		//是否新增
		boolean isNew =StringUtils.isEmpty(id)?true:false; 
		JobTask jobTask = null;
		if(isNew){
			jobTask = jobTaskRepository.newInstance();
		}else{
			jobTask = jobTaskRepository.load(id);			
		}		
		
		//根据新增或更新，进行若干业务处理
		if(isNew){
			//TODO
		}		
		
		//构造返回对象和视图
		ModelAndView modelAndView = buildAutoView(request);
		modelAndView.addObject("isNew",isNew);
		modelAndView.addObject(poEntityName, jobTask.getData());
		
		//返回
		return modelAndView;		
	}	
	
	/**
	 * 保存【任务调度】
	 * @param request
	 * @param response
	 * @param jobTaskPo
	 * @throws Exception 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */	
	@RequestMapping("save")
	@ResponseBody
	public CommonResult save(HttpServletRequest request,HttpServletResponse response,JobTaskPo jobTaskPo) throws Exception{
		//是否新增
		boolean isNew = StringUtils.isEmpty(jobTaskPo.getId())?true:false;
		
		//构造领域对象和保存数据
		JobTask jobTask = jobTaskRepository.newInstance();
		jobTask.setData(jobTaskPo);
		jobTask.save();
		
		//构造返回数据
		CommonResult result = new CommonResult();
		result.setSuccess(true);
		if(isNew){
			result.setMsgCode(ActionMsgCode.CREATE.name());	
		}else {
			result.setMsgCode(ActionMsgCode.UPDATE.name());
		}			
		
		//返回
		return result;
	}		
	
	/**
	 * 删除【任务调度】
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws Exception 
	 * CommonResult
	 * @exception 
	 * @since  1.0.0
	 */
	@RequestMapping("delete")
	@ResponseBody
	public CommonResult delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获得待删除的id
		String id = RequestUtil.getParameterNullSafe(request, "id");
		
		//构造领域对象和进行删除操作
		JobTask jobTask = jobTaskRepository.newInstance();				
		jobTask.destroy(id);
		
		//构造返回数据
		CommonResult result = new CommonResult();
		result.setSuccess(true);
		result.setMsgCode(ActionMsgCode.DELETE.name());		
		
		//返回
		return result;
	}

}