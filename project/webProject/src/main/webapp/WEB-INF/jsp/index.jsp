<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Course Index</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { padding: 20px; }
        .course-header { margin-bottom: 30px; }
        .section-title { margin-top: 20px; margin-bottom: 10px; }
        .list-group-item a { text-decoration: none; }
        .navbar { margin-bottom: 20px; }
        .welcome-message { margin-bottom: 20px; }
        .btn-danger { background-color: #dc3545; border-color: #dc3545; }
        .btn-danger:hover { background-color: #bb2d3b; border-color: #b02a37; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Course Management</a>
        <div class="navbar-nav ms-auto">
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal == null}">
                    <a class="nav-link" href="${pageContext.request.contextPath}/login">Login</a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/register">Register</a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link" href="${pageContext.request.contextPath}/profile">Profile</a>
                    <sec:authorize access="hasRole('ROLE_TEACHER')">
                        <a class="nav-link" href="${pageContext.request.contextPath}/voting-history">Voting History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/comment-history">Comment History</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/users">Users</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/lecture/add">Add Lecture</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/poll/add">Add Poll</a>
                    </sec:authorize>
                    <span class="nav-link">Hello, ${pageContext.request.userPrincipal.name}</span>
                    <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="nav-link btn btn-link">Logout</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
<div class="container">
    <c:if test="${pageContext.request.userPrincipal != null}">
        <div class="welcome-message">
            <h4>Welcome, ${pageContext.request.userPrincipal.name}</h4>
        </div>
    </c:if>
    <h1 class="course-header">${courseName}</h1>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <h3 class="section-title">Lectures</h3>
    <c:if test="${empty lectures}">
        <p>No lectures available.</p>
    </c:if>
    <c:if test="${not empty lectures}">
        <ul class="list-group">
            <c:forEach var="lecture" items="${lectures}">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <sec:authorize access="isAuthenticated()">
                        <a href="${pageContext.request.contextPath}/lecture/${lecture.id}">${lecture.title}</a>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        ${lecture.title} (Login required)
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_TEACHER')">
                        <form action="${pageContext.request.contextPath}/lecture/${lecture.id}/delete" method="post" style="display:inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete ${lecture.title}?')">Delete</button>
                        </form>
                    </sec:authorize>
                </li>
            </c:forEach>
        </ul>
    </c:if>
    <h3 class="section-title">Multiple-Choice Polls</h3>
    <c:if test="${empty polls}">
        <p>No polls available.</p>
    </c:if>
    <c:if test="${not empty polls}">
        <ul class="list-group">
            <c:forEach var="poll" items="${polls}">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <sec:authorize access="isAuthenticated()">
                        <a href="${pageContext.request.contextPath}/poll/${poll.id}">${poll.question}</a>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        ${poll.question} (Login required)
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_TEACHER')">
                        <form action="${pageContext.request.contextPath}/poll/${poll.id}/delete" method="post" style="display:inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete this poll?')">Delete</button>
                        </form>
                    </sec:authorize>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>