package com.matt.coffeeapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import static com.matt.coffeeapp.MainActivity.subtotalVar;
import static com.matt.coffeeapp.MainActivity.taxVar;
import static com.matt.coffeeapp.MainActivity.totalVar;

public class EmailActivity extends AppCompatActivity {
    private EditText email;
    private TextView topText;
    private String sender = "emailtester9037@gmail.com";
    private String password = "emailtester";
    private String subject = "Coffee Order";
    private Button submit;
    private  Button doneButton;
    private android.app.AlertDialog alertDialog;
    private android.app.AlertDialog.Builder builder;
    private ImageView checkMark;
    private RelativeLayout totalLayout;
    private TextView subtotal;
    private TextView tax;
    private TextView total;
    private TextView sentText;
    private TextView headerText;
    private ImageButton backBtn;
    static String user;
    DecimalFormat precision = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        this.getSupportActionBar().hide();

        headerText = findViewById(R.id.headerText);
        doneButton = (Button) findViewById(R.id.doneButton);
        submit = (Button) findViewById(R.id.submit);
        topText = findViewById(R.id.topText);
        email = (EditText) findViewById(R.id.emailBox);
        totalLayout = (RelativeLayout) findViewById(R.id.totalLayout);
        checkMark = (ImageView) findViewById(R.id.checkMark);
        doneButton.setVisibility(View.INVISIBLE);
        checkMark.setVisibility(View.INVISIBLE);
        subtotal = (TextView) findViewById(R.id.subtotalBox);
        tax = (TextView) findViewById(R.id.taxBox);
        total = (TextView) findViewById(R.id.totalBox);
        sentText = (TextView) findViewById(R.id.sentText);
        backBtn = (ImageButton) findViewById(R.id.backBtn);

        sentText.setVisibility(View.INVISIBLE);

        headerText.setText(MainActivity.header);
        subtotal.setText("$" + String.valueOf(precision.format(subtotalVar)));
        tax.setText("$" + String.valueOf(precision.format(taxVar)));
        total.setText("$" + String.valueOf(precision.format(totalVar)));

        backBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        finish();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        startActivity(new Intent(EmailActivity.this, MainActivity.class));            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = email.getText().toString();

                if (Validation.validateEmail(user)) {

                    sendMessage();
                    Animation fadeIn = AnimationUtils.loadAnimation(EmailActivity.this, R.anim.fade_in);
                    checkMark.startAnimation(fadeIn);
                    sentText.startAnimation(fadeIn);
                    checkMark.setVisibility(View.VISIBLE);
                    doneButton.setVisibility(View.VISIBLE);
                    sentText.setVisibility(View.VISIBLE);
                    topText.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.INVISIBLE);
                    email.setVisibility(View.INVISIBLE);
                    totalLayout.setVisibility(View.INVISIBLE);

                } else {
            Animation shake = AnimationUtils.loadAnimation(EmailActivity.this,R.anim.shake);
            email.startAnimation(shake);
            email.setText("");
            email.setHintTextColor(Color.RED);
                }

            }
        });

    }

    private void sendMessage() {
        String[] users = {user.toString()};
        SendEmailAsyncTask email = new SendEmailAsyncTask();
        email.activity = this;
        email.m = new GMailSender(sender, password);
        email.m.set_from(sender);
        email.m.setBody(MainActivity.body);
        email.m.set_to(users);
        email.m.set_subject(subject);
        email.execute();
    }

}
class SendEmailAsyncTask extends AsyncTask<Void, Void, Boolean> {
    GMailSender m;
    EmailActivity activity;

    public SendEmailAsyncTask() {}


    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            if (m.send()) {
//                displayMessage("Email sent.");
            } else {
//              displayMessage("Email failed to send.");
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();
//            displayMessage("Authentication failed.");
            return false;
        } catch (MessagingException e) {
            Log.e(SendEmailAsyncTask.class.getName(), "Email failed");
            e.printStackTrace();
//            displayMessage("Email failed to send.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
//            displayMessage("Unexpected error occured.");
            return false;
        }
    }
}
