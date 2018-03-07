package com.matt.coffeeapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private Button submitBtn;
    private ImageButton upButton;
    private ImageButton downButton;
    private Button clearButton;

    private TextView numOfCups;
    private CheckBox teaBox;
    private CheckBox coffeeBox;
    private CheckBox expressoBox;
    private LinearLayout checkBoxContainer;
    private int counter = 1;
    final double NYS_TAX = .08625;
    private double pricePerCup;
    static String body;
    static String header;
    static double subtotalVar;
    static double taxVar;
    static double totalVar;
    DecimalFormat precision = new DecimalFormat("0.00");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        submitBtn = (Button) findViewById(R.id.submitButton);
        upButton = (ImageButton) findViewById(R.id.upButton);
        downButton = (ImageButton) findViewById(R.id.downButton);
        clearButton = (Button) findViewById(R.id.clearButton);

        numOfCups = (TextView) findViewById(R.id.numberOfCupsBox);
        teaBox = findViewById(R.id.teaBox);
        coffeeBox = findViewById(R.id.coffeeBox);
        expressoBox = findViewById(R.id.expressoBox);

        checkBoxContainer = (LinearLayout) findViewById(R.id.checkBoxContainer);

        numOfCups.setText("1");


        final Beverage expresso = new Beverage("Expresso", "small", 3.49);
        final Beverage coffee = new Beverage("Coffee", "small", 1.99);
        final Beverage tea = new Beverage("Tea", "small", 1.49);




        upButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
         counter++;
         numOfCups.setText(String.valueOf(counter));
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
         if (counter != 0) {
           counter--;
           numOfCups.setText(String.valueOf(counter));
              }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Beverage temp = new Beverage();
                int numOfCupsVar = Integer.parseInt(String.valueOf(numOfCups.getText()));
                if (Validation.validateOrder(coffeeBox.isChecked(),teaBox.isChecked(), expressoBox.isChecked())) {

                    if (coffeeBox.isChecked()) {
                        pricePerCup = coffee.getPrice();
                        temp = coffee;

                    } else if (teaBox.isChecked()) {
                        pricePerCup = tea.getPrice();
                        temp = tea;

                    } else if (expressoBox.isChecked()) {
                        pricePerCup = expresso.getPrice();
                        temp = expresso;

                    }

                     subtotalVar = numOfCupsVar * pricePerCup;
                     taxVar = subtotalVar * NYS_TAX;
                     totalVar = subtotalVar + taxVar;




                    header = numOfCupsVar + " cup(s) of " + temp.getName();
                    body = "You ordered " + numOfCupsVar + " cup(s) of " + temp.getName() + "\n" + "\n" +
                            "Subtotal: " + "$" + String.valueOf(precision.format(subtotalVar)) + "\n" + "Tax: " + ("$" + String.valueOf(precision.format(taxVar)))
                            + "\n" + "Total " + ("$" + String.valueOf(precision.format(totalVar)));


                    startActivity(new Intent(MainActivity.this, EmailActivity.class));


//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:j117dev@gmail.com"));
//
//                intent.putExtra(Intent.EXTRA_EMAIL, "j117dev@gmail.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order");
//                intent.putExtra(Intent.EXTRA_TEXT, "You ordered " +numOfCupsVar + " cups of " + temp.getName() + "\n" +
//                "Subtotal: " + "$" + String.valueOf(precision.format(subtotalVar)) + "\n" + "Tax: " +  ("$" + String.valueOf(precision.format(taxVar)))
//                + "\n" + "Total " + ("$" + String.valueOf(precision.format(totalVar))));
//
//                startActivity(Intent.createChooser(intent, "Send Email"));
                } else {
                    checkBoxContainer.setBackgroundResource(R.drawable.border);

                }

            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 1;
                numOfCups.setText(String.valueOf(counter));
                teaBox.setChecked(false);
                expressoBox.setChecked(false);
                coffeeBox.setChecked(false);

            }
        });

    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        checkBoxContainer.setBackgroundResource(0);

        switch(view.getId()) {
            case R.id.coffeeBox:
                if (checked)
                    teaBox.setChecked(false);
                expressoBox.setChecked(false);
                break;
            case R.id.expressoBox:
                if (checked)
                    coffeeBox.setChecked(false);
                teaBox.setChecked(false);
                break;
            case R.id.teaBox:
                if (checked) {
                    expressoBox.setChecked(false);
                    coffeeBox.setChecked(false);
                    break;
                }
        }
    }


}
