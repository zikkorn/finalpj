package com.tqy.cams.controller;

import com.activiti.base.entity.common.PageResult;
import com.activiti.base.entity.common.ResultMessage;
import com.activiti.base.service.IHistoryService;
import com.activiti.base.util.HessianServiceFactory;
import com.tqy.cams.bean.RzApplication;
import com.tqy.cams.bean.common.BaseStatic;
import com.tqy.cams.service.RzblService;
import com.tqy.cams.service.UserService;
import com.tqy.cams.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("test")
public class Test {
	
    @Autowired
    private UserService userService;
    
    @Autowired
    private RzblService rzblService;

    @RequestMapping("")
    public void test(){
        IHistoryService historyService = HessianServiceFactory.getService(IHistoryService.class, BaseStatic.ACTIVITI_SERVER_URL);
        byte[] data = historyService.getActivitiImageByte("85f653cdb6bf4d5eb827aa7bc6a4e358",true);
        String path = "D:/";
        try{ 
            File file = new File(path);
            if (!file.exists()){
                file.mkdirs();
            }
            FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path +"123.png"));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
            System.out.println("图片地址: " + path);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 展示流程图
     */
    @RequestMapping("showImg")
    public void showImg(String prciId, String procDefId, HttpServletResponse response){
    	rzblService.showImg(prciId,procDefId,response);
    }
    
    /**
     * 认证流程测试
     */
    @RequestMapping("run")
    @ResponseBody
    public String run() {
    	String userId = "1";
    	String processDefKey = "rwbl";
    	String buzId = "";
    	//启动流程实例
/*    	ResultMessage mes = rzblService.startProcess(userId, processDefKey, buzId);
    	Map<String, String> map = JsonUtils.jsonToObject(JsonUtils.objectToJson(mes.getData()), HashMap.class);
    	String prciId = map.get("prciId");
    	String taskId = map.get("taskId");
    	RzApplication rz = new RzApplication("1cams1", "tqy", "1", "28s", "sq", "sunqian", 
    			"13057521111", "1@qq.com", "2019-01-30 16:59:00", null, null, 0, "认证申请");
    	rzblService.updateRzApplication(rz,"1");
    	
    	//查询正在运行的任务
    	PageResult result = rzblService.queryMyTask(userId,processDefKey);
    	Object data = result.getData();
 */   	
    	return "ok";
    }
    
}
