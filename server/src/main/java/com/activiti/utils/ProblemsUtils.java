package com.activiti.utils;

import org.activiti.validation.ValidationError;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

/**
 * 流程返回描述
 **/
public class ProblemsUtils {

    //加载配置文件
    private static Properties prop = new Properties();

    static {
        try {
//            prop.load(ProblemsUtils.class.getResourceAsStream("/problems.properties"));
            // 解决中文乱码问题
            prop.load(new InputStreamReader(ProblemsUtils.class.getClassLoader().getResourceAsStream("problems.properties"), "UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static final String ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE = prop.getProperty("");
//    public static final String PROCESS_DEFINITION_NOT_EXECUTABLE = prop.getProperty("");
//    public static final String ASSOCIATION_INVALID_SOURCE_REFERENCE = prop.getProperty("");
//    public static final String ASSOCIATION_INVALID_TARGET_REFERENCE = prop.getProperty("");
//    public static final String EXECUTION_LISTENER_IMPLEMENTATION_MISSING = prop.getProperty("");
//    public static final String EVENT_LISTENER_IMPLEMENTATION_MISSING = prop.getProperty("");
//    public static final String EVENT_LISTENER_INVALID_IMPLEMENTATION = prop.getProperty("");
//    public static final String EVENT_LISTENER_INVALID_THROW_EVENT_TYPE = prop.getProperty("");
//    public static final String START_EVENT_MULTIPLE_FOUND = prop.getProperty("");
//    public static final String START_EVENT_INVALID_EVENT_DEFINITION = prop.getProperty("");
//    public static final String SEQ_FLOW_INVALID_SRC = prop.getProperty("");
//    public static final String SEQ_FLOW_INVALID_TARGET = prop.getProperty("");
//    public static final String USER_TASK_LISTENER_IMPLEMENTATION_MISSING = prop.getProperty("");
//    public static final String SERVICE_TASK_INVALID_TYPE = prop.getProperty("");
//    public static final String SERVICE_TASK_RESULT_VAR_NAME_WITH_DELEGATE = prop.getProperty("");
//    public static final String SERVICE_TASK_MISSING_IMPLEMENTATION = prop.getProperty("");
//    public static final String SERVICE_TASK_WEBSERVICE_INVALID_OPERATION_REF = prop.getProperty("");
//    public static final String SEND_TASK_INVALID_IMPLEMENTATION = prop.getProperty("");
//    public static final String SEND_TASK_INVALID_TYPE = prop.getProperty("");
//    public static final String SEND_TASK_WEBSERVICE_INVALID_OPERATION_REF = prop.getProperty("");
//    public static final String SCRIPT_TASK_MISSING_SCRIPT = prop.getProperty("");
//    public static final String MAIL_TASK_NO_RECIPIENT = prop.getProperty("");
//    public static final String MAIL_TASK_NO_CONTENT = prop.getProperty("");
//    public static final String SHELL_TASK_NO_COMMAND = prop.getProperty("");
//    public static final String SHELL_TASK_INVALID_PARAM = prop.getProperty("");
//    public static final String EXCLUSIVE_GATEWAY_NO_OUTGOING_SEQ_FLOW = prop.getProperty("");
//    public static final String EXCLUSIVE_GATEWAY_CONDITION_NOT_ALLOWED_ON_SINGLE_SEQ_FLOW = prop.getProperty("");
//    public static final String EXCLUSIVE_GATEWAY_CONDITION_ON_DEFAULT_SEQ_FLOW = prop.getProperty("");
//    public static final String EXCLUSIVE_GATEWAY_SEQ_FLOW_WITHOUT_CONDITIONS = prop.getProperty("");
//    public static final String EVENT_GATEWAY_ONLY_CONNECTED_TO_INTERMEDIATE_EVENTS = prop.getProperty("");
//    public static final String BPMN_MODEL_TARGET_NAMESPACE_TOO_LONG = prop.getProperty("");
//    public static final String PROCESS_DEFINITION_ID_TOO_LONG = prop.getProperty("");
//    public static final String PROCESS_DEFINITION_NAME_TOO_LONG = prop.getProperty("");
//    public static final String PROCESS_DEFINITION_DOCUMENTATION_TOO_LONG = prop.getProperty("");
//    public static final String FLOW_ELEMENT_ID_TOO_LONG = prop.getProperty("");
//    public static final String SUBPROCESS_MULTIPLE_START_EVENTS = prop.getProperty("");
//    public static final String SUBPROCESS_START_EVENT_EVENT_DEFINITION_NOT_ALLOWED = prop.getProperty("");
//    public static final String EVENT_SUBPROCESS_INVALID_START_EVENT_DEFINITION = prop.getProperty("");
//    public static final String BOUNDARY_EVENT_NO_EVENT_DEFINITION = prop.getProperty("");
//    public static final String BOUNDARY_EVENT_INVALID_EVENT_DEFINITION = prop.getProperty("");
//    public static final String BOUNDARY_EVENT_CANCEL_ONLY_ON_TRANSACTION = prop.getProperty("");
//    public static final String BOUNDARY_EVENT_MULTIPLE_CANCEL_ON_TRANSACTION = prop.getProperty("");
//    public static final String INTERMEDIATE_CATCH_EVENT_NO_EVENTDEFINITION = prop.getProperty("");
//    public static final String INTERMEDIATE_CATCH_EVENT_INVALID_EVENTDEFINITION = prop.getProperty("");
//    public static final String ERROR_MISSING_ERROR_CODE = prop.getProperty("");
//    public static final String EVENT_MISSING_ERROR_CODE = prop.getProperty("");
//    public static final String EVENT_TIMER_MISSING_CONFIGURATION = prop.getProperty("");
//    public static final String THROW_EVENT_INVALID_EVENTDEFINITION = prop.getProperty("");
//    public static final String MULTI_INSTANCE_MISSING_COLLECTION = prop.getProperty("");
//    public static final String MESSAGE_MISSING_NAME = prop.getProperty("");
//    public static final String MESSAGE_INVALID_ITEM_REF = prop.getProperty("");
//    public static final String MESSAGE_EVENT_MISSING_MESSAGE_REF = prop.getProperty("");
//    public static final String MESSAGE_EVENT_INVALID_MESSAGE_REF = prop.getProperty("");
//    public static final String MESSAGE_EVENT_MULTIPLE_ON_BOUNDARY_SAME_MESSAGE_ID = prop.getProperty("");
//    public static final String OPERATION_INVALID_IN_MESSAGE_REFERENCE = prop.getProperty("");
//    public static final String SIGNAL_EVENT_MISSING_SIGNAL_REF = prop.getProperty("");
//    public static final String SIGNAL_EVENT_INVALID_SIGNAL_REF = prop.getProperty("");
//    public static final String COMPENSATE_EVENT_INVALID_ACTIVITY_REF = prop.getProperty("");
//    public static final String COMPENSATE_EVENT_MULTIPLE_ON_BOUNDARY = prop.getProperty("");
//    public static final String SIGNAL_MISSING_ID = prop.getProperty("");
//    public static final String SIGNAL_MISSING_NAME = prop.getProperty("");
//    public static final String SIGNAL_DUPLICATE_NAME = prop.getProperty("");
//    public static final String SIGNAL_INVALID_SCOPE = prop.getProperty("");
//    public static final String DATA_ASSOCIATION_MISSING_TARGETREF = prop.getProperty("");
//    public static final String DATA_OBJECT_MISSING_NAME = prop.getProperty("");
//    public static final String END_EVENT_CANCEL_ONLY_INSIDE_TRANSACTION = prop.getProperty("");
//    public static final String DI_INVALID_REFERENCE = prop.getProperty("");
//    public static final String DI_DOES_NOT_REFERENCE_FLOWNODE = prop.getProperty("");
//    public static final String DI_DOES_NOT_REFERENCE_SEQ_FLOW = prop.getProperty("");
//    public static final String NO_END_EVENT = prop.getProperty("");
//    public static final String USER_TASK_NO_INCOMING_FLOW = prop.getProperty("");
//    public static final String USER_TASK_NO_OUTCOMING_FLOW = prop.getProperty("");
//    public static final String START_EVENT_NO_OUTCOMING_FLOW = prop.getProperty("");
//    public static final String END_EVENT_NO_INCOMING_FLOW = prop.getProperty("");
//    public static final String USER_TASK_ID_REPEAT = prop.getProperty("");
//    public static final String USER_TASK_USER_SRC_ERR = prop.getProperty("");


    /**
     * 汉化验证结果
     * @param list
     * @return
     */
    public static List<ValidationError> handleValidationErrorToCN(List<ValidationError> list){
        if (list != null && list.size() > 0) {
            for (ValidationError v : list) {
                String problem = v.getProblem();
                if(StringUtils.isNotBlank(problem) && StringUtils.isNotBlank(prop.getProperty(problem))){
                    v.setProblem(prop.getProperty(problem));
                }
            }
        }
        return list;
    }
}
