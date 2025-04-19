<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Add Lecture Note - ${lectureTitle}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; background-color: #f8f9fa; }
    .add-note-container { max-width: 700px; margin: auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    .btn-primary { background-color: #0d6efd; border-color: #0d6efd; }
    .btn-primary:hover { background-color: #0b5ed7; border-color: #0a58ca; }
    .form-control { margin-bottom: 15px; }
  </style>
</head>
<body>
<div class="container add-note-container">
  <h1 class="mb-4">Add Lecture Note for ${lectureTitle}</h1>

  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
  </c:if>
  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
  </c:if>

  <sec:authorize access="hasRole('ROLE_TEACHER')">
    <form action="${pageContext.request.contextPath}/lecture/${lectureId}/add-note" method="post">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="mb-3">
        <label for="fileName" class="form-label">File Name</label>
        <input type="text" class="form-control" id="fileName" name="fileName" required aria-label="File name"/>
      </div>
      <div class="mb-3">
        <label for="fileUrl" class="form-label">File URL</label>
        <input type="text" class="form-control" id="fileUrl" name="fileUrl" required aria-label="File URL"/>
      </div>
      <button type="submit" class="btn btn-primary">Add Note</button>
    </form>
  </sec:authorize>

  <a href="${pageContext.request.contextPath}/lecture/${lectureId}" class="btn btn-primary mt-4">Back to Lecture</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>