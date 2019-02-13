package com.activiti.controller;

import com.activiti.base.service.IRepositoryService;
import com.activiti.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@RestController
public class ModelSaveRestResource
        implements ModelDataJsonConstants {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ModelSaveRestResource.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IRepositoryService irepositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = {"/service/model/{modelId}/save"}, method = {org.springframework.web.bind.annotation.RequestMethod.PUT})
    @ResponseStatus(HttpStatus.OK)
    public void saveModel(@PathVariable String modelId, String json_xml, String name, String description, String svg_xml) {
        try {
            Model model = this.repositoryService.getModel(modelId);
            System.out.println("ModelSaveRestResource.saveModel----------");
            ObjectNode modelJson = (ObjectNode) this.objectMapper.readTree(model.getMetaInfo());
            String json = json_xml;

            modelJson.put("name", name);
            modelJson.put("description", description);
            modelJson.put("json", JsonUtils.objectToJson(irepositoryService.getProcessStarterForJsonXml(json)));//组装流程启动者，例：{"users":"1,2,3"},{"groups":"1,2,3"}
            model.setMetaInfo(modelJson.toString());
            model.setName(name);
            this.repositoryService.saveModel(model);

            this.repositoryService.addModelEditorSource(model.getId(), json_xml.getBytes("utf-8"));

            InputStream svgStream = new ByteArrayInputStream(svg_xml.getBytes("utf-8"));
            TranscoderInput input = new TranscoderInput(svgStream);

            PNGTranscoder transcoder = new PNGTranscoder();

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outStream);

            transcoder.transcode(input, output);
            byte[] result = outStream.toByteArray();
            this.repositoryService.addModelEditorSourceExtra(model.getId(), result);
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error saving model", e);
            throw new ActivitiException("Error saving model", e);
        }
    }

}