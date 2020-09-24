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
import java.util.HashMap;
import java.util.Map;

public class feedbackUser extends AppCompatActivity implements View.OnClickListener {

    EditText txtName,txtEmail,txtFeedback;
    Button btnSave,btnShow,btnUpdate,btnDelete;
    DatabaseReference dbRef,dbRef2;
    feedbackclass fb;
    int count = 0;
    String countString;
    String temp;
    Map<String,Object> updateMap;

    public static final String EXTRA_MESSAGE = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_user);

        txtName = (EditText) findViewById(R.id.txt1);
        txtEmail = (EditText)findViewById(R.id.txt2);
        txtFeedback = (EditText)findViewById(R.id.txt3);

        btnSave = (Button)findViewById(R.id.btnAdd);
        btnShow = (Button)findViewById(R.id.btnShow);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        fb = new feedbackclass();

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


        for(int i = 1; i <= count; i++) {

            dbRef2 = FirebaseDatabase.getInstance().getReference();
            Query deleteQuery = dbRef2.child("feedbackclass").orderByChild("name").equalTo(txtName.getText().toString().trim());
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
            Query updateQuery = dbRef2.child("feedbackclass").orderByChild("name").equalTo(txtName.getText().toString().trim());
            updateMap = new HashMap<>();
            updateMap.put("name",txtName.getText().toString().trim());
            updateMap.put("email",txtEmail.getText().toString().trim());
            updateMap.put("feedback",txtFeedback.getText().toString().trim());

            updateQuery.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot updateSnapshot: dataSnapshot.getChildren()) {


                        updateSnapshot.getRef().updateChildren(updateMap);

                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void Show(){

        Intent intent = new Intent(this,ShowPage.class);
        intent.putExtra(EXTRA_MESSAGE, String.valueOf(count));
        startActivity(intent);
    }

    public void Save(){
        dbRef = FirebaseDatabase.getInstance().getReference().child("feedbackclass");
        try{
            if(TextUtils.isEmpty(txtName.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Name",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtEmail.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Email",Toast.LENGTH_SHORT).show();
            else if(TextUtils.isEmpty(txtFeedback.getText().toString()))
                Toast.makeText(getApplicationContext(),"Empty Feedback",Toast.LENGTH_SHORT).show();
            else{

                count += 1;
                countString = String.valueOf(count);
                temp = "fb" + countString;

                fb.setName(txtName.getText().toString().trim());
                fb.setEmail(txtEmail.getText().toString().trim());
                fb.setFeedback(txtFeedback.getText().toString().trim());
                dbRef.child(temp).setValue(fb);
                Toast.makeText(getApplicationContext(),"Successfully Inserted",Toast.LENGTH_SHORT).show();
                clearControls();
            }
        } catch (NumberFormatException nfe) {
            Toast.makeText(getApplicationContext(),"Invalid Contact No",Toast.LENGTH_SHORT).show();
        }
    }

    private void clearControls(){
        txtName.setText("");
        txtEmail.setText("");
        txtFeedback.setText("");
    }
}