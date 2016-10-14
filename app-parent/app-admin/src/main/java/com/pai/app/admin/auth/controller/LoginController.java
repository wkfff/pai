package com.pai.app.admin.auth.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.octo.captcha.service.image.ImageCaptchaService;
import com.pai.app.web.core.framework.web.controller.LigerUIController;
import com.pai.base.core.entity.CommonResult;
import com.pai.base.core.util.string.StringUtils;
import com.pai.biz.auth.persistence.entity.AuthUserPo;
import com.pai.biz.auth.persistence.entity.LoginInfo;
import com.pai.biz.auth.repository.AuthResourcesRepository;
import com.pai.biz.auth.repository.AuthUserRepository;

@Controller
@RequestMapping("/")
public class LoginController extends LigerUIController{

	private final static String LOGIN_INFO = "loginInfo";
	
	@Resource
	private ImageCaptchaService imageCaptchaService = null;
	
	@Resource
	AuthUserRepository authUserRepository = null;	
	@Resource
	AuthResourcesRepository authResourcesRepository = null;
	@Override
	protected void initController() {
		// TODO Auto-generated method stub
		
	}

	@RequestMapping("adminLogin")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,LoginInfo loginInfo){
		ModelAndView modelAndView = new ModelAndView("/admin/pai/auth/login");
		 if(loginInfo.isLogin()){	//是提交登录	
			 CommonResult commonResult = null;
				if(isCaptchaCorrect(request.getSession().getId(),loginInfo.getCaptchaCode())){
					//查询用户
					AuthUserPo authUserPo = authUserRepository.getAccount(loginInfo.getUserName(), loginInfo.getEncryptPassword());
					if(authUserPo != null){
						System.out.println("------------------");
					}
//					commonResult = authUser.login(getIpAddr(request));
//					if(commonResult.isSuccess()){
						//权限过滤
//						List<AuthResourcesPo> authResourcesPos =  authResourcesRepository.findBbsResByUserId(user.getId());
//						authUser.getData().setAuthResourcesPos(authResourcesPos);
						//往在线上下文中设置数据
//						OuOnlineHolder.setUserPo(request.getSession(),authUser.getData());
//						redirectUrl(response, request.getAttribute(WebConstants.CONTEXT_PATH)+ UrlConstants.MAIN_URL);
//						return null;
//					}
					commonResult = new CommonResult();
					commonResult.setSuccess(true);
					return null;
				}else{
					commonResult = new CommonResult();
					commonResult.setSuccess(false);
//					commonResult.setMsgCode(LoginMsgCode.CAPTCHA.name());					
				}				
				loginInfo.setCommonResult(commonResult);		
		 }else{	//是访问登录页面								   
//			 	if(loginInfo.getLoginFormStatic().equals(LoginFrom.ADMIN)){			 		
//			 		loginInfo.reset();			 					 	
//			 	}
		 }		
		 
		 modelAndView.addObject(LOGIN_INFO, loginInfo);
		 return	modelAndView;
	}
	
	@RequestMapping("logout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
//		OuOnlineHolder.logout();
		request.getSession().invalidate();
//		redirectUrl(response, request.getAttribute(WebConstants.CONTEXT_PATH) + UrlConstants.LOGIN_URL);
	}
	
	private boolean isCaptchaCorrect(String sessionId,String postCaptchaCode){
		boolean isCorrect = true;
		String captchaId = sessionId;		
		if (StringUtils.isNotEmpty(postCaptchaCode)) {
			try{
				isCorrect = imageCaptchaService.validateResponseForID(captchaId, postCaptchaCode.toLowerCase()).booleanValue();
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			isCorrect = false;		
		}
		return isCorrect;
	}	
	
	public String getIpAddr(HttpServletRequest request) {  
		   String fromSource = "X-Real-IP";  
		    String ip = request.getHeader("X-Real-IP");  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("X-Forwarded-For");  
		        fromSource = "X-Forwarded-For";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("Proxy-Client-IP");  
		        fromSource = "Proxy-Client-IP";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		        fromSource = "WL-Proxy-Client-IP";  
		    }  
		    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		        fromSource = "request.getRemoteAddr";  
		    }  
		    logger.info("App Client IP: "+ip+", fromSource: "+fromSource);  
		    return ip;  
	}  
}
