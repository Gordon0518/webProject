<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { padding: 20px; }
        .form-container { max-width: 500px; margin: auto; }
    </style>
</head>
<body>
<div class="container form-container">
    <h1 class="text-center mb-4">Profile</h1>
    <c:if test="${param.success}">
        <div class="alert alert-success">Profile updated successfully!</div>
    </c:if>
    <sec:authorize access="hasRole('STUDENT')">
        <form method="post" action="${pageContext.request.contextPath}/profile">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="mb-3">
                <label for="username" class="form-label">Username (cannot be changed)</label>
                <input type="text" class="form-control" id="username" value="${user.username}" disabled>
            </div>
            <div class="mb-3">
                <label for="fullName" class="form-label">Full Name</label>
                <input type="text" class="form-control" id="fullName" name="fullName" value="${user.fullName}" required>
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
            </div>
            <div class="mb-3">
                <label for="phoneNumber" class="form-label">Phone Number</label>
                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">New Password (leave blank to keep current)</label>
                <input type="password" class="form-control" id="password" name="password">
            </div>
            <button type="submit" class="btn btn-primary w-100">Update Profile</button>
        </form>
    </sec:authorize>
    <p class="text-center mt-3">
        <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Back to Course</a>
    </p>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
