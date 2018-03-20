package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private Button showButton;
    private Button saveButton;
    private Button cancelButton;

    private EditText nameText;
    private EditText phoneText;
    private EditText emailText;
    private EditText addressText;
    private EditText jobText;
    private EditText companyText;
    private EditText websiteText;
    private EditText imText;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            LinearLayout ll = (LinearLayout)findViewById(R.id.show_hide_layout);
            int visibilityStatus;
            if (view.getId() == R.id.show_hide_button) {
                visibilityStatus = ll.getVisibility();
                if (visibilityStatus == View.VISIBLE) {
                    visibilityStatus = View.INVISIBLE;
                    showButton.setText("SHOW additional fields");
                }
                else {
                    visibilityStatus = View.VISIBLE;
                    showButton.setText("HIDE additional fields");
                }
                ll.setVisibility(visibilityStatus);
            }
            else if (view.getId() == R.id.save_button) {
                String name = nameText.getText().toString();
                String phone = phoneText.getText().toString();
                String email = emailText.getText().toString();
                String address = addressText.getText().toString();
                String job = jobText.getText().toString();
                String company = companyText.getText().toString();
                String website = websiteText.getText().toString();
                String im = imText.getText().toString();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (job != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            }
            else if (view.getId() == R.id.cancel_button) {
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        showButton = (Button)findViewById(R.id.show_hide_button);
        saveButton = (Button)findViewById(R.id.save_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);

        nameText = (EditText)findViewById(R.id.textName);
        phoneText = (EditText)findViewById(R.id.textPhone);
        emailText = (EditText)findViewById(R.id.textEmail);
        addressText = (EditText)findViewById(R.id.textAddress);
        jobText = (EditText)findViewById(R.id.textJob);
        companyText = (EditText)findViewById(R.id.textCompany);
        websiteText = (EditText)findViewById(R.id.textWebsite);
        imText = (EditText)findViewById(R.id.textIM);

        showButton.setOnClickListener(buttonClickListener);
        saveButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

}
