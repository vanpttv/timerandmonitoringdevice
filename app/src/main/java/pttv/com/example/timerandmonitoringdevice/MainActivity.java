package pttv.com.example.timerandmonitoringdevice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
//import android.support.v7.widget.Toolbar;
//import android.support.v7.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pttv.com.example.timerandmonitoringdevice.fragment.CameraFragment;
import pttv.com.example.timerandmonitoringdevice.fragment.HomeFragment;
import pttv.com.example.timerandmonitoringdevice.fragment.Lan1Fragment;
import pttv.com.example.timerandmonitoringdevice.fragment.Lan2Fragment;
import pttv.com.example.timerandmonitoringdevice.fragment.Lan3Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int fragment_home=0;
    private static final int fragment_lan1=1;
    private static final int fragment_lan2=2;
    private static final int fragment_lan3=3;
    private static final int fragment_camera=4;

    private int current_fragment=fragment_home;

    DrawerLayout drawerLayout;
    NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        changeFragment(new HomeFragment()); // mo ung dung la vao thang trang chu
        navigationView.getMenu().findItem(R.id.home_nav).setChecked(true); //nut home duoc check

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.home_nav){
            if(current_fragment !=fragment_home){
                open_home();
            }
        }
        else if(id==R.id.l1_nav){
            if(current_fragment !=fragment_lan1){
                open_lan1();
            }
        }
        else if(id==R.id.l2_nav) {
            if(current_fragment !=fragment_lan2){
                open_lan2();
            }
        }
        else if(id==R.id.l3_nav) {
            if(current_fragment !=fragment_lan3){
                open_lan3();
            }
        }
        else if(id==R.id.camera_nav) {
            if(current_fragment !=fragment_camera){
                open_cam();
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void open_cam() {
        changeFragment(new CameraFragment());
        current_fragment = fragment_camera;
        navigationView.getMenu().findItem(R.id.camera_nav).setChecked(true);
        navigationView.getMenu().findItem(R.id.home_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l1_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l2_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l3_nav).setChecked(false);
    }

    private void open_lan3() {
        changeFragment(new Lan3Fragment());
        current_fragment = fragment_lan3;
        navigationView.getMenu().findItem(R.id.l3_nav).setChecked(true);
        navigationView.getMenu().findItem(R.id.home_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l1_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l2_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.camera_nav).setChecked(false);
    }

    private void open_lan2() {
        changeFragment(new Lan2Fragment());
        current_fragment = fragment_lan2;
        navigationView.getMenu().findItem(R.id.l2_nav).setChecked(true);
        navigationView.getMenu().findItem(R.id.home_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l1_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l3_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.camera_nav).setChecked(false);
    }

    private void open_lan1() {
        changeFragment(new Lan1Fragment());
        current_fragment = fragment_lan1;
        navigationView.getMenu().findItem(R.id.l1_nav).setChecked(true);
        navigationView.getMenu().findItem(R.id.home_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l2_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l3_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.camera_nav).setChecked(false);
    }

    private void open_home() {
        changeFragment(new HomeFragment());
        current_fragment = fragment_home;
        navigationView.getMenu().findItem(R.id.home_nav).setChecked(true); //nut home duoc check
        navigationView.getMenu().findItem(R.id.l1_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l2_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.l3_nav).setChecked(false);
        navigationView.getMenu().findItem(R.id.camera_nav).setChecked(false);
    }

    @Override
    public void onBackPressed() { // khi dang mo drawer ma an nut back thi tat drawer nhan back them 1 lan nua moi tat ung dung
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    private void changeFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,fragment);
        transaction.commit();
    }

}