<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Poll - ${pollQuestion}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; }
    .poll-header { margin-bottom: 30px; }
    .section-title { margin-top: 20px; margin-bottom: 10px; }
    .comment { margin-bottom: 10px; }
  </style>
</head>
<body>
<div class="container">
  <h1 class="poll-header">${pollQuestion}</h1>
  <h3 class="section-title">Options</h3>
  <c:if test="${empty options}">
    <p>No options available.</p>
  </c:if>
  <c:if test="${not empty options}">
    <ul class="list-group">
      <c:forEach var="option" items="${options}">
        <li class="list-group-item">
            ${option.optionText} (Votes: ${option.voteCount})
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
          <strong>${comment.author}:</strong>
          <p>${comment.content}</p>
        </div>
      </c:forEach>
    </div>
  </c:if>
  <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-3">Back to Course</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>