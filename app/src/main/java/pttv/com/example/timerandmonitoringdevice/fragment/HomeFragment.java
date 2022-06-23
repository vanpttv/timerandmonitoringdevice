package pttv.com.example.timerandmonitoringdevice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import pttv.com.example.timerandmonitoringdevice.R;

public class HomeFragment extends Fragment {
    TextView tv_time_l1, tv_weight_l1;
    TextView tv_time_l2, tv_weight_l2;
    TextView tv_time_l3, tv_weight_l3;
    Button on_cam, off_cam;
    WebView ipCamView;
    View myView;
    DatabaseReference myData;
    String time_1, weight_1, time_2, weight_2, time_3, weight_3;
    Integer val;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_home, container, false);

        tv_time_l1 = myView.findViewById(R.id.time_l1);
        tv_weight_l1 = myView.findViewById(R.id.weight_l1);
        tv_time_l2 = myView.findViewById(R.id.time_l2);
        tv_weight_l2 = myView.findViewById(R.id.weight_l2);
        tv_time_l3 = myView.findViewById(R.id.time_l3);
        tv_weight_l3 = myView.findViewById(R.id.weight_l3);
        myData = FirebaseDatabase.getInstance().getReference();
        on_cam=myView.findViewById(R.id.on_camera);
//        off_cam=myView.findViewById(R.id.off_camera);
//        ipCamView = myView.findViewById(R.id.ip_cam_view);
//        WebSettings webSettings=ipCamView.getSettings();
////        webSettings.setJavaScriptEnabled(true);
////        ipCamView.setWebViewClient(new Callback());
//        ipCamView.setWebViewClient(new WebViewClient());
//        ipCamView.loadUrl("http://192.168.1.109:81/stream");



        myData.child("Lan 1").child("Thoi gian cho an").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value = snapshot.getValue().toString();
                    tv_time_l1.setText("Thời gian cho ăn: " + value);
                }catch (NullPointerException e){
                    tv_time_l1.setText("Thời gian cho ăn: Updating...");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.", error.toException());
            }
        });



        myData.child("Lan 1").child("Khoi luong").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value = snapshot.getValue().toString();
                    tv_weight_l1.setText("Khối lượng thức ăn (gram): " + value);
                } catch (NullPointerException e) {
                    tv_weight_l1.setText("Khối lượng thức ăn (gram): Updating...");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.",error.toException());
            }
        });


        myData.child("Lan 2").child("Thoi gian cho an").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value = snapshot.getValue().toString();
                    tv_time_l2.setText("Thời gian cho ăn: " + value);
                }catch (NullPointerException e){
                    tv_time_l2.setText("Thời gian cho ăn: Updating...");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.",error.toException());
            }
        });
        myData.child("Lan 2").child("Khoi luong").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value = snapshot.getValue().toString();
                    tv_weight_l2.setText("Khối lượng thức ăn (gram): " + value);
                }catch (NullPointerException e){
                    tv_weight_l2.setText("Khối lượng thức ăn (gram): Updating...");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.",error.toException());
            }
        });

        myData.child("Lan 3").child("Thoi gian cho an").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value = snapshot.getValue().toString();
                    tv_time_l3.setText("Thời gian cho ăn: " + value);
                }catch (NullPointerException e){
                    tv_time_l3.setText("Thời gian cho ăn: Updating...");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.",error.toException());
            }
        });
        myData.child("Lan 3").child("Khoi luong").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String value=snapshot.getValue().toString();
                    tv_weight_l3.setText("Khối lượng thức ăn (gram): "+value);
                }catch (NullPointerException e){
                    tv_weight_l3.setText("Khối lượng thức ăn (gram): Updating...");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Failed to read value.",error.toException());
            }
        });

        return myView;
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }

//    public void setDesktopMode(WebView webView,boolean enabled) {
//        String newUserAgent = webView.getSettings().getUserAgentString();
//        if (enabled) {
//            try {
//                String ua = webView.getSettings().getUserAgentString();
//                String androidOSString = webView.getSettings().getUserAgentString().substring(ua.indexOf("("), ua.indexOf(")") + 1);
//                newUserAgent = webView.getSettings().getUserAgentString().replace(androidOSString, "(X11; Linux x86_64)");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            newUserAgent = null;
//        }
//
//        webView.getSettings().setUserAgentString(newUserAgent);
//        webView.getSettings().setUseWideViewPort(enabled);
//        webView.getSettings().setLoadWithOverviewMode(enabled);
//        webView.reload();
//    }
}
