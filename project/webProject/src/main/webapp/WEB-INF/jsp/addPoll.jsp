<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Add Poll</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; background-color: #f8f9fa; }
    .add-poll-container { max-width: 700px; margin: auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    .btn-primary { background-color: #0d6efd; border-color: #0d6efd; }
    .btn-primary:hover { background-color: #0b5ed7; border-color: #0a58ca; }
    .form-control { margin-bottom: 15px; }
    .option-field { margin-bottom: 10px; }
  </style>
</head>
<body>
<div class="container add-poll-container">
  <h1 class="mb-4">Add New Poll</h1>

  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
  </c:if>
  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
  </c:if>

  <sec:authorize access="hasRole('ROLE_TEACHER')">
    <form action="${pageContext.request.contextPath}/poll/add" method="post">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="mb-3">
        <label for="question" class="form-label">Poll Question</label>
        <input type="text" class="form-control" id="question" name="question" required aria-label="Poll question"/>
      </div>
      <div id="options-container">
        <div class="option-field">
          <label for="option1" class="form-label">Option 1</label>
          <input type="text" class="form-control" id="option1" name="options" required aria-label="Option 1"/>
        </div>
        <div class="option-field">
          <label for="option2" class="form-label">Option 2</label>
          <input type="text" class="form-control" id="option2" name="options" required aria-label="Option 2"/>
        </div>
        <div class="option-field">
          <label for="option3" class="form-label">Option 3</label>
          <input type="text" class="form-control" id="option3" name="options" required aria-label="Option 3"/>
        </div>
        <div class="option-field">
          <label for="option4" class="form-label">Option 4</label>
          <input type="text" class="form-control" id="option4" name="options" required aria-label="Option 4"/>
        </div>
      </div>
      <input type="hidden" name="courseId" value="1"/>
      <button type="submit" class="btn btn-primary">Add Poll</button>
    </form>
  </sec:authorize>

  <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-4">Back to Course</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>