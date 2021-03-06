package br.ruspotlight;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.ruspotlight.domain.Comment;

public class SingleCommentActivity extends AppCompatActivity {

    private String commentKey = null;

    private RecyclerView commentsList;

    private DatabaseReference mDatabase;

    private TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        commentKey = getIntent().getExtras().getString("comment_key");

        mDatabase = FirebaseDatabase.getInstance().getReference().child("comments").child(commentKey);

        textContent = (TextView) findViewById(R.id.single_comment_content);
        commentsList = (RecyclerView) findViewById(R.id.comments_inner_list);
        commentsList.setHasFixedSize(true);
        commentsList.setLayoutManager(new LinearLayoutManager(this));

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String commentContent = (String) dataSnapshot.child("content").getValue();

                textContent.setText(commentContent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog;
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(SingleCommentActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_comment, null);

                final EditText mComment = (EditText) mView.findViewById(R.id.text_comment);
                Button mButton = (Button) mView.findViewById(R.id.btn_add_comment);

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String commentContent = mComment.getText().toString().trim();
                        if(commentContent.isEmpty()) {
                            Toast.makeText(
                                    SingleCommentActivity.this,
                                    "Insira a mensagem do comentário.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference newCommentRef = mDatabase.child("responses").push();
                            newCommentRef.child("content").setValue(commentContent);
                            dialog.hide();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Comment, SingleCommentActivity.CommentViewHolder> adapter = new FirebaseRecyclerAdapter<Comment, SingleCommentActivity.CommentViewHolder>(
                Comment.class,
                R.layout.card_comment,
                SingleCommentActivity.CommentViewHolder.class,
                mDatabase.child("responses")
        ) {
            @Override
            protected void populateViewHolder(SingleCommentActivity.CommentViewHolder viewHolder, Comment model, int position) {
                final String commentKey = getRef(position).getKey();

                viewHolder.setContent(model.content);
            }
        };

        commentsList.setAdapter(adapter);
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public CommentViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setContent(String content) {
            TextView textContent = (TextView) mView.findViewById(R.id.comment_content);
            textContent.setText(content);
        }
    }
}
