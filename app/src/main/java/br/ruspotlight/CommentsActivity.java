package br.ruspotlight;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.ruspotlight.domain.Comment;

public class CommentsActivity extends AppCompatActivity {

    private RecyclerView commentsList;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("comments");

        commentsList = (RecyclerView) findViewById(R.id.comments_list);
        commentsList.setHasFixedSize(true);
        commentsList.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog;
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(CommentsActivity.this);
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
                                    CommentsActivity.this,
                                    "Insira a mensagem do comentário.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference newCommentRef = mDatabase.push();
                            newCommentRef.setValue(new Comment(commentContent));
                                    //child("content").setValue(commentContent);
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

        FirebaseRecyclerAdapter<Comment, CommentViewHolder> adapter = new FirebaseRecyclerAdapter<Comment, CommentViewHolder>(
                Comment.class,
                R.layout.card_comment,
                CommentViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(CommentViewHolder viewHolder, Comment model, int position) {
                final String commentKey = getRef(position).getKey();

                viewHolder.setContent(model.content);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent commentIntent = new Intent(CommentsActivity.this, SingleCommentActivity.class);
                        commentIntent.putExtra("comment_key", commentKey);
                        startActivity(commentIntent);
                    }
                });
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
