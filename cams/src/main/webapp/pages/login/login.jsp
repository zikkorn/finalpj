<%--
  Created by IntelliJ IDEA.
  User: tch002
  Date: 2019/2/11
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
   <%-- <link rel="stylesheet" href="../../assets/layui/css/layui.css" media="all">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/layui/css/layui.css" media="all">
    <style>
        #LAY_app,body,html{height:100%}.layui-layout-body{overflow:auto}#LAY-user-login,.layadmin-user-display-show{display:block!important}.layadmin-user-login{position:relative;left:0;top:0;padding:110px 0;min-height:100%;box-sizing:border-box}.layadmin-user-login-main{width:375px;margin:0 auto;box-sizing:border-box}.layadmin-user-login-box{padding:20px}.layadmin-user-login-header{text-align:center}.layadmin-user-login-header h2{margin-bottom:10px;font-weight:300;font-size:30px;color:#000}.layadmin-user-login-header p{font-weight:300;color:#999}.layadmin-user-login-body .layui-form-item{position:relative}.layadmin-user-login-icon{position:absolute;left:1px;top:1px;width:38px;line-height:36px;text-align:center;color:#d2d2d2}.layadmin-user-login-body .layui-form-item .layui-input{padding-left:38px}.layadmin-user-login-codeimg{max-height:38px;width:100%;cursor:pointer;box-sizing:border-box}.layadmin-user-login-other{position:relative;font-size:0;line-height:38px;padding-top:20px}.layadmin-user-login-other>*{display:inline-block;vertical-align:middle;margin-right:10px;font-size:14px}.layadmin-user-login-other .layui-icon{position:relative;top:2px;font-size:26px}.layadmin-user-login-other a:hover{opacity:.8}.layadmin-user-jump-change{float:right}.layadmin-user-login-footer{position:absolute;left:0;bottom:0;width:100%;line-height:30px;padding:20px;text-align:center;box-sizing:border-box;color:rgba(0,0,0,.5)}.layadmin-user-login-footer span{padding:0 5px}.layadmin-user-login-footer a{padding:0 5px;color:rgba(0,0,0,.5)}.layadmin-user-login-footer a:hover{color:rgba(0,0,0,1)}.layadmin-user-login-main[bgimg]{background-color:#fff;box-shadow:0 0 5px rgba(0,0,0,.05)}.ladmin-user-login-theme{position:fixed;bottom:0;left:0;width:100%;text-align:center}.ladmin-user-login-theme ul{display:inline-block;padding:5px;background-color:#fff}.ladmin-user-login-theme ul li{display:inline-block;vertical-align:top;width:64px;height:43px;cursor:pointer;transition:all .3s;-webkit-transition:all .3s;background-color:#f2f2f2}.ladmin-user-login-theme ul li:hover{opacity:.9}@media screen and (max-width:768px){.layadmin-user-login{padding-top:60px}.layadmin-user-login-main{width:300px}.layadmin-user-login-box{padding:10px}}
    </style>
</head>
<body layadmin-themealias="default">

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" >

    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2>入网管理系统</h2>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
                <input type="text" name="username" id="LAY-user-login-username" lay-verify="required" placeholder="用户名" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
                <input type="password" name="password" id="LAY-user-login-password" lay-verify="required" placeholder="密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit="" lay-filter="LAY-user-login-submit">登 入</button>
            </div>
        </div>
    </div>
</div>
<script src="../../assets/layui/layui.js"></script>
<script>
    var ctx = "${pageContext.request.contextPath}/";
    //192.168.0.105
    layui.use(['form', 'layedit', 'laydate', 'table', 'upload'], function () {
        var $ = layui.jquery;
        var form = layui.form, laydate = layui.laydate, upload = layui.upload;
        var postData={'userName':'admin','userPwd':'admin'};
        form.on('submit(LAY-user-login-submit)', function (data) {
         //   postData.userName=data.field.username;
          //  postData.userPwd=data.field.password;
            $.ajax({
                url: ctx + "user/login",
                data: postData,
                type: 'post',
                dataType: 'json',
                async: false,
                success: function (resp) {

                }, error: function () {

                }
            })

        })
    });
</script>
</body>
</html>
