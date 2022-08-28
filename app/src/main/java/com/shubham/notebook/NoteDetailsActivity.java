package com.shubham.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NoteDetailsActivity extends AppCompatActivity {

    EditText titleEditText,contentEditText;
    TextView saveNoteBtn;
//    ImageButton saveNoteBtn;
    TextView pageTitleTextView, deleteButton;

    String title,content,docId;
    boolean isEditMode = false;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titleEditText = findViewById(R.id.notes_title_text);
        contentEditText = findViewById(R.id.notes_content_text);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        pageTitleTextView= findViewById(R.id.page_title);
        deleteButton= findViewById(R.id.delete_button);

        deleteButton.setOnClickListener((v)->deleteNote());

        //receive from adapter
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("docId");

        if (docId!=null&&!docId.isEmpty()){
            isEditMode=true;
        }

        titleEditText.setText(title);
        contentEditText.setText(content);
        if (isEditMode){
            pageTitleTextView.setText("Edit your note");
            deleteButton.setVisibility(View.VISIBLE);
        }


         saveNoteBtn.setOnClickListener((v)->saveNote());

    }

    void saveNote(){
        String noteTitle = titleEditText.getText().toString();
        String noteContent = contentEditText.getText().toString();

        if (noteTitle==null||noteTitle.isEmpty()){
            titleEditText.setError("Title is required");
            return;
        }

        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveNoteToFirebase(note);

    }


    void saveNoteToFirebase(Note note){
        DocumentReference documentReference;
        if (isEditMode){
            //update
            documentReference = utility.getCollectionReferenceForNotes().document(docId);
        }else {
            //create
            documentReference = utility.getCollectionReferenceForNotes().document();
        }

        documentReference.set(note).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                //note add
                utility.showToast(NoteDetailsActivity.this,"success");
                finish();
            }else {
                utility.showToast(NoteDetailsActivity.this,"Failed");
            }
        });
    }

   void deleteNote(){
       DocumentReference documentReference;
           documentReference = utility.getCollectionReferenceForNotes().document(docId);
       documentReference.delete().addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               //note delete
               utility.showToast(NoteDetailsActivity.this,"success");
               finish();
           }else {
               utility.showToast(NoteDetailsActivity.this,"Failed");
           }
       });
   }

}