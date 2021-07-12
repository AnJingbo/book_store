<%--
  Created by IntelliJ IDEA.
  User: l
  Date: 2021/6/20
  Time: 16:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="page_nav">
    <%--只有当前页码大于1的时候，才会显示首页和上一页--%>
    <c:if test="${requestScope.page.pageNo > 1}">
        <a href="${requestScope.page.url}&pageNo=1">首页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo-1}">上一页</a>
    </c:if>

    <%-- 分页模块中，每页显示 5 个页码，1 2 【3】 4 5，并且页码可以点击跳转 --%>
    <c:choose>
        <%-- 总页码小于等于 5 的情况，页码的范围是：1 ~ 总页码 --%>
        <c:when test="${requestScope.page.pageTotal <= 5}">
            <c:forEach begin="1" end="${requestScope.page.pageTotal}" var="i">
                <c:if test="${requestScope.page.pageNo == i}">
                    【<a>${i}</a>】
                </c:if>
                <c:if test="${requestScope.page.pageNo != i}">
                    <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
                </c:if>
            </c:forEach>
        </c:when>


        <%-- 总页码大于 5 的情况 --%>
        <c:when test="${requestScope.page.pageTotal > 5}">
            <c:choose>
                <%-- 情况1：当前页码为前面 3 个，页码的范围是：1 ~ 5 --%>
                <c:when test="${requestScope.page.pageNo <= 3}">
                    <c:forEach begin="1" end="5" var="i">
                        <c:if test="${requestScope.page.pageNo == i}">
                            【<a>${i}</a>】
                        </c:if>
                        <c:if test="${requestScope.page.pageNo != i}">
                            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
                        </c:if>
                    </c:forEach>
                </c:when>
                <%-- 情况2：当前页码为最后 3 个，页码的范围是：总页码-4 ~ 总页码 --%>
                <c:when test="${requestScope.page.pageNo > requestScope.page.pageTotal - 3}">
                    <c:forEach begin="${requestScope.page.pageTotal - 4}" end="${requestScope.page.pageTotal}" var="i">
                        <c:if test="${requestScope.page.pageNo == i}">
                            【<a>${i}</a>】
                        </c:if>
                        <c:if test="${requestScope.page.pageNo != i}">
                            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
                        </c:if>
                    </c:forEach>
                </c:when>
                <%-- 情况3：当前页码为中间的，页码的范围是：当前页码-2 ~ 当前页码+2 --%>
                <c:otherwise>
                    <c:forEach begin="${requestScope.page.pageNo - 2}" end="${requestScope.page.pageNo + 2}" var="i">
                        <c:if test="${requestScope.page.pageNo == i}">
                            【<a>${i}</a>】
                        </c:if>
                        <c:if test="${requestScope.page.pageNo != i}">
                            <a href="${requestScope.page.url}&pageNo=${i}">${i}</a>
                        </c:if>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>


    <%--只有当前页码小于最后一页的时候，才会显示下一页和末页--%>
    <c:if test="${requestScope.page.pageNo < requestScope.page.pageTotal}">
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageNo+1}">下一页</a>
        <a href="${requestScope.page.url}&pageNo=${requestScope.page.pageTotal}">末页</a>
    </c:if>
    共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录
    到第<input value="${param.pageNo}" name="pn" id="pn_input"/>页
    <input id="searchPageBtn" type="button" value="确定">

    <script type="text/javascript">
        $(function (){
            // 调到指定页码
            $("#searchPageBtn").click(function(){
                var pageNo = $("#pn_input").val();
                var pageTotal = ${requestScope.page.pageTotal};
                if(pageNo < 1){
                    pageNo = 1;
                }
                if(pageNo > pageTotal){
                    pageNo = pageTotal;
                }
                // JavaScript 语言提供了一个 location 地址栏对象，他有一个属性叫 href，他可以获取浏览器地址栏中的地址。href 属性可读、可写
                location.href = "http://localhost:8080/book/${requestScope.page.url}&pageNo=" + pageNo;
            });
        });
    </script>
</div>

