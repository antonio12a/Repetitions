package Session;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.ripetizioni.MainActivity;
import com.example.ripetizioni.R;
import com.example.ripetizioni.database.AppDatabase;
import com.example.ripetizioni.database.Corso;
import com.example.ripetizioni.database.Docente;
import com.example.ripetizioni.database.Prenotazioni;
import com.example.ripetizioni.database.Utente;
import com.example.ripetizioni.database.ViewModelLogin;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    private EditText textUsername;
    private EditText textPassword;
    private String username;
    private String password;

    private TextInputLayout textInputLayout_username;
    private TextInputLayout textInputLayout_password;

    private Button buttonLogin;
    private Button buttonRegistrati;
    private Button buttonSenzaReg;
    private ArrayAdapter<String> adapterUtenti;
    private ArrayAdapter<String> adapter;
    private Utente utenteIn = new Utente(5000, "default", "default");

    private List<String> list = new ArrayList<>();
    private List<Utente> listUtenti  = new ArrayList<>();

    private ViewModelLogin viewModelLogin;

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {
        //vediamo se l'utente è loggato
        SessionManagement sessionManagement = new SessionManagement(Login.this);
        int userID = sessionManagement.getSession();
        if(userID != -1){
            moveToMainActivity();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        textUsername = findViewById(R.id.editTextTextEmailAddress);
        username = textUsername.getText().toString();
        textInputLayout_username = findViewById(R.id.username_textInput);
        textInputLayout_password = findViewById(R.id.password_textInput);

        textPassword = findViewById(R.id.editTextPassword);
        password = textPassword.getText().toString();

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegistrati = findViewById(R.id.buttonRegistrati);
        buttonSenzaReg = findViewById(R.id.buttonsenzaRegistrazione);

        viewModelLogin = ViewModelProviders.of(this).get(ViewModelLogin.class);
        viewModelLogin.getAllUtenti().observe(this, new Observer<List<Utente>>() {
            @Override
            public void onChanged(List<Utente> utentes) {
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = textUsername.getText().toString();

                password = textPassword.getText().toString();
                utenteIn = new Utente(5, username, password);

                if(viewModelLogin.checkUtente(utenteIn.username)){
                    if (viewModelLogin.checkPassword(utenteIn.username, utenteIn.password)>0) {
                        //apro sessione
                        utenteIn.utenteId = viewModelLogin.checkPassword(utenteIn.username, utenteIn.password);
                        SessionManagement sessionManagement = new SessionManagement(Login.this);
                        sessionManagement.saveSession(utenteIn);

                        moveToMainActivity();
                    } else {
                        textInputLayout_password.setErrorEnabled(true);
                        textPassword.setError("Password errata");
                    }
                }
                else{
                    textInputLayout_username.setErrorEnabled(true);
                    textUsername.setError("Username non registrato");
                }

            }});

        buttonRegistrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = textUsername.getText().toString();

                password = textPassword.getText().toString();
                if(viewModelLogin.checkUtente(username)) {
                    textInputLayout_username.setErrorEnabled(true);
                    textUsername.setError("Username già registrato");
                } else if(username.length() < 5){
                    textInputLayout_username.setErrorEnabled(true);
                    textUsername.setError("Username minore di 5 caratteri");
                }else if(password.length() < 5){
                    textInputLayout_password.setErrorEnabled(true);
                    textPassword.setError("Password minore di 5 caratteri");
                }
                else{
                    utenteIn = new Utente(viewModelLogin.getLastIndex() + 1, username, password);
                    viewModelLogin.addUtente(utenteIn);
                    SessionManagement sessionManagement = new SessionManagement(Login.this);
                    sessionManagement.saveSession(utenteIn);
                    moveToMainActivity();
                }
            }
        });
        buttonSenzaReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utenteIn = new Utente(-2, "Ospite", "Ospite");
                SessionManagement sessionManagement = new SessionManagement(Login.this);
                sessionManagement.saveSession(utenteIn);
                moveToMainActivity();
            }
        });
        

    }

    private void moveToMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("username_text", utenteIn.username);
        startActivity(intent);
    }
}