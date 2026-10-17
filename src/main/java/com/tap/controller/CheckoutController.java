package com.tap.controller;

import java.io.IOException;
import java.sql.Timestamp;

import com.tap.daoimpl.OrderDaoImpl;
import com.tap.daoimpl.OrderItemDaoImpl;
import com.tap.model.Cart;
import com.tap.model.CartItem;
import com.tap.model.Order;
import com.tap.model.OrderItem;
import com.tap.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (cart == null || cart.getItems().isEmpty()) {
            resp.sendRedirect("cart.jsp");
            return;
        }

        if (loggedInUser == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String paymentMode = req.getParameter("paymentMode");
        double totalAmount = cart.getTotalAmount();
        int restaurantId = cart.getItems().values().iterator().next().getRestaurantId();

        // 1. Save the main Order
        Order order = new Order();
        order.setUserId(loggedInUser.getUserId());
        order.setRestaurantId(restaurantId);
        order.setOrderdate(new Timestamp(System.currentTimeMillis()));
        order.setTotalamount(totalAmount);
        order.setStatus("Pending");
        order.setPaymentMode(paymentMode);

        OrderDaoImpl orderDao = new OrderDaoImpl();
        int newOrderId = orderDao.addOrder(order); // Capturing the new ID

        // 2. Loop through the cart and save each OrderItem
        if (newOrderId > 0) {
            OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();
            
            for (CartItem item : cart.getItems().values()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(newOrderId);
                orderItem.setMenuId(item.getItemId()); // Sourced from your cart.jsp logic
                orderItem.setQuantity(item.getQuantity());
                orderItem.setItemTotal(item.getSubTotal());
                
                // Assuming your DAO method is named addOrderItem
                orderItemDao.addOrderItem(orderItem); 
            }
        }

        // 3. Clear cart and redirect
        cart.clear();
        session.setAttribute("cart", cart);
        resp.sendRedirect("order_success.jsp");
    }
}