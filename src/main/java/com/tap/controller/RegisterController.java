package com.tap.controller;

import java.io.IOException;
import com.tap.daoimpl.UserDaoImpl;
import com.tap.model.User;
import org.mindrot.jbcrypt.BCrypt; // Add the BCrypt library

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. Retrieve data from the register.jsp form
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String phone = req.getParameter("phone");
		String plainPassword = req.getParameter("password");
		String address = req.getParameter("address");

		// SECURITY UPGRADE: Scramble the password with BCrypt before saving
		String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

		// 2. Create a User object and set the data
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPhone(phone);
		user.setPassword(hashedPassword); // IMPORTANT: Save the HASH, not the plain text!
		user.setAddress(address);
		user.setRole("Customer"); // Setting a default role for new signups

		// 3. Save to database using your exact UserDaoImpl
		UserDaoImpl userDao = new UserDaoImpl();

		try {
			userDao.addUser(user);
			// If successful, redirect to login page
			resp.sendRedirect("login.jsp?registered=true");
		} catch (Exception e) {
			e.printStackTrace();
			// If it fails, send them back to the register page
			resp.sendRedirect("register.jsp?error=true");
		}
	}
}