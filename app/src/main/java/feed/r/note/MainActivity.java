package feed.r.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addNewNote;
    Button addToDatabase;
    private DatabaseReference myRef ;
    private FirebaseDatabase firebaseDatabase;
    ListView listView;
    List<Note> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       addNewNote = findViewById(R.id.add_new_note);
       listView = findViewById(R.id.note_list_view);
        addToDatabase =findViewById(R.id.add_to_database);

        list = new ArrayList<>();

        // Write a message to the database

         firebaseDatabase = FirebaseDatabase.getInstance();
       myRef = firebaseDatabase.getReference("Notes");

        ///////////
        addNewNote.setOnClickListener(new View.OnClickListener() {
        ////////////////
            @Override
            public void onClick(View v)
            {
                showDialogNote();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this , BodyNote.class);
                intent.putExtra("title_key" ,list.get(position).getTitle());
                intent.putExtra("note_key" ,list.get(position).getNote());
                startActivity(intent);
                //Toast.makeText(MainActivity.this ,list.get(position).getNote() , Toast.LENGTH_LONG).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                final View view1 =  layoutInflater.inflate(R.layout.update_nate, null , false);
                //Creating dialog box

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setView(view1);
                alertDialog.show();

                ///
                final Note note22= list.get(position);
                final EditText ti = view1.findViewById(R.id.titleedit_text);
                ti.setText(note22.getTitle());
                final EditText no = view1.findViewById(R.id.noteedit_text);
                no.setText(note22.getNote());

                Button update = view1.findViewById(R.id.update_to_database);
                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    DatabaseReference myChild =  myRef.child(note22.getId());
                        String x = ti.getText().toString();
                        String y = no.getText().toString();
                        Note nn = new Note(getCurrentDate() , note22.getId() , y ,x);
                        myChild.setValue(nn);
                        alertDialog.dismiss();
                    }
                });
                Button deleteNote = view1.findViewById(R.id.delete_to_database);
                deleteNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef.child(note22.getId()).removeValue();
                        alertDialog.dismiss();
                    }
                });

                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add(0, snapshot1.getValue(Note.class));
                }

                MyAdapter adapter = new MyAdapter(MainActivity.this ,0 , list);
                listView.setAdapter(adapter);
              //  Toast.makeText(MainActivity.this,, Toast.LENGTH_LONG);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Error in Retreive Data" , error.getMessage());
            }
        });
        //////////

    }
    void showDialogNote()
    {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view =  layoutInflater.inflate(R.layout.add_note, null , false);
            addToDatabase = view.findViewById(R.id.add_to_database);

        //Creating dialog box

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        alertDialog.show();
        /////////////////////
        addToDatabase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText ti = view.findViewById(R.id.title_edit_text);
                    String title = ti.getText().toString();
                    EditText no = view.findViewById(R.id.note_edit_text);
                    String note = no.getText().toString();

                    if(title.isEmpty() || note.isEmpty())
                    {
                        Toast.makeText(MainActivity.this , "Empty" , Toast.LENGTH_LONG).show();

                    }
                    else
                    {

                        String id = myRef.push().getKey();
                       myRef.child(id).child("Id").setValue(id);
                       myRef.child(id).child("Title").setValue(title);
                       myRef.child(id).child("Note").setValue(note);
                        myRef.child(id).child("Date").setValue(getCurrentDate());
                        ///
                        alertDialog.dismiss();
                        //Toast.makeText(MainActivity.this , "Key " ,Toast.LENGTH_LONG ).show();
                    }

                }

            });
        ///////////


    }
    public String getCurrentDate()
    {
        Calendar currentTime = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("EEEE HH:mm a");
        String strDate = df.format(currentTime.getTime());
        return strDate;
    }
}
