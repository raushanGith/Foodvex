package com.tap.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.tap.model.User;
import com.tap.daoimpl.MenuDaoImpl;

@WebServlet("/deleteMenu")
public class DeleteMenuController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Security Check
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loggedInUser");
		if (user == null || !"Admin".equalsIgnoreCase(user.getRole())) {
			response.sendRedirect("login.jsp");
			return;
		}

		// Get ID from URL and delete
		String idStr = request.getParameter("id");
		if (idStr != null) {
			int menuId = Integer.parseInt(idStr);
			MenuDaoImpl menuDao = new MenuDaoImpl();
			menuDao.deleteMenu(menuId);
		}

		// Refresh the page
		response.sendRedirect("manageMenu");
	}
}