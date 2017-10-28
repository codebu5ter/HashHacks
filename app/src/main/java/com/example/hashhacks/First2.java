package com.example.hashhacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class First2 extends AppCompatActivity {

    EditText bmi;
    EditText consume;
    TextView yourvalue;
    Double a,b;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first2_layout);

        bmi= (EditText) findViewById(R.id.bmi);
        consume= (EditText)findViewById(R.id.consume);
        yourvalue= (TextView)findViewById(R.id.yourvalue);

        Intent ii=getIntent();
        Bundle ib=ii.getExtras();
        a=(Double) ib.get("bmi");
        b = (Double) ib.get("intake");
        bmi.setText(String.valueOf(a));

        int imageResource = interpretBMI(a);
        ImageView image= (ImageView)findViewById(R.id.image);
        String suggestion = result(a);
        yourvalue.setText(String.valueOf(suggestion));
        consume.setText(String.valueOf(b));
    }

    private int interpretBMI(Double value)
    {

        if (value < 18.5)
        {
            String uri = "@drawable/underweight";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            return imageResource;

        }
        else if (value < 25)
        {
            String uri = "@drawable/normalweight";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            return imageResource;
        }
        else if (value < 30)
        {
            String uri = "@drawable/overweight";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            return imageResource;
        }
        else
        {
            String uri = "@drawable/obese";
            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            return imageResource;
        }
    }

    private String result(Double value)
    {

        if (value < 18.5)
        {
            return "\n \n \n You are Underweight. Eat more";
        }
        else if (value < 25)
        {
            return "\n \n \n You are Normal. Maintain it.";
        }
        else if (value < 30)
        {
            return "\n \n \n You are Overweight. Start doing work out.";
        }
        else
        {
            return "\n \n \n You are Obese. Consult a doctor.";
        }
    }

    public void onclickhome(View v) {
        Intent intent = new Intent(First2.this, MainActivity.class);
        startActivity(intent);
    }

}
