package com.codecool.plants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class PlantManager {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public PlantManager(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    public List<String> getPlantsToBeWatered() {
        List<String> result = new ArrayList<>();
        try (Connection conn = getConnection(dbUrl,dbUser, dbPassword)) {
            String SQL = "SELECT DISTINCT p.plant_name FROM plants as p LEFT JOIN watered as w ON p.plant_id = w.plant_id WHERE w.watered_at < '2022-01-01' ORDER BY p.plant_name ASC";
            PreparedStatement st = conn.prepareStatement(SQL);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                String name = rs.getString("plant_name");
                result.add(name);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }
}
