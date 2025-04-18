<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <title>Lecture - ${lecture.title}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; background-color: #f8f9fa; }
    .lecture-container { max-width: 700px; margin: auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    .section-title { margin-top: 20px; margin-bottom: 15px; color: #343a40; }
    .note-item, .comment { margin-bottom: 15px; padding: 10px; background: #f1f3f5; border-radius: 5px; }
    .btn-primary { background-color: #0d6efd; border-color: #0d6efd; }
    .btn-primary:hover { background-color: #0b5ed7; border-color: #0a58ca; }
    .btn-danger { background-color: #dc3545; border-color: #dc3545; }
    .btn-danger:hover { background-color: #c82333; border-color: #bd2130; }
    textarea { resize: vertical; }
    .note-item { display: flex; justify-content: space-between; align-items: center; }
    .add-note-btn { margin-bottom: 15px; }
  </style>
</head>
<body>
<div class="container lecture-container">
  <h1 class="mb-4"><c:out value="${lecture.title}"/></h1>

  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
  </c:if>

  <%-- Notes Section --%>
  <h3 class="section-title">Notes</h3>
  <sec:authorize access="hasRole('ROLE_TEACHER')">
    <a href="${pageContext.request.contextPath}/lecture/${lecture.id}/add-note" class="btn btn-primary add-note-btn">Add Note</a>
  </sec:authorize>
  <c:if test="${empty notes}">
    <p class="text-muted">No notes available.</p>
  </c:if>
  <c:if test="${not empty notes}">
    <div>
      <c:forEach var="note" items="${notes}">
        <div class="note-item">
          <p><a href="<c:out value='${note.fileUrl}'/>" target="_blank"><c:out value="${note.fileName}"/></a></p>
          <sec:authorize access="hasRole('ROLE_TEACHER')">
            <form action="${pageContext.request.contextPath}/lecture/${lecture.id}/note/${note.id}/delete" method="post">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete ${note.fileName}?')">Delete</button>
            </form>
          </sec:authorize>
        </div>
      </c:forEach>
    </div>
  </c:if>

  <%-- Comments Section --%>
  <h3 class="section-title">Comments</h3>
  <c:if test="${empty comments}">
    <p class="text-muted">No comments available.</p>
  </c:if>
  <c:if test="${not empty comments}">
    <div>
      <c:forEach var="comment" items="${comments}">
        <div class="comment d-flex justify-content-between align-items-start">
          <div>
            <strong><c:out value="${comment.author != null ? comment.author.username : 'Unknown'}"/>:</strong>
            <p><c:out value="${comment.content}"/></p>
          </div>
          <sec:authorize access="hasRole('ROLE_TEACHER')">
            <form action="${pageContext.request.contextPath}/lecture/${lecture.id}/comment/${comment.id}/delete" method="post">
              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <button type="submit" class="btn btn-danger btn-sm">Delete</button>
            </form>
          </sec:authorize>
        </div>
      </c:forEach>
    </div>
  </c:if>

  <%-- Add Comment Form --%>
  <sec:authorize access="hasAnyRole('STUDENT', 'TEACHER')">
    <form action="${pageContext.request.contextPath}/lecture/${lecture.id}/comment" method="post" class="mt-4">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="mb-3">
        <label for="commentContent" class="form-label">Add Comment</label>
        <textarea class="form-control" id="commentContent" name="content" rows="4" required aria-label="Comment text"></textarea>
      </div>
      <button type="submit" class="btn btn-primary">Submit Comment</button>
    </form>
  </sec:authorize>

  <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-4">Back to Home</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>