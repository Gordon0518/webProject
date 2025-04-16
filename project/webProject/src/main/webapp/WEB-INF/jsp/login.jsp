<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { padding: 20px; }
        .form-container { max-width: 400px; margin: auto; }
    </style>
</head>
<body>
<div class="container form-container">
    <h1 class="text-center mb-4">Login</h1>
    <c:if test="${param.error !=null}">
        <div class="alert alert-danger">Invalid username or password</div>
    </c:if>
    <c:if test="${param.success}">
        <div class="alert alert-success">Registration successful! Please log in.</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Login</button>
    </form>
    <p class="text-center mt-3">
        Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a>
    </p>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>