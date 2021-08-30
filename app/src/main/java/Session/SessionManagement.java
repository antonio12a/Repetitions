package Session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ripetizioni.database.Utente;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context){
      sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
      editor = sharedPreferences.edit();
    }

    public void saveSession(Utente user){
        //salvo sessione utente
        int id = user.utenteId;

        editor.putInt(SESSION_KEY, id).commit();
    }

    public int getSession(){
        //ritorna id utente
        return sharedPreferences.getInt(SESSION_KEY, -1);
    }

    public void removeSession(){
        editor.putInt(SESSION_KEY, -1).commit();
    }
}
