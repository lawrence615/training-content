package com.mobidev.mpesa;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by lawrence on 7/1/15.
 */
public class MpesaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSendMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa);


        btnSendMoney = (Button) findViewById(R.id.send_money);
        btnSendMoney.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_money:
//                new ConfirmDialogFragment().show(getSupportFragmentManager(), "");
                startActivity(new Intent(getApplicationContext(), SendMoneyActivity.class));
                break;
            default:
                break;
        }
    }


    public static class ConfirmDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Send ksh200 to 0723384122")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent login = new Intent(getActivity(),
                                            MainActivity.class);
                                    startActivity(login);
                                    getActivity().finish();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    // User cancelled the dialog
                                }
                            });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
