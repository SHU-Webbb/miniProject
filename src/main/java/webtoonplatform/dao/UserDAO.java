package webtoonplatform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.JDBConnect;
import webtoonplatform.dto.UserDTO;

public class UserDAO {
    private Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    // 사용자 생성
    public void createUser(UserDTO user) {
    	try {
            String sql = "INSERT INTO users (usernum, id, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, user.getUsernum());
                preparedStatement.setString(2, user.getId());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 사용자 조회
    public UserDTO getUserById(int userId) {
        UserDTO userDTO = null;

        try {
            // SQL 쿼리 실행
            String sql = "SELECT * FROM users WHERE usernum=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // 결과가 있다면 UserDTO에 매핑
                    if (resultSet.next()) {
                        userDTO = new UserDTO();
                        userDTO.setUsernum(resultSet.getInt("usernum"));
                        userDTO.setId(resultSet.getString("id"));
                        userDTO.setPassword(resultSet.getString("password"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userDTO;
    }


    // 사용자 목록 조회
    public List<UserDTO> getAllUsers() {
    	List<UserDTO> userList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setUsernum(resultSet.getInt("usernum"));
                    userDTO.setId(resultSet.getString("id"));
                    userDTO.setPassword(resultSet.getString("password"));
                    userList.add(userDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // 사용자 업데이트
    public void updateUser(UserDTO user) {
    	try {
            String sql = "UPDATE users SET id=?, password=? WHERE usernum=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setInt(3, user.getUsernum());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 사용자 삭제
    public void deleteUser(int userId) {
    	try {
            String sql = "DELETE FROM users WHERE usernum=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void closeConnection() {
        // DAO 사용이 끝나면 연결을 종료
        new JDBConnect().close();
    }
}
