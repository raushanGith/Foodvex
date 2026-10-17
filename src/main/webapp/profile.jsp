<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User"%>
<%
// Security check: Ensure user is logged in
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
<title>Foodvex - My Profile</title>

<!-- 100% External CSS safely loaded -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=16">
</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container profile-wrapper">
		<div class="neu-card profile-card">

			<div class="profile-header">
				<div class="profile-avatar">👤</div>
				<h2 class="profile-title">Account Details</h2>
			</div>

			<div class="info-group">
				<span class="info-label">Full Name</span>
				<div class="info-value"><%=user.getUsername()%></div>
			</div>

			<div class="info-group">
				<span class="info-label">Email Address</span>
				<div class="info-value"><%=user.getEmail()%></div>
			</div>

			<div class="info-group">
				<span class="info-label">Phone Number</span>
				<div class="info-value"><%=user.getPhone()%></div>
			</div>

			<div class="info-group">
				<span class="info-label">Delivery Address</span>
				<div class="info-value" style="line-height: 1.5;"><%=user.getAddress()%></div>
			</div>

			<div class="info-group">
				<span class="info-label">Account Type</span>
				<div class="info-value" style="color: var(--primary-yellow);"><%=user.getRole()%></div>
			</div>

			<div class="profile-actions">
				<a href="edit_profile.jsp" class="neu-btn-solid"
					style="display: inline-block; padding: 12px 30px;">✏️ Edit
					Profile</a>
			</div>

		</div>
	</div>

</body>
</html>