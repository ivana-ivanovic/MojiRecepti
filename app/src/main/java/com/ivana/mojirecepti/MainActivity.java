package com.ivana.mojirecepti;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.ivana.mojirecepti.database.ReceptiBaza;
import com.ivana.mojirecepti.fragment.KategorijeFragment;
import com.ivana.mojirecepti.fragment.OmiljeniFragment;
import com.ivana.mojirecepti.fragment.PocetnaFragment;
import com.ivana.mojirecepti.fragment.PretragaFragment;
import com.ivana.mojirecepti.model.Recept;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.sql.Date;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, PocetnaFragment.newInstance()).addToBackStack(null).commit();

        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dorucak) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, KategorijeFragment.newInstance(Recept.Tip.DORUCAK)).addToBackStack(null).commit();
            toolbar.setTitle(R.string.menu_dorucak);
        } else if (id == R.id.nav_rucak) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, KategorijeFragment.newInstance(Recept.Tip.RUCAK)).addToBackStack(null).commit();
            toolbar.setTitle(R.string.menu_rucak);
        } else if (id == R.id.nav_vecera) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, KategorijeFragment.newInstance(Recept.Tip.VECERA)).addToBackStack(null).commit();
            toolbar.setTitle(R.string.menu_vecera);
        } else if (id == R.id.nav_uzina) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, KategorijeFragment.newInstance(Recept.Tip.UZINA)).addToBackStack(null).commit();
            toolbar.setTitle(R.string.menu_uzina);
        } else if (id == R.id.nav_napici) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, KategorijeFragment.newInstance(Recept.Tip.NAPICI)).addToBackStack(null).commit();
            toolbar.setTitle(R.string.menu_napici);
        } else if (id == R.id.nav_pocetna) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, PocetnaFragment.newInstance()).addToBackStack(null).commit();
            toolbar.setTitle(R.string.menu_pocetna);
        } else if (id == R.id.nav_dodaj_recept) {
            Intent intent = new Intent(this, DodajActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_omiljeni) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, OmiljeniFragment.newInstance()).addToBackStack(null).commit();
            toolbar.setTitle(R.string.menu_omiljeni);
        } else if (id == R.id.nav_pretraga) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, PretragaFragment.newInstance()).addToBackStack(null).commit();
            toolbar.setTitle(R.string.menu_pretraga);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}