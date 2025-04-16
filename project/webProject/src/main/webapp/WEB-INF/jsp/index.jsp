<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
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
    </style>
</head>
<body>
<div class="container">
    <h1 class="course-header">${courseName}</h1>
    <h3 class="section-title">Lectures</h3>
    <c:if test="${empty lectures}">
        <p>No lectures available.</p>
    </c:if>
    <c:if test="${not empty lectures}">
        <ul class="list-group">
            <c:forEach var="lecture" items="${lectures}">
                <li class="list-group-item">
                    <a href="/webProject/lecture/${lecture.id}">${lecture.title}</a>
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
                <li class="list-group-item">${poll.question}</li>
            </c:forEach>
        </ul>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>