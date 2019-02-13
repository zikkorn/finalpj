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
        <div class="layui-card-header">入网认证审批</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" action="" lay-filter="component-form-group">
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">系统化名称</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title" readonly autocomplete="off" value="系统化名称" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">业务类型</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title" readonly autocomplete="off" value="业务类型一" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">系统管理单位</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title" readonly autocomplete="off" value="系统管理单位" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">研制单位</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title" readonly autocomplete="off" value="研制单位" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">联系人</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title" readonly autocomplete="off" value="联系人" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">手机号</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title" readonly autocomplete="off" value="手机号" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">电子邮箱(必填)</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title" autocomplete="off" value="电子邮箱"  placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">申请日期</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title" readonly value="申请日期" autocomplete="off" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-row">
                    <label class="layui-form-label">电子附件</label>
                    <div class="layui-col-md6">
                        <input type="text" name="title" lay-verify="title"  readonly value="电子附件"  autocomplete="off" placeholder="请输入标题" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item layui-form-text  layui-row">
                    <label class="layui-form-label">系统描述</label>
                    <div class=" layui-col-md6">
                        <textarea name="text" placeholder="请输入内容" readonly value="系统描述"  lay-verify="systemIntro" class="layui-textarea"></textarea>
                    </div>
                </div>
                <div class="layui-form-item layui-layout-admin">
                    <div class="layui-input-block">
                        <div style="text-align: center">
                            <button class="layui-btn" data-method="offset" data-type="auto" id="assest" type="button">
                                审核
                            </button>
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
<div id="modalContent" style="display: none;">
    <div class="layui-fluid">
        <div class="layui-card">
            <div  class="layui-card-body">
                <form class="layui-form" lay-filter="ensureModal">
                    <div class="layui-form-item layui-row">
                        <label class="layui-form-label">审批人</label>
                        <div class="layui-col-md6">
                            <input type="text" name="title" lay-verify="" readonly autocomplete="off" value="张三" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item layui-row">
                        <label class="layui-form-label">审批日期</label>
                        <div class="layui-col-md6">
                            <input type="text" name="date"   id="date" lay-verify="required|date" placeholder="请选择审批日期" autocomplete="off" class="layui-input" lay-key="1">                    </div>
                    </div>
                    <div class="layui-form-item layui-form-text  layui-row">
                        <label class="layui-form-label">审批意见</label>
                        <div class=" layui-col-md6">
                            <textarea name="text" placeholder="请输入审批意见"  lay-verify="systemIntro" class="layui-textarea"></textarea>
                        </div>
                    </div>
                    <div class="layui-form-item layui-form-text  layui-row">
                        <label class="layui-form-label">审批意见</label>
                        <div class="layui-input-block">
                            <input type="radio" name="agree" value="通过" title="通过" checked="">
                            <input type="radio" name="agree" value="不通过" title="不通过">
                        </div>
                    </div>
                    <div class="layui-form-item layui-layout-admin">
                        <div class="layui-input-block">
                            <div style="text-align: center">
                                <button type="reset" class="layui-btn layui-btn-primary canclebtn" >取消</button>
                                <button class="layui-btn" type="button" lay-submit  lay-filter="component-form-demo1"  id="submit">
                                    确定
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="../../assets/layui/layui.js"></script>
<script>
    layui.use(['form', 'layedit', 'laydate', 'table', 'layer'],function () {
        var $ = layui.jquery;
        var form = layui.form, laydate = layui.laydate, layer = layui.layer;
        laydate.render({
            elem: '#date'
        });
        var active={
            offset : function(othis){
                var type = othis.data('type')
                    ,text = othis.text();
                layer.open({
                    type: 1
                    ,title: '认证申请审核'
                    ,area: ['800px', '600px']
                    ,offset: 'auto' //具体配置参考：http://www.layui.com/doc/modules/layer.html#offset
                    ,id: 'layerDemo'+type //防止重复弹出
                    ,content: $('#modalContent')
                    ,btnAlign: 'c' //按钮居中
                    ,shade:  [0.5,'#000'] //不显示遮罩
                    ,yes: function(){
                        layer.closeAll();
                    }
                });
            }
        };
        $('#assest').on('click', function(){
            var othis = $(this), method = othis.data('method');
            active[method] ? active[method].call(this, othis) : '';
        });
        $('.canclebtn').on('click',function () {
            layer.closeAll();
        });
    })
</script>
</body>
</html>