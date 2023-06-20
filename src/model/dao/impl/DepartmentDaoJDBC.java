package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection connection;

    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, department.getName());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected < 0) {
                throw new DbException("Unexpected Error: No rows afeccted");
            }

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                department.setId(resultSet.getInt(1));
            }
            DB.closeResultSet(resultSet);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE department SET Name= ? WHERE Id= ?");
            statement.setString(1, department.getName());
            statement.setInt(2, department.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("DELETE FROM department WHERE Id= ?");
            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM department WHERE Id= ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            return instantiateDepartment(resultSet);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public List<Department> findAll() {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM department ORDER BY Id");
            ResultSet resultSet = statement.executeQuery();
            List<Department> departments = new ArrayList<>();

            while (resultSet.next()) {
                departments.add(instantiateDepartment(resultSet));
            }
            DB.closeResultSet(resultSet);
            return departments;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}
