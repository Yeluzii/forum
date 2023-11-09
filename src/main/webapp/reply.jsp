<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Java Web 开发者论坛</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/jquery.min.js"></script>
</head>

<body>
<div class="container">
    <ul class="nav nav-tabs">
        <c:forEach items="${categoryList}" var="category">
            <li>
                <a href="${pageContext.request.contextPath}/topic?method=list&c_id=${category.id}">${category.name}</a>
            </li>
        </c:forEach>
    </ul>
</div>

<div style="margin-top: 10px">
    <form class="form-horizontal" role="form"
          action="${pageContext.request.contextPath}/topic?method=replyByTopicId&topic_id=${param.topic_id}"
          method="post">
        <div class="form-group">
            <label class="col-sm-2 control-label">内容</label>
            <div class="col-lg-8">
                <label>
                    <textarea class="form-control" name="content" placeholder="请输入盖楼内容" rows="10" cols="100"></textarea>
                </label>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">回复</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>