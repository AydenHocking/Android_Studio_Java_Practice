package com.example.tip_calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
/*
Ayden Hocking
Assignment 1
MainActivity.java
 */
public class MainActivity extends AppCompatActivity {
    TextView textViewPercent;
    TextView textViewTotalBill;
    EditText billPrice;
    TextView textViewTipPrice;
    String billPriceText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        billPrice = findViewById(R.id.editTextNumberBillPrice);
        textViewPercent = findViewById(R.id.textViewTipPercentNumber);
        textViewTotalBill = findViewById(R.id.textViewTotalBill);
        textViewTipPrice = findViewById(R.id.textViewTipPrice);
        findViewById(R.id.button10Percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double billPriceNumber = billNumberTest(billPrice);
                if (billPriceNumber != -1 && billPriceNumber<=99999999){
                    double tipPriceNumber = billPriceNumber * .10;
                    billPriceNumber = billPriceNumber * 1.10;
                    textViewTotalBill.setText(String.format("$%.2f",billPriceNumber));
                    textViewPercent.setText("10%");
                    textViewTipPrice.setText(String.format("$%.2f",tipPriceNumber));
                }else{
                    Toast.makeText(MainActivity.this, "Invalid Number Format : Too Large", Toast.LENGTH_LONG).show();
                }

            }

        });
        findViewById(R.id.button15Percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double billPriceNumber = billNumberTest(billPrice);
                if (billPriceNumber != -1 && billPriceNumber<=99999999){
                    double tipPriceNumber = billPriceNumber * .15;
                    billPriceNumber = billPriceNumber * 1.15;
                    textViewTotalBill.setText(String.format("$%.2f",billPriceNumber));
                    textViewPercent.setText("15%");
                    textViewTipPrice.setText(String.format("$%.2f",tipPriceNumber));
                }else{
                    Toast.makeText(MainActivity.this, "Invalid Number Format : Too Large", Toast.LENGTH_LONG).show();
                }

            }

        });
        findViewById(R.id.button18Percent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double billPriceNumber = billNumberTest(billPrice);
                if (billPriceNumber != -1 && billPriceNumber<=99999999){
                    double tipPriceNumber = billPriceNumber * .18;
                    billPriceNumber = billPriceNumber * 1.18;
                    textViewTotalBill.setText(String.format("$%.2f",billPriceNumber));
                    textViewPercent.setText("18%");
                    textViewTipPrice.setText(String.format("$%.2f",tipPriceNumber));
                }else{
                    Toast.makeText(MainActivity.this, "Invalid Number Format : Too Large", Toast.LENGTH_LONG).show();
                }

            }

        });
        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewPercent.setText("");
                textViewTipPrice.setText("");
                billPrice.setText("");
                textViewTotalBill.setText("");
            }
        });

    }
    public double billNumberTest(EditText BillPrice) {
        billPriceText = billPrice.getText().toString();
        if (!billPriceText.isEmpty()){
            try{
                return Double.parseDouble(billPriceText);
            } catch (NumberFormatException e){
                Toast.makeText(MainActivity.this, "Invalid Number Format : Empty", Toast.LENGTH_LONG).show();
                return -1;
            }
        }else {
            Toast.makeText(MainActivity.this, "Invalid Number Format : Empty", Toast.LENGTH_LONG).show();
            return -1;
        }
    }
}