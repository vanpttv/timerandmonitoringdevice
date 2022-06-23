package pttv.com.example.timerandmonitoringdevice.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

import pttv.com.example.timerandmonitoringdevice.R;

public class Lan2Fragment extends Fragment {
    Button btn_ok_l2;
    Button btn_set_time;
    TextView edt_time_l2;
    EditText edt_weight_l2;
    DatabaseReference myData;
    Integer Hour, Minute;
    View myView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_lan2,container,false);
        btn_ok_l2=myView.findViewById(R.id.ok_l2);
        btn_set_time=myView.findViewById(R.id.btn_set_time);
        edt_time_l2=myView.findViewById(R.id.time_l2);
        edt_weight_l2=myView.findViewById(R.id.weight_l2);
        myData = FirebaseDatabase.getInstance().getReference();

        btn_set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Hour=hourOfDay;
                                Minute=minute;
                                edt_time_l2.setText(String.format(Locale.getDefault(),"%02d:%02d", Hour,Minute));
                            }
                        },
                        24,0, true);
                timePickerDialog.show();

            }
        });

        btn_ok_l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDatatoFirebase();
                Toast.makeText(getActivity(),"Cài đặt thành công",Toast.LENGTH_SHORT).show();
            }

            private void sendDatatoFirebase() {
                if(Hour!=null && Minute!=null){
                    myData.child("Lan 2").child("Thoi gian cho an").setValue(String.format(Locale.getDefault(),"%02d:%02d", Hour, Minute));
                }else{
                    myData.child("Lan 2").child("Thoi gian cho an").setValue("No data");
                }
                String khoi_luong = edt_weight_l2.getText().toString().trim();
                if(khoi_luong.isEmpty()){
                    myData.child("Lan 2").child("Khoi luong").setValue("No data");
                }else{
                    Integer kl= Integer.parseInt(khoi_luong.replaceAll("[\\D]", ""));
                    myData.child("Lan 2").child("Khoi luong").setValue(kl);
                }
            }
        });
        return myView;
    }
}
