package br.ruspotlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FinishSignUpActivity extends AppCompatActivity {

    private EditText mNicknameField;
    private Button mRegisterBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mDatabase.keepSynced(true);

        mNicknameField = (EditText) findViewById(R.id.text_nickname);
        mRegisterBtn = (Button) findViewById(R.id.btn_register);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserData();
            }
        });
    }

    private void addUserData() {
        String nickname = mNicknameField.getText().toString().trim();

        if(!TextUtils.isEmpty(nickname)) {
            String userId = mAuth.getCurrentUser().getUid();

            DatabaseReference userDB = mDatabase.child(userId);

            userDB.child("nickname").setValue(nickname);

            finish();
        }
    }
}
