<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Login</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=14">

</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container auth-wrapper">

		<div class="neu-card auth-card">
			<!-- Title with gradient text -->
			<h2 class="text-center"
				style="margin-top: 0; margin-bottom: 30px; background: linear-gradient(135deg, var(--primary-yellow), var(--primary-orange)); -webkit-background-clip: text; -webkit-text-fill-color: transparent; text-align: center;">
				Welcome Back</h2>

			<%
			String error = request.getParameter("error");
			if ("invalid".equals(error)) {
			%>
			<div class="error-msg">Invalid email or password. Please try
				again.</div>
			<%
			}
			%>

			<form action="login" method="post">
				<div class="form-group">
					<label>Email</label> <input type="email" name="email"
						class="neu-input" required placeholder="Enter your email">
				</div>

				<div class="form-group">
					<label>Password</label> <input type="password" name="password"
						class="neu-input" required placeholder="Enter your password">
				</div>

				<button type="submit" class="neu-btn-solid"
					style="width: 100%; margin-top: 15px;">Login</button>
			</form>

			<div class="auth-footer">
				<span>Don't have an account? </span> <a href="register.jsp">Sign
					up here</a>
			</div>
		</div>

	</div>

</body>
</html>