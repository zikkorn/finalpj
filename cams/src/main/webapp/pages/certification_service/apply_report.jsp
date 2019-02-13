<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>入网申请填报</title>
    <link rel="stylesheet" href="../../assets/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="../../assets/css/common.css" media="all">
    <style>
        .layui-form-label {
            width: 130px;
        }
    </style>
</head>
<body>
<div class="layui-fluid ">
    <div class="layui-card ">
        <div class="layui-card-header">认证申请填报</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" action="" lay-filter="component-form-group">
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">系统化名称(必填)</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title"  lay-verify="required|systemName" autocomplete="off" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">业务类型(必填)</label>
                    <div class="layui-col-md6">
                        <select name="interest" lay-verify="required|yewu">
                            <option value="" selected="">请选择业务类型</option>
                            <option value="0">业务1</option>
                            <option value="1" >业务2</option>
                            <option value="2">业务3</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">系统管理单位(必填)</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="required|system" autocomplete="off" placeholder="请输入系统管理单位" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">研制单位(必填)</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="required|company" autocomplete="off" placeholder="请输入研制单位" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">联系人(必填)</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="required|contact" autocomplete="off" placeholder="请输入联系人" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">手机号(必填)</label>
                    <div class="layui-col-md6">
                        <input type="tel"  placeholder="请输入手机号" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input">                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">电子邮箱(必填)</label>
                    <div class="layui-col-md6">
                        <input type="text" placeholder="请输入电子邮箱" name="email" lay-verify="required|email" autocomplete="off" class="layui-input">                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">申请日期</label>
                    <div class="layui-col-md6">
                        <input type="text" name="date"   id="date" lay-verify="required|date" placeholder="请选择申请日期" autocomplete="off" class="layui-input" lay-key="1">                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">电子附件</label>
                    <div class="layui-col-md6 layui-upload">
                        <span></span>
                        <button class="layui-btn layui-btn-normal " type="button" id="fileUpload">选择文件</button>
                        <%-- <input id="excelFile" type="file" name="excelFile" lay-type="file"  class="layui-upload-file" >--%>
                        <%--<input type="text" name="title"   autocomplete="off" placeholder="请选择电子附件" class="layui-input">--%>
                    </div>
                </div>
                <div class="layui-form-item layui-form-text  layui-row">
                    <label class="layui-form-label">系统描述</label>
                    <div class=" layui-col-md6">
                        <textarea name="text" placeholder="请输入内容" lay-verify="systemIntro" class="layui-textarea"></textarea>
                    </div>
                </div>
                <div class="layui-form-item layui-layout-admin">
                    <div class="layui-input-block">
                        <div style="text-align: center">
                            <button class="layui-btn" type="button" lay-submit lay-filter="component-form-demo1" id="submit">
                                立即提交
                            </button>
                            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <br>
            </form>
        </div>
    </div>
</div>
<%--<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
    <legend>常规使用：普通图片上传</legend>
</fieldset>
<div class="layui-upload">
    <button type="button" class="layui-btn" id="test1">上传图片</button>
    <div class="layui-upload-list">
        <img class="layui-upload-img" id="demo1">
        <p id="demoText"></p>
    </div>
</div>--%>
<script src="../../assets/layui/layui.js"></script>
<script>
    layui.use(['form', 'layedit', 'laydate', 'table', 'upload'], function () {
        var $ = layui.jquery;
        var form = layui.form, laydate = layui.laydate, upload = layui.upload;
        laydate.render({
            elem: '#date'
        });
        upload.render({
            elem: '#fileUpload'
            , url: '/upload/'
            ,accept:'file'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    console.log(result)
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg('上传失败');
                }
                //上传成功
            }
            , error: function () {
                //演示失败状态，并实现重传
            }
        });
        form.on('submit(component-form-demo1)', function (data) {
            console.log(data);
            /*if (!submiting) {
                var userName = $("input[name='userinfo.username']").val();
                var phone = $("input[name='userinfo.phone']").val();
                if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(userName)) {
                    layer.msg('用户名不能有特殊字符');
                    return false;
                }
                if (!/^[\S]{1,64}$/.test(userName)) {
                    layer.msg('用户名不能超过64个字符');
                    return false;
                }
                if (!new RegExp(/^((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/).test(phone)) {
                    layer.msg("手机号不合法");
                    return false;
                }
                var passwd = $("input[name='userinfo.passwd']").val();
                if (!/^[\S]{6,15}$/.test(passwd)) {
                    layer.msg('密码必须在6到15位');
                    return false;
                }
                var leadindustry = $("#leadindustry").val();
                if (leadindustry != null && leadindustry != "" && leadindustry != "undefined" && leadindustry != "null") {
                    var length = leadindustry.split(";").length;
                    if (length > 5) {
                        layer.msg("主导产业不可以超过5个");
                        return false;
                    }
                }
                var data = data.field;
                //判断机构选择传递过来的机构名称和机构名称输入框的值是否相等
                if (selectOrgName != $("#orgName").val()) {
                    //新增机构
                    //机构id置为空
                    data["orginfo.orgid"] = "";
                }
                //省市区
                var allcity = [];
                $('.layui-form-select').each(function (element, index) {
                    allcity.push($(this).find('input').val())
                })
                //data.field.extras.address=allcity[0] + ',' + allcity[1] + ',' + allcity[2] + ',' + $('#district').val();
                //data.field.extras.companies=JSON.stringify(companyextras);
                data["extras.address"] = allcity[0] + ',' + allcity[1] + ',' + allcity[2] + ',' + $('#district').val();
                data["extras.companies"] = JSON.stringify(companyextras);
                submiting = true;
                data.filePath=filesPath;
                $.ajax({
                    url: ctx + "actions/sysm/submitparkgoverment.action",
                    data: data,
                    type: 'post',
                    dataType: 'json',
                    async: false,
                    success: function (resp) {
                        submiting = false;
                        if (resp.hasOwnProperty("success")) {
                            layer.alert("数据已提交", function () {
                                window.location.reload();
                            });
                        } else {
                            layer.alert(resp.errorMsg);
                        }
                    }, error: function () {
                        submiting = false;
                    }
                })
                return false;
            }*/
        })
        //但是，如果你的HTML是动态生成的，自动渲染就会失效
        //因此你需要在相应的地方，执行下述方法来手动渲染，跟这类似的还有 element.init();
        //  form.render();
    });
</script>
</body>
</html>

