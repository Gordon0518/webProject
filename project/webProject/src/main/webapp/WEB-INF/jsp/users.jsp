<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>User Management</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { padding: 20px; background-color: #f8f9fa; }
    .users-container { max-width: 1200px; margin: auto; background: white; border-radius: 8px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    .section-title { margin-top: 20px; margin-bottom: 15px; color: #343a40; }
    .btn-primary { background-color: #0d6efd; border-color: #0d6efd; }
    .btn-primary:hover { background-color: #0b5ed7; border-color: #0a58ca; }
    .btn-danger { background-color: #dc3545; border-color: #dc3545; }
    .btn-danger:hover { background-color: #bb2d3b; border-color: #b02a37; }
    .form-control { margin-bottom: 10px; }
    table { width: 100%; }
    th, td { padding: 10px; text-align: left; }
    .form-inline { display: flex; gap: 10px; flex-wrap: wrap; }
  </style>
</head>
<body>
<div class="container users-container">
  <h1 class="mb-4">User Management</h1>

  <c:if test="${not empty successMessage}">
    <div class="alert alert-success">${successMessage}</div>
  </c:if>
  <c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">${errorMessage}</div>
  </c:if>

  <sec:authorize access="hasRole('ROLE_TEACHER')">
    <!-- Add User Form -->
    <h3 class="section-title">Add New User</h3>
    <form action="${pageContext.request.contextPath}/users/add" method="post">
      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      <div class="row">
        <div class="col-md-3">
          <input type="text" class="form-control" name="username" placeholder="Username" required aria-label="Username"/>
        </div>
        <div class="col-md-3">
          <input type="password" class="form-control" name="password" placeholder="Password" required aria-label="Password"/>
        </div>
        <div class="col-md-3">
          <select class="form-control" name="role" required aria-label="Role">
            <option value="ROLE_STUDENT">Student</option>
            <option value="ROLE_TEACHER">Teacher</option>
          </select>
        </div>
        <div class="col-md-3">
          <input type="text" class="form-control" name="fullName" placeholder="Full Name" required aria-label="Full Name"/>
        </div>
        <div class="col-md-3">
          <input type="email" class="form-control" name="email" placeholder="Email" required aria-label="Email"/>
        </div>
        <div class="col-md-3">
          <input type="text" class="form-control" name="phoneNumber" placeholder="Phone Number" required aria-label="Phone Number"/>
        </div>
      </div>
      <button type="submit" class="btn btn-primary mt-2">Add User</button>
    </form>

    <!-- User List -->
    <h3 class="section-title">Users</h3>
    <c:if test="${empty users}">
      <p class="text-muted">No users available.</p>
    </c:if>
    <c:if test="${not empty users}">
      <table class="table table-bordered">
        <thead>
        <tr>
          <th>Username</th>
          <th>Role</th>
          <th>Full Name</th>
          <th>Email</th>
          <th>Phone Number</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
          <tr>
            <td><c:out value="${user.username}"/></td>
            <td><c:out value="${user.role}"/></td>
            <td><c:out value="${user.fullName}"/></td>
            <td><c:out value="${user.email}"/></td>
            <td><c:out value="${user.phoneNumber}"/></td>
            <td>
              <!-- Update Form -->
              <form action="${pageContext.request.contextPath}/users/update" method="post" class="form-inline">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="username" value="${user.username}"/>
                <input type="password" class="form-control" name="password" placeholder="New Password" aria-label="New Password"/>
                <select class="form-control" name="role" aria-label="Role">
                  <option value="">Keep Current</option>
                  <option value="ROLE_STUDENT">Student</option>
                  <option value="ROLE_TEACHER">Teacher</option>
                </select>
                <input type="text" class="form-control" name="fullName" placeholder="Full Name" value="${user.fullName}" aria-label="Full Name"/>
                <input type="email" class="form-control" name="email" placeholder="Email" value="${user.email}" aria-label="Email"/>
                <input type="text" class="form-control" name="phoneNumber" placeholder="Phone Number" value="${user.phoneNumber}" aria-label="Phone Number"/>
                <button type="submit" class="btn btn-primary btn-sm">Update</button>
              </form>
              <!-- Delete Form -->
              <form action="${pageContext.request.contextPath}/users/delete" method="post" class="form-inline">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="username" value="${user.username}"/>
                <button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure you want to delete ${user.username}?')">Delete</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:if>
  </sec:authorize>

  <a href="${pageContext.request.contextPath}/" class="btn btn-primary mt-4">Back to Home</a>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>