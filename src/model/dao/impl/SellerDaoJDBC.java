package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            statement = connection.prepareStatement("SELECT seller.*,dep.Name as DepName " +
                    "FROM seller INNER JOIN department AS dep " +
                    "ON seller.DepartmentId = dep.Id " +
                    "WHERE seller.Id = ?");
            statement.setInt(1,id);
            resultSet = statement.executeQuery();

            if(!resultSet.next()){
                return null;
            }

            Department department = instantiateDepartment(resultSet);

            return instantiateSeller(resultSet,department);

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
        }



    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT seller.*,dep.Name as DepName " +
                    "FROM seller INNER JOIN department AS dep " +
                    "ON seller.DepartmentId = dep.Id " +
                    "ORDER BY Id");

            resultSet = statement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer,Department> mapDepartments = new HashMap<>();

            while (resultSet.next()){
                Department dep = mapDepartments.get(resultSet.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(resultSet);
                    mapDepartments.put(resultSet.getInt("DepartmentId"),dep);
                }

                sellers.add(instantiateSeller(resultSet,dep));
            }

            return sellers;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
        }
    }

    @Override
    public List<Seller> findByDeparment(Department department) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try{
            statement = connection.prepareStatement("SELECT seller.*,dep.Name as DepName " +
                    "FROM seller INNER JOIN department AS dep " +
                    "ON seller.DepartmentId = dep.Id " +
                    "WHERE DepartmentId = ? " +
                    "ORDER BY Name");

            statement.setInt(1,department.getId());
            resultSet = statement.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer,Department> mapDepartments = new HashMap<>();

            while (resultSet.next()){
                Department dep = mapDepartments.get(resultSet.getInt("DepartmentId"));

                if(dep == null){
                    dep = instantiateDepartment(resultSet);
                    mapDepartments.put(resultSet.getInt("DepartmentId"),dep);
                }

                sellers.add(instantiateSeller(resultSet,dep));
            }

            return sellers;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeResultSet(resultSet);
            DB.closeStatement(statement);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setDepartment(department);

        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department =  new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }


}
