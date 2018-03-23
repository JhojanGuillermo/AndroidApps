package foundobjectapp.jhojan.com.foundobjectapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import foundobjectapp.jhojan.com.foundobjectapp.R;

public class DetallesActivity extends AppCompatActivity {

    private static final String TAG = DetallesActivity.class.getSimpleName();

    private String Id;

    private String Objeto;

    private String Fecha;

    private String Lugar;

    private String Descripción;

    TextView objeto;
    TextView fecha;
    TextView lugar;
    TextView descripción;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        id = (TextView) findViewById(R.id.idText);
        objeto = (TextView) findViewById(R.id.objeto_text);
        fecha = (TextView) findViewById(R.id.fechaText);
        lugar = (TextView) findViewById(R.id.lugarText);
        descripción = (TextView) findViewById(R.id.descText);

       // FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

//        if (this.getIntent().getExtras() != null){
//            Bundle bundle = this.getIntent().getExtras();
//            id = bundle.getInt("ID");
//        }
        objeto.setText(getIntent().getStringExtra("OBJETO"));
        id.setText(getIntent().getStringExtra("ID"));
        fecha.setText(getIntent().getStringExtra("FECHA"));
        lugar.setText(getIntent().getStringExtra("LUGAR"));
        descripción.setText(getIntent().getStringExtra("DESCRIP"));
        //objeto.setText(getIntent().getStringExtra("IMG"));


//        mDatabase.getReference().child("Objetos").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String nameobjeto = dataSnapshot.getValue(String.class);
//                objeto.setText(nameobjeto);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }
}
