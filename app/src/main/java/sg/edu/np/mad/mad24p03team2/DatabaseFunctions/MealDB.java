package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.AbstractDBProcess;
import sg.edu.np.mad.mad24p03team2.ApplicationSetUp.StartUp;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

public class MealDB extends AbstractDBProcess {
    Statement stmt;
    private final Context context;
    private Connection dbCon = null;
    AccountClass account = null;
    FoodDB foodDB = null;

    protected MealDB(Context appContext) {
        super(appContext);
        this.context = appContext;
        getDBConnection();
    }

    private void getDBConnection(){
        if(context instanceof StartUp){
            final StartUp app = (StartUp) context;
            dbCon = app.getConnection();
        }
    }

    public ResultSet GetRecord(String mealName, int accountID){
        // Get records that are equal to the mealname (eg Breakfast) and is today's record
        String sql = "SELECT * FROM Meal WHERE MealName = '"+mealName+"' AND AccID = " + accountID;

        try {
            stmt = dbCon.createStatement();
            return stmt.executeQuery(sql); // Column 1 = email, Column 2 = password
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean CreateRecord(int accId, FoodItemClass foodItem, String mealName, int quantity) throws SQLException {
        Boolean isSuccess = false;
        ResultSet resultSet = null;
        String sql = "INSERT INTO Meal(AccID, FoodID, MealName, Quantity, Timestamp) VALUES ('"+accId+"','"+foodItem.getId()+"','"+mealName+"',"+quantity+", GETDATE())";

        try{
            // Check if record is already inside LoginInfo
            Log.d("CreateRecord", "Connection = " +dbCon);
            stmt = dbCon.createStatement();
            stmt.executeUpdate(sql);
            isSuccess = true;
        }
        catch (Exception e) {
            return isSuccess;
        }
        finally{
            // Close resultset
            if(resultSet != null) {
                resultSet.close();
            }
        }

        return isSuccess;
    }

    public boolean UpdateQuantity(int accId, FoodItemClass foodItem, String mealName, int quantity) throws SQLException {
        boolean isUpdateSuccessful = false;
        ResultSet resultSet = null;
        String sql = null;
        int mealId = 0;

        try {
            resultSet = GetRecord(mealName, accId);
            if (resultSet.next()) {
                // If there is record
                if (quantity > 0) {
                    mealId = resultSet.getInt("MealID");
                    // Create and execute the SQL statement to Database
                    sql = "UPDATE MealDB SET Quantity = '"+quantity+"' WHERE MealId = '"+mealId+"'";
                    stmt = dbCon.createStatement();
                    stmt.executeUpdate(sql);

                } else {
                    // Quantity is 0, so should delete the record
                    DeleteMealItem(mealId);
                }
                isUpdateSuccessful = true;
            } else {
                // Create a new record
                CreateRecord(accId, foodItem, mealName, quantity);
            }
        } catch (Exception e) {
            Log.d("UpdateRecord failed", e.getMessage());
            return isUpdateSuccessful = false;
        } finally {
            // Close resultset
            if (resultSet != null) {
                resultSet.close();
            }
        }

        Log.d("Update Record", "status = " + isUpdateSuccessful);
        return isUpdateSuccessful;
    }

    public boolean UpdateBloodSugar(String mealName, Float bloodSugar) throws SQLException {
        boolean isUpdateSuccessful = false;
        ResultSet resultSet = null;
        String sql = null;
        int mealId = 0;

        try {
            resultSet = GetRecord(mealName, mealId);
            if (resultSet.next()) {
                // If there is record
                sql = "UPDATE MealDB SET BloodSugar = '" + bloodSugar + "' WHERE MealId = '" + mealId + "'";
                stmt = dbCon.createStatement();
                stmt.executeUpdate(sql);
                isUpdateSuccessful = true;
            }
        } catch (Exception e) {
            Log.d("UpdateBloodSugar", "Error: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return isUpdateSuccessful;
    }

    public boolean DeleteMealItem(int mealId) throws SQLException {
        boolean isUpdateSuccessful = false;
        String sql = null;

        try {
            sql = "DELETE FROM Meal WHERE MealID = " + mealId;
            stmt = dbCon.createStatement();
            stmt.executeUpdate(sql);
            isUpdateSuccessful = true;
        } catch (Exception e) {
            Log.d("DELETE MEAL", "Deletion failed: " + e.getMessage());
            isUpdateSuccessful = false;
        }

        return isUpdateSuccessful;
    }
}