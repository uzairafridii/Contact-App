package com.example.contactlistapp;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactListView = findViewById(R.id.contactList);
        contactListView.setLayoutManager(new LinearLayoutManager(this));

        loadContacts();

    }

    private void loadContacts() {
        Cursor cursor;
        ArrayList<MyContact> arrayList = new ArrayList<>();


            cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (number.length() > 0) {
                        Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                                new String[]{id}, null);

                        if ((phoneCursor.getCount() > 0)) {
                            while (phoneCursor.moveToNext()) {
                                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                MyContact myContact = new MyContact(name, phoneNumber);
                                arrayList.add(myContact);



                            }
                        }
                        phoneCursor.close();


                    }

                }

                // set adapter
                MyContactAdapter adapter = new MyContactAdapter(this, arrayList);
                contactListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } else {
                Toast.makeText(this, "No Contact is found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();





    }
}
