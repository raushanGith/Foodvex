package com.tap.dao;

import java.util.List;
import com.tap.model.Order;

public interface OrderDao {
	public int addOrder(Order order);
    Order getOrder(int orderId);
    List<Order> getAllOrders();
    List<Order> getOrdersByUserId(int userId);
    void updateOrder(Order order);
    void deleteOrder(int orderId);
}
