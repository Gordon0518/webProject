<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Lecture Material - ${lectureTitle}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; background-color: #f8f9fa; }
    .lecture-container { max-width: 700px; margin: auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    .section-title { margin-top: 20px; margin-bottom: 15px; color: #343a40; }
    .comment { margin-bottom: 15px; padding: 10px; background: #f1f3f5; border-radius: 5px; }
    .btn-primary { background-color: #0d6efd; border-color: #0d6efd; }
    .btn-primary:hover { background-color: #0b5ed7; border-color: #0a58ca; }
    textarea { resize: vertical; }
  </style>
</head>
<body>
<div class="container lecture-container">
  <h1 class="mb-4">${lectureTitle}</h1>

  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
  </c:if>

  <h3 class="section-title">Lecture Notes</h3>
  <c:if test="${empty notes}">
    <p class="text-muted">No notes available.</p>
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
    <p class="text-muted">No comments available.</p>
  </c:if>
  <c:if test="${not empty comments}">
    <div>
      <c:forEach var="comment" items="${comments}">
        <div class="comment">
          <strong><c:out value="${comment.author != null ? comment.author.username : 'Unknown'}"/>:</strong>
          <p><c:out value="${comment.content}"/></p>
        </div>
      </c:forEach>
    </div>
  </c:if>

  <sec:authorize access="hasAnyRole('STUDENT', 'TEACHER')">
    <c:if test="${not empty lectureId}">
      <form action="${pageContext.request.contextPath}/lecture/${lectureId}/comment" method="post" class="mt-4">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="mb-3">
          <label for="content" class="form-label">Add Comment</label>
          <textarea class="form-control" id="content" name="content" rows="4" required aria-label="Comment text"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit Comment</button>
      </form>
    </c:if>
    <c:if test="${empty lectureId}">
      <p class="text-danger">Error: Lecture ID is missing. Cannot add comments.</p>
    </c:if>
  </sec:authorize>

  <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-4">Back to Course</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>