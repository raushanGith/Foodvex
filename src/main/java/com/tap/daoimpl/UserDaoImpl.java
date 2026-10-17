package com.tap.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap.dao.UserDao;
import com.tap.model.User;
import com.tap.util.DBConnection;

public class UserDaoImpl implements UserDao {

	private static final String INSERT_USER_QUERY = "INSERT INTO user (username, password, email, phone, address, role) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE userId = ?";
	private static final String SELECT_ALL_USERS = "SELECT * FROM user";
	private static final String UPDATE_USER_QUERY = "UPDATE user SET username = ?, password = ?, email = ?, phone = ?, address = ?, role = ? WHERE userId = ?";
	private static final String DELETE_USER_QUERY = "DELETE FROM user WHERE userId = ?";

	// NEW CONSTANT: Fetch by email only for BCrypt
	private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";

	@Override
	public void addUser(User user) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_QUERY)) {

			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setString(6, user.getRole());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public User getUser(int userId) {
		User user = null;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {

			preparedStatement.setInt(1, userId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					user = extractUserFromResultSet(resultSet);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	// NEW METHOD: Fetches the user by email so LoginController can verify the hash
	public User getUserByEmail(String email) {
		User user = null;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {

			preparedStatement.setString(1, email);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					// Reusing your excellent helper method!
					user = extractUserFromResultSet(resultSet);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	// Note: If you get a red underline here, just remove the @Override tag,
	// or leave it as a dummy method so your UserDao interface doesn't complain.
	@Override
	public User getUser(String email, String password) {
		return null; // We no longer use this method because of BCrypt!
	}

	@Override
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS)) {

			while (resultSet.next()) {
				userList.add(extractUserFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Override
	public void updateUser(User user) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY)) {

			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getPhone());
			preparedStatement.setString(5, user.getAddress());
			preparedStatement.setString(6, user.getRole());
			preparedStatement.setInt(7, user.getUserId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUser(int userId) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY)) {

			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Helper method to keep code clean and prevent repetition
	private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setUserId(resultSet.getInt("userId"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setEmail(resultSet.getString("email"));
		user.setPhone(resultSet.getString("phone"));
		user.setAddress(resultSet.getString("address"));
		user.setRole(resultSet.getString("role"));
		return user;
	}
}