<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SpringMVC 测试页</title>


    <script type="text/javascript" src="/js/jquery.min.js"></script>

    <script>
        $(function () {

            $("#ajaxBtn").bind("click",function () {
                // 发送ajax请求
                $.ajax({
                    url: '/demo/handleAjax',
                    type: 'POST',
                    data: '{"id":"1","name":"李四"}',
                    contentType: 'application/json;charset=utf-8',
                    dataType: 'json',
                    success: function (data) {
                        alert(data.name);
                    }
                })

            })


        })
    </script>


    <style>
        div{
            padding:10px 10px 0 10px;
        }
    </style>
</head>
<body>


<div>
    <h2>SpringMVC对Restful风格url的支持</h2>
    <fieldset>
        <p>测试用例：SpringMVC对Restful风格url的支持</p>

        <a href="/demo/handle/15">rest_get测试</a>


        <form method="post" action="/demo/handle">
            <input type="text" name="username"/>
            <input type="submit" value="提交rest_post请求"/>
        </form>


        <form method="post" action="/demo/handle/15/lisi">
            <input type="hidden" name="_method" value="put"/>
            <input type="submit" value="提交rest_put请求"/>
        </form>


        <form method="post" action="/demo/handle/15">
            <input type="hidden" name="_method" value="delete"/>
            <input type="submit" value="提交rest_delete请求"/>
        </form>
    </fieldset>
</div>



<div>
    <h2>Ajax json交互</h2>
    <fieldset>
        <input type="button" id="ajaxBtn" value="ajax提交"/>
    </fieldset>
</div>


<div>
    <h2>multipart 文件上传</h2>
    <fieldset>
        <%--
            1 method="post"
            2 enctype="multipart/form-data"
            3 type="file"
        --%>
        <form method="post" enctype="multipart/form-data" action="/demo/upload">
            <input type="file" name="uploadFile"/>
            <input type="submit" value="上传"/>
        </form>
    </fieldset>
</div>



</body>
</html>
