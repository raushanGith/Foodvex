<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Foodvex - Register</title>

<!-- Safely pulling 100% of styles externally -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css?v=15">

</head>
<body>

	<jsp:include page="includes/navbar.jsp" />

	<div class="container auth-wrapper">

		<div class="neu-card register-card">
			<h2 class="text-center"
				style="margin-top: 0; margin-bottom: 30px; background: linear-gradient(135deg, var(--primary-yellow), var(--primary-orange)); -webkit-background-clip: text; -webkit-text-fill-color: transparent; text-align: center;">
				Create an Account</h2>

			<form action="register" method="post">
				<div class="form-group">
					<label>Full Name</label>
					<!-- CHANGED: name="name" is now name="username" to match Java backend -->
					<input type="text" name="username" class="neu-input" required
						placeholder="Enter your full name">
				</div>

				<div class="form-group">
					<label>Email</label> <input type="email" name="email"
						class="neu-input" required placeholder="Enter your email address">
				</div>

				<div class="form-group">
					<label>Phone Number</label> <input type="text" name="phone"
						class="neu-input" required placeholder="10-digit mobile number">
				</div>

				<div class="form-group">
					<label>Password</label> <input type="password" name="password"
						class="neu-input" required placeholder="Create a password">
				</div>

				<div class="form-group">
					<label>Delivery Address</label>
					<textarea name="address" class="neu-input neu-textarea" required
						placeholder="Enter your complete address"></textarea>
				</div>

				<button type="submit" class="neu-btn-solid"
					style="width: 100%; margin-top: 15px;">Sign Up</button>
			</form>

			<div class="auth-footer">
				<span>Already have an account? </span> <a href="login.jsp">Login
					here</a>
			</div>
		</div>

	</div>

</body>
</html>