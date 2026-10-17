package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.OrderItemDao;
import com.tap.model.OrderItem;
import com.tap.util.DBConnection;

public class OrderItemDaoImpl implements OrderItemDao {

    private static final String INSERT_ORDER_ITEM_QUERY = "INSERT INTO orderitem (orderId, menuId, quantity, itemTotal) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ORDER_ITEM_BY_ID = "SELECT * FROM orderitem WHERE orderItemId = ?";
    private static final String SELECT_ALL_ORDER_ITEMS = "SELECT * FROM orderitem";
    private static final String SELECT_ORDER_ITEMS_BY_ORDER = "SELECT * FROM orderitem WHERE orderId = ?";
    private static final String UPDATE_ORDER_ITEM_QUERY = "UPDATE orderitem SET orderId = ?, menuId = ?, quantity = ?, itemTotal = ? WHERE orderItemId = ?";
    private static final String DELETE_ORDER_ITEM_QUERY = "DELETE FROM orderitem WHERE orderItemId = ?";

    @Override
    public void addOrderItem(OrderItem orderItem) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_QUERY)) {
            
            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getMenuId());
            preparedStatement.setInt(3, orderItem.getQuantity());
            preparedStatement.setDouble(4, orderItem.getItemTotal());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OrderItem getOrderItem(int orderItemId) {
        OrderItem orderItem = null;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_ITEM_BY_ID)) {
            
            preparedStatement.setInt(1, orderItemId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderItem = extractOrderItemFromResultSet(resultSet);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItem;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> orderItemList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDER_ITEMS)) {
            
            while (resultSet.next()) {
                orderItemList.add(extractOrderItemFromResultSet(resultSet));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItemList;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItemList = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_ITEMS_BY_ORDER)) {
            
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderItemList.add(extractOrderItemFromResultSet(resultSet));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItemList;
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_ITEM_QUERY)) {
            
            preparedStatement.setInt(1, orderItem.getOrderId());
            preparedStatement.setInt(2, orderItem.getMenuId());
            preparedStatement.setInt(3, orderItem.getQuantity());
            preparedStatement.setDouble(4, orderItem.getItemTotal());
            preparedStatement.setInt(5, orderItem.getOrderItemId());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrderItem(int orderItemId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_ITEM_QUERY)) {
            
            preparedStatement.setInt(1, orderItemId);
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    private OrderItem extractOrderItemFromResultSet(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(resultSet.getInt("orderItemId"));
        orderItem.setOrderId(resultSet.getInt("orderId"));
        orderItem.setMenuId(resultSet.getInt("menuId"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setItemTotal(resultSet.getDouble("itemTotal"));
        return orderItem;
    }
}