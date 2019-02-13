package com.activiti.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StencilsetRestResource
{
	
	@Autowired
	private RepositoryService repositoryService;
	
  @RequestMapping(value={"/service/editor/stencilset"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json;charset=utf-8"})
  @ResponseBody
  public String getStencilset(String modelId)
  {
	  System.out.println("StencilsetRestResource.getStencilset-----------");
    InputStream stencilsetStream = getClass().getClassLoader().getResourceAsStream("stencilset.json");
    try {
      return IOUtils.toString(stencilsetStream, "utf-8");
    } catch (Exception e) {
      throw new ActivitiException("Error while loading stencil set", e);
    }
  }
  
  @RequestMapping(value={"/service/editor/stencilset/{modelId}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET}, produces={"application/json;charset=utf-8"})
  @ResponseBody
  public String getStencilsetById(@PathVariable String modelId)
  {
	  Map<String,String> m = new HashMap<String, String>();
		  Model model = this.repositoryService.getModel(modelId);
		  m.put("name", model.getName());
		  m.put("key", model.getKey());
	  System.out.println("StencilsetRestResource.getStencilset-----------");
    InputStream stencilsetStream = getClass().getClassLoader().getResourceAsStream("stencilset.json");
    try {
    	m.put("stencilset", IOUtils.toString(stencilsetStream, "utf-8"));
      return m.get("stencilset");
    } catch (Exception e) {
      throw new ActivitiException("Error while loading stencil set", e);
    }
  }
  
  @RequestMapping("/service/editor/{modelId}")
  public Model getModel(@PathVariable String modelId){
	  Model model = this.repositoryService.getModel(modelId);
	  return model;
  }
}