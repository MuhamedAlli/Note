package feed.r.note;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BodyNote extends AppCompatActivity {
        TextView textView1 , textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_note);
        textView1 = findViewById(R.id.show_title);
        textView2 = findViewById(R.id.show_note);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            textView1.setText(bundle.getString("title_key"));
            textView2.setText(bundle.getString("note_key"));
        }
    }
}