<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../../assets/layui/css/layui.css" media="all">
    <style>
        .layui-form-label{
            width: 95px;
        }
    </style>
</head>
<body style="background: #fff">
<div class="layui-card-header">认证结果查询</div>
<br>
<div class="layui-row layui-col-space10">
    <div class="layui-col-md3">
        <label class="layui-form-label">系统化名称：</label>
        <div class="layui-col-md6">
            <input type="text" name="title"  lay-verify="required|systemName" autocomplete="off" placeholder="请输入系统化名称" class="layui-input">
        </div>
    </div>
    <div class="layui-col-md3">
        <label class="layui-form-label">研制单位：</label>
        <div class="layui-col-md6">
            <input type="text" name="title"  lay-verify="required|systemName" autocomplete="off" placeholder="请输入研制单位" class="layui-input">
        </div>
    </div>
    <div class="layui-col-md6">
        <label class="layui-form-label">管理单位：</label>
        <div class="layui-col-md3">
            <input type="text" name="title"  lay-verify="required|systemName" autocomplete="off" placeholder="请输入管理单位" class="layui-input">
        </div>
        <div class="layui-col-md2">&nbsp;</div>
        <button class="layui-btn layui-btn-sm" type="button"  lay-submit  lay-filter="component-form-demo1"  >
            查询
        </button>
    </div>
</div>
<%--
<table class="layui-hide" id="demo" lay-filter="test"></table>
--%>
<table class="layui-table">
    <colgroup>
        <col width="150">
        <col width="200">
        <col>
    </colgroup>
    <thead>
    <tr>
        <th>序号</th>
        <th>系统名称</th>
        <th>研制单位</th>
        <th>管理单位</th>
        <th>业务类型</th>
        <th>申请时间</th>
        <th>审核时间</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>1</td>
        <td>xxx系统</td>
        <td>xxx单位</td>
        <td>xxx单位</td>
        <td>xxx业务</td>
        <td>2017-12-13</td>
        <td>2017-12-13</td>
        <td>已审核</td>
        <td>
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-primary layui-btn-sm">入网结论查看</button>
                <button class="layui-btn layui-btn-sm layui-btn-normal">测试报告查询</button>
                <button class="layui-btn layui-btn-sm layui-btn-normal">测试报告下载</button>
            </div>
        </td>
    </tr>
    <tr>
        <td>2</td>
        <td>xxx系统</td>
        <td>xxx单位</td>
        <td>xxx单位</td>
        <td>xxx业务</td>
        <td>2017-12-13</td>
        <td>2017-12-13</td>
        <td>已审核</td>
        <td>
            <div class="layui-btn-container">
                <button class="layui-btn layui-btn-primary layui-btn-sm">入网结论查看</button>
                <button class="layui-btn layui-btn-sm layui-btn-normal">测试报告查询</button>
                <button class="layui-btn layui-btn-sm layui-btn-normal">测试报告下载</button>
                <%--<button class="layui-btn layui-btn-sm layui-btn-danger">删除</button>--%>
            </div>
        </td>
    </tr>
    </tbody>
</table>
<div id="demo7"></div>
<script src="../../assets/layui/layui.js"></script>
<script>
    layui.use(['table','laypage'], function(){
        var laypage = layui.laypage //分页
        //,table = layui.table //表格
        /* table.render({
             elem: '#demo'
             ,height: 420
            /// ,url: '/demo/table/user/' //数据接口
             ,title: '用户表'
             ,page: true //开启分页
             //,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
             ,totalRow: true //开启合计行
             ,cols: [[ //表头
                 {type: 'checkbox', fixed: 'left'}
                 ,{field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', totalRowText: '合计：'}
                 ,{field: 'username', title: '用户名', width:80}
                 ,{field: 'experience', title: '积分', width: 90, sort: true, totalRow: true}
                 ,{field: 'sex', title: '性别', width:80, sort: true}
                 ,{field: 'score', title: '评分', width: 80, sort: true, totalRow: true}
                 ,{field: 'city', title: '城市', width:150}
                 ,{field: 'sign', title: '签名', width: 200}
                 ,{field: 'classify', title: '职业', width: 100}
                 ,{field: 'wealth', title: '财富', width: 135, sort: true, totalRow: true}
                 ,{fixed: 'right', width: 165, align:'center', toolbar: '#barDemo'}
             ]]
         });*/
        //执行一个laypage实例
        laypage.render({
            elem: 'demo7'
            ,count: 100
            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
            ,jump: function(obj){
                console.log(obj)
            }
        });
    });
</script>
</body>
</html>