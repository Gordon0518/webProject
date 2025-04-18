<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <title>Poll - ${pollQuestion}</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; background-color: #f8f9fa; }
    .poll-container { max-width: 700px; margin: auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    .section-title { margin-top: 20px; margin-bottom: 15px; color: #343a40; }
    .option-item { margin-bottom: 10px; }
    .option-item input:checked + label { font-weight: bold; color: #0d6efd; }
    .progress { height: 10px; margin-top: 5px; }
    .comment { margin-bottom: 15px; padding: 10px; background: #f1f3f5; border-radius: 5px; }
    .btn-primary { background-color: #0d6efd; border-color: #0d6efd; }
    .btn-primary:hover { background-color: #0b5ed7; border-color: #0a58ca; }
    .btn-secondary { background-color: #6c757d; border-color: #6c757d; }
    .btn-secondary:hover { background-color: #5c636a; border-color: #565e64; }
    .form-check-input:focus { box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25); }
    textarea { resize: vertical; }
  </style>
</head>
<body>
<div class="container poll-container">
  <h1 class="mb-4">${pollQuestion}</h1>

  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
  </c:if>

  <%-- Voting Form --%>
  <sec:authorize access="hasAnyRole('STUDENT', 'TEACHER')">
    <form action="${pageContext.request.contextPath}/poll/${pollId}/vote" method="post">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <h3 class="section-title">Options</h3>
      <c:set var="totalVotes" value="0"/>
      <c:forEach var="option" items="${options}">
        <c:set var="totalVotes" value="${totalVotes + option.voteCount}"/>
      </c:forEach>
      <c:forEach var="option" items="${options}">
        <div class="option-item">
          <div class="form-check">
            <input class="form-check-input" type="radio" name="optionId" id="option${option.id}" value="${option.id}"
                   <c:if test="${userVote != null && userVote.option != null && userVote.option.id == option.id}">checked</c:if>
                   aria-label="Select ${option.optionText}">
            <label class="form-check-label" for="option${option.id}">
              <c:out value="${option.optionText}"/>
            </label>
          </div>
          <div class="progress" role="progressbar" aria-label="Votes for ${option.optionText}" aria-valuenow="${option.voteCount}" aria-valuemin="0" aria-valuemax="${totalVotes > 0 ? totalVotes : 1}">
            <div class="progress-bar" style="width: ${totalVotes > 0 ? (option.voteCount * 100 / totalVotes) : 0}%"></div>
          </div>
          <small class="text-muted">Votes: <c:out value="${option.voteCount}"/></small>
        </div>
      </c:forEach>
      <div class="d-flex gap-2 mt-3">
        <button type="submit" class="btn btn-primary">Submit Vote</button>
      </div>
    </form>
  </sec:authorize>
  <sec:authorize access="!hasAnyRole('STUDENT', 'TEACHER')">
    <h3 class="section-title">Results</h3>
    <c:set var="totalVotes" value="0"/>
    <c:forEach var="option" items="${options}">
      <c:set var="totalVotes" value="${totalVotes + option.voteCount}"/>
    </c:forEach>
    <c:forEach var="option" items="${options}">
      <div class="option-item">
        <span><c:out value="${option.optionText}"/></span>
        <div class="progress" role="progressbar" aria-label="Votes for ${option.optionText}" aria-valuenow="${option.voteCount}" aria-valuemin="0" aria-valuemax="${totalVotes > 0 ? totalVotes : 1}">
          <div class="progress-bar" style="width: ${totalVotes > 0 ? (option.voteCount * 100 / totalVotes) : 0}%"></div>
        </div>
        <small class="text-muted">Votes: <c:out value="${option.voteCount}"/></small>
      </div>
    </c:forEach>
  </sec:authorize>

  <%-- Comments Section --%>
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

  <%-- Add Comment Form --%>
  <sec:authorize access="hasAnyRole('STUDENT', 'TEACHER')">
    <form action="${pageContext.request.contextPath}/poll/${pollId}/comment" method="post" class="mt-4">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="mb-3">
        <label for="content" class="form-label">Add Comment</label>
        <textarea class="form-control" id="content" name="content" rows="4" required aria-label="Comment text"></textarea>
      </div>
      <button type="submit" class="btn btn-primary">Submit Comment</button>
    </form>
  </sec:authorize>

  <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-4">Back to Home</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>