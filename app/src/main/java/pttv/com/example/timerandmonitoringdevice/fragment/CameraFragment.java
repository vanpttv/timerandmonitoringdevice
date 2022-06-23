package pttv.com.example.timerandmonitoringdevice.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import pttv.com.example.timerandmonitoringdevice.R;

public class CameraFragment extends Fragment {
    View myView;
    Button btn_on_camera;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_camera,container,false);

        btn_on_camera=myView.findViewById(R.id.on_camera);

        btn_on_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.1.109:81/stream"));
                startActivity(intent);
            }
        });
        return myView;
    }
}
