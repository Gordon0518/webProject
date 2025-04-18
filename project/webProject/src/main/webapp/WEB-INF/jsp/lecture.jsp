<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lecture Material - ${lectureTitle}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; }
    .lecture-header { margin-bottom: 30px; }
    .section-title { margin-top: 20px; margin-bottom: 10px; }
    .comment { margin-bottom: 10px; }
  </style>
</head>
<body>
<div class="container">
  <h1 class="lecture-header">${lectureTitle}</h1>
  <h3 class="section-title">Lecture Notes</h3>
  <c:if test="${empty notes}">
    <p>No notes available.</p>
  </c:if>
  <c:if test="${not empty notes}">
    <ul class="list-group">
      <c:forEach var="note" items="${notes}">
        <li class="list-group-item">
          <a href="${note.fileUrl}" download="${note.fileName}">${note.fileName}</a>
        </li>
      </c:forEach>
    </ul>
  </c:if>
  <h3 class="section-title">Comments</h3>
  <c:if test="${empty comments}">
    <p>No comments available.</p>
  </c:if>
  <c:if test="${not empty comments}">
    <div>
      <c:forEach var="comment" items="${comments}">
        <div class="comment">
          <strong>${comment.author.username}:</strong>
          <p>${comment.content}</p>
        </div>
      </c:forEach>
    </div>
  </c:if>
  <sec:authorize access="hasRole('STUDENT')">
    <c:if test="${not empty lectureId}">
      <form action="${pageContext.request.contextPath}/lecture/${lectureId}/comment" method="post" class="mt-3">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="mb-3">
          <label for="content" class="form-label">Add Comment</label>
          <textarea class="form-control" id="content" name="content" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit Comment</button>
      </form>
    </c:if>
    <c:if test="${empty lectureId}">
      <p class="text-danger">Error: Lecture ID is missing. Cannot add comments.</p>
    </c:if>
  </sec:authorize>
  <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-3">Back to Course</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>