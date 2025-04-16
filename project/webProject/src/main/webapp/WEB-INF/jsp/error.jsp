<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Error</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; }
    .error-container { max-width: 600px; margin: auto; text-align: center; }
  </style>
</head>
<body>
<div class="container error-container">
  <h1 class="mb-4">Error</h1>
  <div class="alert alert-danger">
    <c:choose>
      <c:when test="${not empty error}">
        ${error}
      </c:when>
      <c:otherwise>
        An unexpected error occurred.
      </c:otherwise>
    </c:choose>
  </div>
  <p><a href="${pageContext.request.contextPath}/" class="btn btn-primary">Return to Home</a></p>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>