<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
  <title>Poll</title>
</head>
<body>
<h2>${pollQuestion}</h2>

<%-- Debug output --%>
<p>Debug: userVote = ${userVote}, userVote.option = ${userVote != null ? userVote.option : 'null'}</p>

<%-- Voting Form --%>
<sec:authorize access="hasRole('STUDENT')">
  <form action="${pageContext.request.contextPath}/poll/${pollId}/vote" method="post">
    <c:forEach var="option" items="${options}">
      <input type="radio" name="optionId" value="${option.id}"
             <c:if test="${userVote != null && userVote.option != null && userVote.option.id == option.id}">checked</c:if>>
      <c:out value="${option.optionText}"/> (Votes: <c:out value="${option.voteCount}"/>)<br>
    </c:forEach>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Vote">
  </form>
</sec:authorize>

<%-- Comments Section --%>
<h3>Comments</h3>
<c:forEach var="comment" items="${comments}">
  <p>
    <c:out value="${comment.content}"/>
    <c:if test="${comment.author != null}">
      by <c:out value="${comment.author.username}"/>
    </c:if>
  </p>
</c:forEach>

<%-- Add Comment Form --%>
<sec:authorize access="hasRole('STUDENT')">
  <form action="${pageContext.request.contextPath}/poll/${pollId}/comment" method="post">
    <textarea name="commentText" rows="4" cols="50"></textarea><br>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Add Comment">
  </form>
</sec:authorize>

<a href="${pageContext.request.contextPath}/">Back to Home</a>
</body>
</html>