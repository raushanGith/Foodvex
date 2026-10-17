package com.tap.controller;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt; // Added BCrypt import

import com.tap.daoimpl.UserDaoImpl;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. Retrieve credentials from the form
		String email = req.getParameter("email");
		String plainPassword = req.getParameter("password");

		// 2. Fetch the user by email using the new DAO method
		UserDaoImpl userDao = new UserDaoImpl();
		User user = userDao.getUserByEmail(email);

		// 3. Handle Login Success or Failure with BCrypt
		// First check if user exists, THEN check if the typed password matches the
		// stored hash
		if (user != null && BCrypt.checkpw(plainPassword, user.getPassword())) {

			// Success: Save user object to the HTTP Session
			HttpSession session = req.getSession();
			session.setAttribute("loggedInUser", user);

			// Redirect to the home page (Restaurants)
			resp.sendRedirect("restaurant");

		} else {
			// Failure: Redirect back to login with an error flag
			resp.sendRedirect("login.jsp?error=invalid");
		}
	}
}