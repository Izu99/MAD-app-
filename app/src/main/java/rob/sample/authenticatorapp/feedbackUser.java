package rob.sample.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;

import java.util.HashMap;
import java.util.Map;

public class feedbackUser extends AppCompatActivity implements View.OnClickListener {

    EditText txtid,txtName,txtAdd,txtConNo;
    Button btnSave,btnShow,btnUpdate,btnDelete;
    DatabaseReference dbRef,dbRef2;
    feedbackclass std;
    int count = 0;
    String countString,iString;
    String temp,temp2,temp3,temp4,itemp;
    Map<String,Object> updateMap;

    public static final String EXTRA_MESSAGE = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_user);

        txtid = (EditText) findViewById(R.id.txt1);
        txtName = (EditText)findViewById(R.id.txt2);
        txtAdd = (EditText)findViewById(R.id.txt3);
        txtConNo = (EditText)findViewById(R.id.txt4);

        btnSave = (Button)findViewById(R.id.btnAdd);
        btnShow = (Button)findViewById(R.id.btnShow);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        std = new feedbackclass();

        btnSave.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAdd:Save();
                break;
            case R.id.btnShow:Show();
                break;
            case R.id.btnDelete:Delete();
                break;
            case R.id.btnUpdate:Update();
                break;
        }
    }

    public void Delete(){
        //dbRef = FirebaseDatabase.getInstance().getReference().child("feedbackclass/Std2");
        // dbRef.removeValue();
        //Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();


        for(int i = 1; i <= count; i++) {
            //iString = String.valueOf(i);
            //itemp = "Std" + iString;
            dbRef2 = FirebaseDatabase.getInstance().getReference();
            Query deleteQuery = dbRef2.child("feedbackclass").orderByChild("id").equalTo(txtid.getText().toString().trim());
            deleteQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot deleteSnapshot: dataSnapshot.getChildren()) {
                        deleteSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    public void Update(){

        for(int i = 1; i <= count; i++){

            dbRef2 = FirebaseDatabase.getInstance().getReference();
            Query updateQuery = dbRef2.child("feedbackclass").orderByChild("id").equalTo(txtid.getText().toString().trim());
            updateMap = new HashMap<String,Object>();
            updateMap.put("name",txtName.getText().toString().trim());
            updateMap.put("address",txtAdd.getText().toString().trim());
            updateMap.put("id",txtid.getText().toString().trim());
            updateMap.put("conNo",txtConNo.getText().toString().trim());
            updateQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot updateSnapshot: dataSnapshot.getChildren()) {


                        updateSnapshot.getRef().updateChildren(updateMap);


                        //updateSnapshot.getRef().child("name").setValue(txtName.getText().toString().trim());
                        //updateSnapshot.getRef().child("address").setValue(txtAdd.getText().toString().trim());
                        //updateSnapshot.getRef().child("id").setValue(txtid.getText().toString().trim());
                        //updateSnapshot.getRef().child("conNo").setValue(txtConNo.getText().toString().trim());
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                    //if(dataSnapshot.hasChildren()){
                    //    if(txtid.getText().toString().trim().equals(dataSnapshot.child("id").getValue().toString().trim())){
                    //        dbRef2.child("feedbackclass").child(itemp).child("name").setValue(txtName.getText().toString().trim());
                    //        dbRef2.child("feedbackclass").child(itemp).child("address").setValue(txtAdd.getText().toString().trim());
                    //        dbRef2.child("feedbackclass").child(itemp).child("id").setValue(txtid.getText().toString().trim());
                    //       dbRef2.child("feedbackclass").child(itemp).child("conNo").setValue(txtConNo.getText().toString().trim());
                    //       Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    //       clearControls();
                    //     }
                    //}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        //dbRef.child("feedbackclass").child("Std2").child("name").setValue(txtName.getText().toString().trim());
        //dbRef.child("feedbackclass/Std2/address").setValue(txtAdd.getText().toString().trim());
        //dbRef.child("feedbackclass/Std2/id").setValue(txtid.getText().toString().trim());
        //dbRef.child("feedbackclass/Std2/conNo").setValue(txtConNo.getText().toString().trim());
        //dbRef.child("feedbackclass").child(temp).child("name").setValue(txtName.getText().toString().trim());
        //dbRef.child(temp2).setValue(txtAdd.getText().toString().trim());
        //dbRef.child(temp3).setValue(txtid.getText().toString().trim());
        //dbRef.child(temp4).setValue(txtConNo.getText().toString().trim());
        //Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
        //clearControls();
    }

    public void Show(){

        Intent intent = new Intent(this,ShowPage.class);
        intent.putExtra(EXTRA_MESSAGE, String.valueOf(count));
        startActivity(intent);


    }

    public void Save(){
        dbRef = FirebaseDatabase.getInstance().getReference().child("feedbackclass");
        try{
            if(TextUtils.isEmpty(txtid.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty id",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtName.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Name",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtAdd.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Address",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtConNo.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Contact No",Toast.LENGTH_SHORT).show();
            else{

                count += 1;
                countString = String.valueOf(count);
                temp = "Std" + countString;

                std.setID(txtid.getText().toString().trim());
                std.setName(txtName.getText().toString().trim());
                std.setAddress(txtAdd.getText().toString().trim());
                std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));
                dbRef.child(temp).setValue(std);
                //dbRef.child("Std2").setValue(std);
                Toast.makeText(getApplicationContext(),"Successfully Inserted",Toast.LENGTH_SHORT).show();
                clearControls();
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(getApplicationContext(),"Invalid Contact No",Toast.LENGTH_SHORT).show();
        }
    }

    private void clearControls(){
        txtid.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtConNo.setText("");
    }
}