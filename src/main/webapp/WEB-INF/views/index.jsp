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