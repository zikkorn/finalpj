<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/4
  Time: 9:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>Title</title>
    <link href="./css/jquery-confirm.css" rel="stylesheet" type="text/css">
    <script src="../editor-app/libs/jquery_1.11.0/jquery.min.js"></script>
    <script src="./js/jquery-confirm.js"></script>
</head>
<style type="text/css">
    #content {
        width: 100%;
        height: 100%;
    }

    #tab_bar {
        width: 100%;
        height: 20px;
        float: left;
    }
    #tab_bar ul {
        padding: 0px;
        margin: 0px;
        height: 20px;
        text-align: center;
        border-bottom: 1px solid #5bc0de;
    }

    #tab_bar li {
        list-style-type: none;
        float: left;
        width: 133.3px;
        height: 20px;
        cursor: pointer;
    }

    .tab_css {
        width: 100%;
        height: 100%;
        display: none;
        float: left;
    }

    .selected{
        background-color: #28a4c9;
    }

    .user_table  {
        width:100%;
        margin:15px 0;
        border:1px;
        cellspacing:0;
    }
    .user_table th {
        background-color:#36a7c4;
        color:#000000
    }
    .user_table,.user_table th,.user_table td {
        font-size:0.95em;
        text-align:center;
        padding:4px;
        border-collapse:collapse;
    }
    .user_table th,.user_table td {
        border: 1px solid #c1e9fe;
        border-width:1px;
    }
    .user_table tr {
        border: 1px solid #c1e9fe;
    }
    .user_table input{
        border: 0px;
        outline:none;
        width:100%;
    }

    .icon-send {
        background: url(./img/send.png) no-repeat;
    }
    .icon-pstart {
        background: url(./img/start.png) no-repeat;
    }
    .icon-back {
        background: url(./img/goback.png) no-repeat;
    }
    .icon-revoke {
        background: url(./img/revoke.png) no-repeat;
    }
    .icon-claim {
        background: url(./img/claim.png) no-repeat;
    }
    .icon-noclaim {
        background: url(./img/noclaim.png) no-repeat;
    }
    .title{
        display: block;
        font-size: 1.5em;
        -webkit-margin-before: 0.83em;
        -webkit-margin-after: 0.83em;
        -webkit-margin-start: 0px;
        -webkit-margin-end: 0px;
        font-weight: bold;
    }

</style>
<body>
<div id="content">
    <div id="tab_bar">
        <ul>
            <li id="tab1" class="selected">
                我的任务
            </li>
            <li id="tab2">
                启动流程
            </li>
        </ul>
    </div>
    <div id="tab-content">
        <div class="tab_css" id="tab1_content" style="display: block">
            <span class="title">待处理任务</span>
            <table class="user_table">
                <tr>
                    <th>操作</th>
                    <th>工作项名称</th>
                    <th>业务流程名称</th>
                    <th>当前状态</th>
                    <th>启动时间</th>
                    <th>流程图查看</th>
                </tr>
                <c:forEach items="${taskList}" var="task">
                    <tr>
                        <td>
                            <span onclick="goSend(this)" data-taskid="${task.taskId}" margin-left="30%" style="cursor:pointer" class="icon-send" title="发送">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            &nbsp;&nbsp;
                            <span onclick="goback(this)" data-taskid="${task.taskId}" margin-left="30%" style="cursor:pointer" class="icon-back" title="回退">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            &nbsp;&nbsp;
                            <span onclick="goDeleteProcess(this)" data-prciid="${task.procInstId}" margin-left="30%" style="cursor:pointer" class="icon-revoke" title="中止流程">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            <c:if test="${empty task.assignee}">
                                &nbsp;&nbsp;
                                <span onclick="goClaim(this,1)" data-taskid="${task.procInstId}" margin-left="30%" style="cursor:pointer" class="icon-claim" title="签收任务">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            </c:if>
                            <c:if test="${not empty task.assignee}">
                                &nbsp;&nbsp;
                                <span onclick="goClaim(this,2)" data-taskid="${task.procInstId}" margin-left="30%" style="cursor:pointer" class="icon-noclaim" title="取消签收任务">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                            </c:if>
                        </td>
                        <td>${task.activityName}</td>
                        <td>${task.modelName}</td>
                        <td>${task.state}</td>
                        <td><fmt:formatDate type="both" value="${task.startTime}" /></td>
                        <td>
                                <a href="<%=basePath%>client/showCurrentImg/${task.procInstId}" target="_blank">流程图</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div class="tab_css" id="tab2_content">
            <span class="title">可启动流程</span>
            <table class="user_table">
                <tr>
                    <th>操作</th>
                    <th>流程模版名称</th>
                    <th>流程版本</th>
                    <th>最后修改时间</th>
                </tr>
                <c:forEach items="${proceList}" var="proc">
                    <tr>
                        <td onclick="pstart(this)" data-processKey="${proc.key}">
                            <span margin-left="30%" style="cursor:pointer" class="icon-pstart" title="启动流程">&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </td>
                        <td>${proc.name}</td>
                        <td>${proc.version}</td>
                        <td><fmt:formatDate type="both" value="${proc.updateTime}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    $('#tab_bar ul li').click(function () {
        $(this).siblings().removeClass('selected');
        $(this).addClass('selected');
        var id = $(this).attr('id') + '_content';
        $('#' + id).siblings().hide();
        $('#' + id).show();
    });
    
    function goSend(obj) {
        var _this = $(obj);
        $.confirm({
            title: '确认',
            content: '确认发送任务?',
            type: 'green',
            icon: 'glyphicon glyphicon-question-sign',
            buttons: {
                ok: {
                    text: '确认',
                    btnClass: 'btn-primary',
                    action: function() {
                        send(_this.attr("data-taskid"));
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary'
                }
            }
        });
    }
    function send(taskId) {
        $.ajax({
            url: '<%=basePath%>client/send/' + taskId,
            data:{
                userId:'${userId}',
                result:'同意',
                resultDesc:'通过'
            },
            type: 'POST',
            dataType: 'json',
            success:function (data) {
                if(data.code == 0){
                    location.reload();
                }
            }
        });
    }

    function pstart(obj) {
        var _this = $(obj);
        $.confirm({
            title: '确认',
            content: '确认启动流程?',
            type: 'green',
            icon: 'glyphicon glyphicon-question-sign',
            buttons: {
                ok: {
                    text: '确认',
                    btnClass: 'btn-primary',
                    action: function() {
                        start(_this.attr('data-processkey'));
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary'
                }
            }
        });
    }
    function start(processKey) {
        $.ajax({
            url: '<%=basePath%>client/start/' + processKey,
            type: 'POST',
            dataType: 'json',
            success:function (data) {
                if(data.code == 0){
                    location.reload();
                }
            }
        });
    }

    function goback(obj) {
        var _this = $(obj);
        $.confirm({
            title: '确认',
            content: '确认回退任务?',
            type: 'green',
            icon: 'glyphicon glyphicon-question-sign',
            buttons: {
                ok: {
                    text: '确认',
                    btnClass: 'btn-primary',
                    action: function() {
                        back(_this.attr("data-taskid"));
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary'
                }
            }
        });
    }
    function back(taskId) {
        $.ajax({
            url: '<%=basePath%>client/back/' + taskId,
            data:{
                userId:'${userId}',
                result:'不同意',
                resultDesc:'不通过'
            },
            type: 'POST',
            dataType: 'json',
            success:function (data) {
                if(data.code == 0){
                    location.reload();
                }
            }
        });
    }

    function goDeleteProcess(obj) {
        var _this = $(obj);
        $.confirm({
            title: '确认',
            content: '确认撤销流程?',
            type: 'green',
            icon: 'glyphicon glyphicon-question-sign',
            buttons: {
                ok: {
                    text: '确认',
                    btnClass: 'btn-primary',
                    action: function() {
                        deleteProcess(_this.attr("data-prciid"));
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary'
                }
            }
        });
    }
    function deleteProcess(procInstId) {
        $.ajax({
            url: '<%=basePath%>client/deleteProcess/' + procInstId,
            type: 'POST',
            dataType: 'json',
            success:function (data) {
                if(data.code == 0){
                    location.reload();
                }
            }
        });
    }

    function goClaim(obj,type) {
        var _this = $(obj);
        var msg = '确认签收任务?';
        if(type==2){
            msg = '确认取消签收任务?';
        }
        $.confirm({
            title: '确认',
            content: msg,
            type: 'green',
            icon: 'glyphicon glyphicon-question-sign',
            buttons: {
                ok: {
                    text: '确认',
                    btnClass: 'btn-primary',
                    action: function() {
                        claim(_this.attr("data-taskid"),type);
                    }
                },
                cancel: {
                    text: '取消',
                    btnClass: 'btn-primary'
                }
            }
        });
    }
    function claim(taskId,type) {
        var url = '<%=basePath%>client/claim/' + taskId + "/${userId}";
        if(type==2){
            url = '<%=basePath%>client/unclaim/' + taskId;
        }
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            success:function (data) {
                if(data.code == 0){
                    location.reload();
                }
            }
        });
    }
</script>
</html>
