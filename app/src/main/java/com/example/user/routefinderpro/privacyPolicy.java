package com.example.user.routefinderpro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class privacyPolicy extends DialogFragment {

    Button Yes;
//    CheckBox agreeCheckBox;
    TinyDB tinydb;
    public static String PrivacyUrl = "http://www.khastech.com/privacy.html";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, getActivity().getApplicationInfo().theme);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {

            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        tinydb = new com.example.user.routefinderpro.TinyDB(getActivity());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon(R.mipmap.ic_launcher)
                // Set Dialog Title
                .setTitle(getResources().getString(R.string.app_name));
        builder.setMessage(getResources().getString(R.string.optin_message));
        builder.setPositiveButton(getActivity().getResources().getString(R.string.optin_yesbtn),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
//        builder.setNegativeButton(getActivity().getResources().getString(R.string.optin_nobtn),
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    }
//                });

        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.frg_privacy_policy, null);

        TextView clickLink = (TextView) v.findViewById(R.id.link_tv);
        clickLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(PrivacyUrl));
                startActivity(i);
            }
        });

//        agreeCheckBox = (CheckBox) v.findViewById(R.id.agreement_check);
//        agreeCheckBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);

    }

    @Override
    public void onStart() {
        super.onStart();
        Yes = ((AlertDialog) getDialog())
                .getButton(AlertDialog.BUTTON_POSITIVE);
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDialog().dismiss();
               /* if (agreeCheckBox.isChecked()) {
                    getDialog().dismiss();
                    //save data in shared preffrences and show privacOpt button
                    //value 1 for agreed 0 for not agreed
                    tinydb.putInt(AdsId.PRIVACY_KEY, 1);
                } else {
                    Toast.makeText(getActivity(), "Please tick the required fied if agreed on terms and conditions", Toast.LENGTH_LONG).show();
                }*/
            }
        });
//        No = ((AlertDialog) getDialog())
//                .getButton(AlertDialog.BUTTON_NEGATIVE);
//        No.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tinydb.putInt(AdsId.PRIVACY_KEY, 0);
//                getDialog().dismiss();
//                getActivity().finish();
//                //save data in shared preffrences and exit application
//            }
//        });
    }
}
