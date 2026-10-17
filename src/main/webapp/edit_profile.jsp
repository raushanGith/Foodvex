<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User"%>
<%
// Security check
User user = (User) session.getAttribute("loggedInUser");
if (user == null) {
	response.sendRedirect("login.jsp");
	return;
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Edit Profile</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=17">

</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container profile-wrapper">
		<div class="neu-card profile-card">
			<h2 class="page-title">Edit Account Details</h2>

			<form action="updateProfile" method="POST">

				<div class="form-group">
					<label>Email Address (Cannot be changed)</label> <input type="text"
						class="neu-input" value="<%=user.getEmail()%>" readonly>
				</div>

				<div class="form-group">
					<label>Full Name</label> <input type="text" name="username"
						class="neu-input" value="<%=user.getUsername()%>" required>
				</div>

				<div class="form-group">
					<label>Phone Number</label> <input type="text" name="phone"
						class="neu-input" value="<%=user.getPhone()%>" required>
				</div>

				<div class="form-group">
					<label>Delivery Address</label>
					<textarea name="address" class="neu-input" rows="3" required><%=user.getAddress()%></textarea>
				</div>

				<div class="action-buttons">
					<a href="profile.jsp" class="neu-btn">Cancel</a>
					<button type="submit" class="neu-btn-solid">Save Changes</button>
				</div>
			</form>

		</div>
	</div>

</body>
</html>