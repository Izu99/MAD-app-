package rob.sample.authenticatorapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShowPage extends AppCompatActivity implements View.OnClickListener{

    TextView txtShow;
    DatabaseReference dbRef;
    Button btnshow;
    String count,iString,itemp;
    int countInt;
    DatabaseReference dbRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_page);

        txtShow = findViewById(R.id.txt_show);
        btnshow = findViewById(R.id.btnShow);

        btnshow.setOnClickListener(this);

        Intent intent = getIntent();
        count = intent.getStringExtra(feedbackUser.EXTRA_MESSAGE);
        countInt = Integer.parseInt(count);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnShow:Show();
                break;
        }
    }

    public void Show(){

        for(int i = 1; i <= countInt ; i++){
            iString = String.valueOf(i);
            itemp = "fb" + iString;
            dbRef2 = FirebaseDatabase.getInstance().getReference().child("feedbackclass").child(itemp);
            dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChildren()){
                        txtShow.append(dataSnapshot.child("name").getValue().toString() + "\n"+ dataSnapshot.child("email").getValue().toString() + "\n"
                                +dataSnapshot.child("feedback").getValue().toString() + "\n"+dataSnapshot.child("conNo").getValue().toString() + "\n\n");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }

}