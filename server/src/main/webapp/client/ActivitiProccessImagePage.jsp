<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>流程状态</title>
</head>
<body>
	<div>
		<img src="<%=basePath%>user/getActivitiProccessImage?pProcessInstanceId=${processInstanceId}">
	</div>
</body>
</html>