package com.tap.controller;

import java.io.IOException;
import java.util.List;

import com.tap.daoimpl.RestaurantDaoImpl;
import com.tap.model.Restaurant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/restaurant")
public class RestaurantController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. Retrieve the search query from the URL (e.g., ?search=pizza)
		String searchQuery = req.getParameter("search");

		RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();
		List<Restaurant> allrestaurants;

		// 2. If the user searched for something, fetch filtered results
		if (searchQuery != null && !searchQuery.trim().isEmpty()) {
			allrestaurants = restaurantDao.getRestaurantsBySearch(searchQuery.trim());
		}
		// 3. Otherwise, fetch everything normally
		else {
			allrestaurants = restaurantDao.getAllRestaurants();
		}

		// 4. Send the data to the JSP using your existing "restaurants" attribute name
		req.setAttribute("restaurants", allrestaurants);

		RequestDispatcher rd = req.getRequestDispatcher("restaurant.jsp");
		rd.forward(req, resp);
	}
}