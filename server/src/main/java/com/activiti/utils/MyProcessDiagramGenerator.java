package com.activiti.utils;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class MyProcessDiagramGenerator extends DefaultProcessDiagramGenerator {

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor) {
        return generateProcessDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows, activityFontName, labelFontName, annotationFontName, customClassLoader, scaleFactor).generateImage(imageType);
    }

    protected DefaultProcessDiagramCanvas generateProcessDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor) {
        this.prepareBpmnModel(bpmnModel);
        DefaultProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(bpmnModel, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
        Iterator var12 = bpmnModel.getPools().iterator();

        while(var12.hasNext()) {
            Pool pool = (Pool)var12.next();
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo);
        }

        var12 = bpmnModel.getProcesses().iterator();

        Process process;
        Iterator var20;
        while(var12.hasNext()) {
            process = (Process)var12.next();
            var20 = process.getLanes().iterator();

            while(var20.hasNext()) {
                Lane lane = (Lane)var20.next();
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
                processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo);
            }
        }

        var12 = bpmnModel.getProcesses().iterator();

        while(var12.hasNext()) {
            process = (Process)var12.next();
            var20 = process.findFlowElementsOfType(FlowNode.class).iterator();

            while(var20.hasNext()) {
                FlowNode flowNode = (FlowNode)var20.next();
                this.drawActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, highLightedFlows, scaleFactor);
            }
        }

        var12 = bpmnModel.getProcesses().iterator();

        while(true) {
            List subProcesses;
            do {
                if (!var12.hasNext()) {
                    return processDiagramCanvas;
                }

                process = (Process)var12.next();
                var20 = process.getArtifacts().iterator();

                while(var20.hasNext()) {
                    Artifact artifact = (Artifact)var20.next();
                    this.drawArtifact(processDiagramCanvas, bpmnModel, artifact);
                }

                subProcesses = process.findFlowElementsOfType(SubProcess.class, true);
            } while(subProcesses == null);

            Iterator var24 = subProcesses.iterator();

            while(var24.hasNext()) {
                SubProcess subProcess = (SubProcess)var24.next();
                Iterator var17 = subProcess.getArtifacts().iterator();

                while(var17.hasNext()) {
                    Artifact subProcessArtifact = (Artifact)var17.next();
                    this.drawArtifact(processDiagramCanvas, bpmnModel, subProcessArtifact);
                }
            }
        }
    }
}
