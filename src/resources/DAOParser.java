package resources;

import entity.CustomDrink;
import entity.Queue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOParser {
    public static CustomDrink[] parseIntoCustomDrinkArray(ResultSet resultSet) {
        List<CustomDrink> list = new ArrayList<>();
        try {
            while(resultSet.next()) {
                CustomDrink customDrink = new CustomDrink();
                customDrink.setId(resultSet.getInt("id"));
                customDrink.setName(resultSet.getString("name"));
                customDrink.setDescription(resultSet.getString("description"));
                customDrink.setVolumeCl1(resultSet.getFloat("volumeCl1"));
                customDrink.setVolumeCl2(resultSet.getFloat("volumeCl2"));
                customDrink.setVolumeCl3(resultSet.getFloat("volumeCl3"));
                customDrink.setVolumeCl4(resultSet.getFloat("volumeCl4"));
                customDrink.setVolumeCl5(resultSet.getFloat("volumeCl5"));
                list.add(customDrink);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new CustomDrink[0]);
    }

    public static CustomDrink parseIntoCustomDrink(ResultSet resultSet) {
        CustomDrink customDrink = new CustomDrink();
        try {
            if(resultSet.next()) {
                customDrink.setId(resultSet.getInt("id"));
                customDrink.setName(resultSet.getString("name"));
                customDrink.setDescription(resultSet.getString("description"));
                customDrink.setVolumeCl1(resultSet.getFloat("volumeCl1"));
                customDrink.setVolumeCl2(resultSet.getFloat("volumeCl2"));
                customDrink.setVolumeCl3(resultSet.getFloat("volumeCl3"));
                customDrink.setVolumeCl4(resultSet.getFloat("volumeCl4"));
                customDrink.setVolumeCl5(resultSet.getFloat("volumeCl5"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customDrink;
    }

    public static Queue[] parseIntoQueueArray(ResultSet resultSet) {
        List<Queue> list = new ArrayList<>();
        try {
            while(resultSet.next()) {
                Queue queue = new Queue();
                queue.setId(resultSet.getInt("id"));
                queue.setCustomDrinkFk(resultSet.getInt("customDrinkFk"));
                queue.setDrinkFk(resultSet.getInt("drinkFk"));
                queue.setStatus(resultSet.getString("status"));
                list.add(queue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.toArray(new Queue[0]);
    }
}
