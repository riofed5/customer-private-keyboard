package com.example.privatekeyboard.Helpers;

import android.graphics.Bitmap;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.util.UUID;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRUtils {

    public static String connectedUuid = null;
    public static String newUuid = null;
    private static final String baseWebAppUrl = "https://lively-stone-01c8fc003.azurestaticapps.net/";
    // Deployment web app URL: https://lively-stone-01c8fc003.azurestaticapps.net/
    // Development web app URL (example): http://192.168.1.149:3000/

    public static void SetNewQRBitmap(ImageView qrImage, LinearLayout formLinearLayout) {
        newUuid = UUID.randomUUID().toString();
        Log.d("NewUUID", newUuid);
        String settings = GenerateQRQuery(formLinearLayout);
        Log.d("InputSettings", settings);
        QRGEncoder qrgEncoder = new QRGEncoder(baseWebAppUrl + "?settings=" + settings + "&uuid=" + newUuid, null, QRGContents.Type.TEXT, 550);
        try {
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private static String GenerateQRQuery(LinearLayout layout) {
        StringBuilder query = new StringBuilder("[");
        for (int i = 0; i < layout.getChildCount() -1; i++) {
            LinearLayout fieldLayout = (LinearLayout) layout.getChildAt(i);
            String fieldTag = (String) layout.getChildAt(i).getTag();
            if (!fieldTag.equals("hidden")) {

                query.append("{");
                query.append("\"position\":\"").append(i).append("\",");
                if (fieldLayout.getChildAt(1) instanceof EditText) {
                    AddTextFieldJsonSetting(fieldLayout, query);
                } else if (fieldLayout.getChildAt(1) instanceof RadioGroup) {
                    AddRadioGroupJsonSetting(fieldLayout, query);
                }

                query.append("}");

                if (i < layout.getChildCount() - 1) {
                    query.append(",");
                }
            }
        }
        if (query.length() > 0) {
            query.deleteCharAt(query.length() - 1);
        }
        query.append("]");
        Log.d("QUERY", query.toString());
        return CipherDecrypt.Encrypt(query.toString());
    }

    private static void AddTextFieldJsonSetting(LinearLayout layout, StringBuilder query) {
        TextView label = (TextView) layout.getChildAt(0);
        EditText input = (EditText) layout.getChildAt(1);

        if (input.getInputType() == InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PERSON_NAME) {
            query.append("\"type\":\"text\",");
        } else if (input.getInputType() == InputType.TYPE_CLASS_PHONE) {
            query.append("\"type\":\"tel\",");
        } else if (input.getInputType() == InputType.TYPE_DATETIME_VARIATION_DATE + InputType.TYPE_CLASS_DATETIME) {
            query.append("\"type\":\"date\",");
        } else if (input.getInputType() == InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
            query.append("\"type\":\"email\",");
        }

        query.append("\"label\":\"").append(label.getText()).append("\",");
        CharSequence hint = input.getHint() == null ? "" : input.getHint();
        query.append("\"placeholder\":\"").append(hint).append("\"");
    }

    private static void AddRadioGroupJsonSetting(LinearLayout layout, StringBuilder query) {
        RadioGroup radioGroup = (RadioGroup) layout.getChildAt(1);
        TextView mainLabel = (TextView) layout.getChildAt(0);
        query.append("\"label\":\"").append(mainLabel.getText()).append("\",");
        query.append("\"type\":\"radio\",");
        query.append("\"group\":\"").append(radioGroup.getTag().toString()).append("\",");
        query.append("\"radioButtons\":[");

        for (int j = 0; j < radioGroup.getChildCount(); j++) {
            RadioButton button = (RadioButton) radioGroup.getChildAt(j);
            query.append("{");
            query.append("\"label\":\"").append(button.getText()).append("\",");
            query.append("\"isChecked\":").append(button.isChecked());
            query.append("}");

            if (j < radioGroup.getChildCount() - 1) {
                query.append(",");
            }
        }
        query.append("]");
    }
}