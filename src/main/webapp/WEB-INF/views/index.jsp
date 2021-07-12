<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="layout/header.jsp" %>
<div class="container">

    <c:forEach var="board" items="${boards.content}">
        <div class="card m-2">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                    <%--getter이 있어야 함--%>
                <a href="/board/${board.id}" class="btn btn-primary">상세 보기</a>
            </div>
        </div>
    </c:forEach>
    <%--    <table class="table table-horizontal table-bordered">--%>
    <%--        <thead class="thead-strong">--%>
    <%--        <tr>--%>
    <%--            <th width="10%">번호</th>--%>
    <%--            <th width="50%">제목</th>--%>
    <%--            <th width="20%">작성자</th>--%>
    <%--            <th width="20%">최종수정일</th>--%>
    <%--        </tr>--%>
    <%--        </thead>--%>
    <%--        <tbody id="tbody">--%>
    <%--        <c:forEach var="board" items="${boards.content}">--%>
    <%--            <tr>--%>
    <%--                <td>${board.id}</td>--%>
    <%--                <td><a href="/board/${board.id}">${board.title}</a></td>--%>
    <%--                <td>${board.user.username}</td>--%>
    <%--                <td>${board.createDate}</td>--%>
    <%--            </tr>--%>
    <%--        </c:forEach>--%>
    <%--        </tbody>--%>
    <%--    </table>--%>

    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${boards.first}">
                <li class="page-item disabled"><a class="page-link" href="/?page=${boards.number-1}">Previous</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="/?page=${boards.number-1}">Previous</a></li>
            </c:otherwise>
        </c:choose>
        <%--구현할것: 보이는 페이지수  제한--%>
        <c:forEach var="page" begin="1" end="${boards.totalPages}">
            <c:choose>
                <c:when test="${boards.number+1 == page}">
                    <li class="page-item active"><a class="page-link" href="/?page=${page-1}">${page}</a></li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link" href="/?page=${page-1}">${page}</a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${boards.last}">
                <li class="page-item disabled"><a class="page-link" href="/?page=${boards.number+1}">Next</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="/?page=${boards.number+1}">Next</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>


<%@ include file="layout/footer.jsp" %>