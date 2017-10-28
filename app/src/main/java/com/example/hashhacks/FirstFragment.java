package com.example.hashhacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FirstFragment extends AppCompatActivity {

    private static EditText height;
    private static EditText weight;
    private static EditText age;
    private static Spinner gender;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);
        gender = (Spinner) findViewById(R.id.gender);
        final Button button = (Button) findViewById(R.id.calculate);
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        String str1 = height.getText().toString();
                        String str2 = weight.getText().toString();
                        String str3 = age.getText().toString();
                        String str4 = gender.getSelectedItem().toString();

                        if (TextUtils.isEmpty(str1))
                        {
                            height.setError("Please enter your height");
                            height.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(str2))
                        {
                            weight.setError("Please enter your weight");
                            weight.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(str3))
                        {
                            age.setError("Please enter your age");
                            age.requestFocus();
                            return;
                        }

                        Double cal;
                        Double height = Double.parseDouble(str1);
                        Double weight = Double.parseDouble(str2);
                        Double age = Double.parseDouble(str3);
                        Double total = Math.ceil(calc(height, weight));

                        if(str4=="Female")
                            cal= ((4.7*height)+(4.35*weight)-(4.7*age))+655;
                        else
                            cal=((12.7*height)+(6.23*weight)-(6.8*age))+66;

                        Intent i = new Intent(getBaseContext(), First2.class);
                        i.putExtra("bmi", total);
                        i.putExtra("intake", Math.ceil(cal));
                        getBaseContext().startActivity(i);


                    }
                });
    }

            public Double calc (Double height, Double weight)
            {
                Double h=(height*0.025);
                Double w=(weight*0.45);
                return (Double) (w / (h * h));
            }
}
