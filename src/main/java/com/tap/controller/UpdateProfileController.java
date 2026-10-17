package com.tap.controller;

import java.io.IOException;

import com.tap.daoimpl.UserDaoImpl;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateProfile")
public class UpdateProfileController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		if (loggedInUser == null) {
			resp.sendRedirect("login.jsp");
			return;
		}

		// 1. Grab the updated information from the form
		String newName = req.getParameter("username");
		String newPhone = req.getParameter("phone");
		String newAddress = req.getParameter("address");

		// 2. Update the existing User object
		loggedInUser.setUsername(newName);
		loggedInUser.setPhone(newPhone);
		loggedInUser.setAddress(newAddress);

		// 3. Update the database using your existing UserDaoImpl
		UserDaoImpl userDao = new UserDaoImpl();
		// **IMPORTANT:** Check your UserDaoImpl.java to ensure this method is exactly
		// named "updateUser"
		userDao.updateUser(loggedInUser);

		// 4. Overwrite the session with the newly updated user
		session.setAttribute("loggedInUser", loggedInUser);

		// 5. Send them back to the newly updated profile page
		resp.sendRedirect("profile.jsp");
	}
}