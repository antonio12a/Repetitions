package com.example.ripetizioni;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ripetizioni.database.AppDatabase;
import com.example.ripetizioni.database.DatabaseDao;
import com.example.ripetizioni.database.RepositoryCorsi;
import com.example.ripetizioni.ui.corsil.CorsiFragment;
import com.example.ripetizioni.ui.prenotazioni.PrenotazioniAdapter;
import com.example.ripetizioni.ui.prenotazioni.PrenotazioniFragment;
import com.google.android.material.navigation.NavigationView;

import Session.Login;
import Session.SessionManagement;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;
    SessionManagement sessionManagement;
    public Bundle idSession;
    DrawerLayout drawer;
    private String username_text;
    private TextView title_toolbar;
    PrenotazioniFragment prenotazioniFragment;
    CorsiFragment corsiFragment;
    SearchView searchView;
    private static String fragmentIn = "corsi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagement = new SessionManagement(MainActivity.this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        idSession = new Bundle();
        Log.d("SESSIONE", String.valueOf(sessionManagement.getSession()));
        idSession.putInt("idSession", sessionManagement.getSession());
        Bundle in = getIntent().getExtras();
        username_text = in.getString("username_text");
        RepositoryCorsi repositoryCorsi = new RepositoryCorsi(getApplication());
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_corsi, R.id.nav_prenotazioni)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.setGraph(R.navigation.mobile_navigation, idSession);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.textViewUser);
        RepositoryCorsi repository = new RepositoryCorsi(getApplication());
        prenotazioniFragment = new PrenotazioniFragment();
        corsiFragment = new CorsiFragment();
        navUsername.setText(username_text);
        View toolbarView = toolbar.getRootView();
        title_toolbar = (TextView) toolbarView.findViewById(R.id.title_toolbar);
        title_toolbar.setText("Corsi disponibili");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.search_toolbar);
        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                if(fragmentIn.equals("corsi"))
                    corsiFragment.corsiAdapter.getFilter().filter(s);
                else
                    prenotazioniFragment.prenotazioniAdapter.getFilter().filter(s);

                    return false;
                }
            });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void logout() {
        sessionManagement.removeSession();

        moveToLogin();

    }
    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.buttonLogoutNV:
                logout();
                break;
            case R.id.buttonCorsiDisponibili:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_corsi, idSession);
                title_toolbar.setText("Corsi disponibili");
                drawer.closeDrawers();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                fragmentIn = "corsi";
                break;
            case R.id.buttonPrenotazioni:
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_prenotazioni, idSession);
                title_toolbar.setText("Le mie prenotazioni");
                drawer.closeDrawers();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                fragmentIn = "prenotazioni";
                break;
            default:
                break;
        }
        return true;
    }
}