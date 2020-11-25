package com.cm_grupo18.paint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.cm_grupo18.paint.ui.home.HomeFragment;
import com.cm_grupo18.paint.ui.home.PaintFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class PaintActivityDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Menu drawerMenu;
    private int backgroundColor = Color.WHITE;

    private DatabaseReference refDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_map, R.id.nav_settings, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        refDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        drawerMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.paint_activity_drawer, drawerMenu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_menu) {
            createDialogBoxText(0);
            return true;
        }
        else if (id == R.id.load_menu) {

            createDialogBoxText(1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //mode = 0 is save
    //mode = 1 is load
    private void createDialogBoxText(final int mode){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Name:");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mode == 0){
                    saveCanvas(input.getText().toString());
                }
                else if (mode == 1){
                    loadCanvas(input.getText().toString());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void saveCanvas(String saveName){
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment == null){
            return;
        }

        HomeFragment fragment = (HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment == null) {
            return;
        }

        PaintCanvasDTO canvasDTO = fragment.getPaintCanvasDTO();

        DatabaseReference databaseRef = refDatabase.child("canvas");
        databaseRef.child(saveName).setValue(canvasDTO.toMap(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null) {
                    Snackbar.make(findViewById(R.id.paint_fragment), R.string.data_fail, Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    Snackbar.make(findViewById(R.id.paint_fragment), R.string.data_success, Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private void loadCanvas(final String saveName){
        final boolean[] nameExists = {true};

        DatabaseReference databaseRef = refDatabase.child("canvas");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.child(saveName).exists()) {
                    Snackbar.make(findViewById(R.id.paint_fragment), R.string.data_non_exist, Snackbar.LENGTH_SHORT)
                            .show();
                    nameExists[0] = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        if (!nameExists[0]){
            return;
        }

        DatabaseReference ref = databaseRef.child(saveName);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PaintCanvasDTO canvasDTO = new PaintCanvasDTO();

                int background = Integer.parseInt(snapshot.child("background").getValue().toString());

                canvasDTO.setBackgroundColor(background);

                sendCanvasDTO(canvasDTO);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void sendCanvasDTO(PaintCanvasDTO canvasDTO){
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment == null){
            return;
        }

        HomeFragment fragment = (HomeFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
        if (fragment == null) {
            return;
        }

        fragment.setPaintCanvasDTO(canvasDTO);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int color){
        this.backgroundColor = color;
    }

    public void hideOptionsMenu(){
        if (drawerMenu != null) {
            MenuItem item = drawerMenu.findItem(R.id.save_menu);
            item.setVisible(false);
            MenuItem item2 = drawerMenu.findItem(R.id.load_menu);
            item2.setVisible(false);
        }
    }

    public void showOptionsMenu(){
        if (drawerMenu != null) {
            MenuItem item = drawerMenu.findItem(R.id.save_menu);
            item.setVisible(true);
            MenuItem item2 = drawerMenu.findItem(R.id.load_menu);
            item2.setVisible(true);
        }
    }

}