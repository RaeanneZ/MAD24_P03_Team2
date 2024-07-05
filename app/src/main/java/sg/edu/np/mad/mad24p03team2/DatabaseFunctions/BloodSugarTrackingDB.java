package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;
import android.util.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.AbstractDBProcess;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonBloodSugarResult;

/**
 * BloodSugarTrackingDB
 * Handles all the CRUD operations to the database
 */
public class BloodSugarTrackingDB extends AbstractDBProcess {
    Statement stmt;

    // Connect to database
    protected BloodSugarTrackingDB(Context appContext) {
        super(appContext);
    }

    // Read Function - Get the record by querying with Account ID, MealName and today's date
    public ResultSet GetRecord(int AccID, String mealName) {

        String sql = "SELECT * FROM BloodSugar WHERE AccID = " + AccID + " AND MealName = '" + mealName + "'";
        try {
            stmt = dbCon.createStatement();
            return stmt.executeQuery(sql);
        } catch (Exception e) {
            return null;
        }
    }

    public ResultSet GetRecordByDate(int AccID, String date) {

        String sql = "SELECT * FROM BloodSugar WHERE AccID = " + AccID + " AND TimeStamp = '" + date + "'";
        try {
            stmt = dbCon.createStatement();
            return stmt.executeQuery(sql);
        } catch (Exception e) {
            return null;
        }
    }

    // Create Function
    //int AccID, Float bloodSugarReading, String mealName, String timestamp
    public boolean CreateRecord(int AccID, Float bloodSugarReading, String mealName) throws SQLException {

        Boolean isSuccess = false;
        ResultSet resultSet = null;
        String sql = "INSERT INTO BloodSugar(AccID, BloodSugarReading, MealName, Timestamp) " +
                "VALUES (" + AccID + ", " + bloodSugarReading + ", '" + mealName + "'" + ", GETDATE())";

        try {
            // Check if record already exist
            resultSet = GetRecord(AccID, mealName);
            //if record does not exist
            if (!resultSet.isBeforeFirst() && resultSet.getRow() == 0) {
                // Create and execute the SQL statement to Database
                stmt = dbCon.createStatement();
                stmt.executeUpdate(sql);
                isSuccess = true;
            }

        } catch (Exception e) {
            Log.d("BloodSugarTrackingDB", "CreateRecord failed =" + e.getMessage());
            return isSuccess;
        } finally {
            // Close resultset
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return isSuccess;
    }

    //int AccID, Float bloodSugarReading, String mealName, String timestamp
    public boolean UpdateRecord(int AccID, Float bloodSugarReading, String mealName) throws SQLException {
        boolean isUpdateSuccessful = false;
        ResultSet resultSet = null;
        String sql = null;

        try {
            resultSet = GetRecord(AccID, mealName);
            // if there is result
            if (resultSet.next()) {
                // Create and execute the SQL statement to Database
                sql = "UPDATE BloodSugar SET BloodSugarReading = " + bloodSugarReading + ", Timestamp = GETDATE() WHERE " +
                        "AccID = " + AccID + " AND MealName = '" + mealName + "'";

                stmt = dbCon.createStatement();
                stmt.executeUpdate(sql);

                isUpdateSuccessful = true;

            } else {
                // If there is no results, call create function
                CreateRecord(AccID, bloodSugarReading, mealName);
                isUpdateSuccessful = true;
            }

            //getUpdated record and store locally
            resultSet = GetRecord(AccID, mealName);
            if (resultSet.next()) {
                BloodSugarClass bsugarClass = new BloodSugarClass(resultSet.getInt("BloodSugarID"), bloodSugarReading,
                        mealName, resultSet.getString("Timestamp"));
                SingletonBloodSugarResult.getInstance().addTodayBloodSugarByMeal(bsugarClass);
            }

        } catch (Exception e) {
            Log.d("BloodSugarTrackingDB", "UpdateRecord failed =" + e.getMessage());
            isUpdateSuccessful = false;
        } finally {
            // Close resultset
            if (resultSet != null) {
                resultSet.close();
            }

            return isUpdateSuccessful;
        }
    }
}
