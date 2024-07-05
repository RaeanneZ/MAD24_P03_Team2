package sg.edu.np.mad.mad24p03team2.DatabaseFunctions;

import android.content.Context;

import java.sql.ResultSet;
import java.util.ArrayList;

import sg.edu.np.mad.mad24p03team2.Abstract_Interfaces.IDBProcessListener;
import sg.edu.np.mad.mad24p03team2.AsyncTaskExecutorService.AsyncTaskExecutorService;
import sg.edu.np.mad.mad24p03team2.SingletonClasses.SingletonSession;

/**
 * GetCurrentUserProfile
 * Model-Controller that handles UI-request to retrieve all currentUserProfile at Login
 */
public class GetCurrentUserProfile extends AsyncTaskExecutorService<String, String , String> {

    String z = "";
    Boolean isSuccess = false;
    ArrayList<IDBProcessListener> dbListeners = null;

    // Account DB
    AccountDB accountDB = null;

    private ArrayList<IDBProcessListener> listeners = new ArrayList<IDBProcessListener>();

    @Override
    protected String doInBackground(String... strings) {
        String email = strings[0];

        try{
            ResultSet resultSet = accountDB.GetRecord(email);
            if (resultSet.next()) {

                // Store info locally
                SingletonSession.getInstance().GetAccount().setId(resultSet.getInt("AccID"));
                SingletonSession.getInstance().SetUpAccount(resultSet.getString("Name"),
                        resultSet.getString("AccEmail"));

                // Update the user profile
                SingletonSession.getInstance().UpdateProfile(resultSet.getString("Gender"),
                        resultSet.getDate("BirthDate"), resultSet.getInt("Height"),
                        resultSet.getFloat("Weight"));
                SingletonSession.getInstance().SetBloodSugarTracking(resultSet.getString("TrackBloodSugar"));
              }

            isSuccess = true;
        }
        catch (Exception e) {
            z = e.getMessage();
        }

        return z;
    }

    public GetCurrentUserProfile(Context appContext){
        this.accountDB = new AccountDB(appContext);
        this.dbListeners = new ArrayList<IDBProcessListener>();
    }

    public GetCurrentUserProfile(Context appContext, IDBProcessListener listener){
        this(appContext);
        if(listener != null)
            registerListener(listener);
    }

    public void registerListener(IDBProcessListener listener){
        listeners.add(listener);
    }

    @Override
    protected ArrayList<FoodItemClass> doInBackground(String name) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        for(IDBProcessListener listener : dbListeners){
            listener.afterProcess(isSuccess, GetCurrentUserProfile.class);
        }
    }
}
