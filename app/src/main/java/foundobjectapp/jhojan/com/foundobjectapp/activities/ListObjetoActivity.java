package foundobjectapp.jhojan.com.foundobjectapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import foundobjectapp.jhojan.com.foundobjectapp.R;
import foundobjectapp.jhojan.com.foundobjectapp.adapters.ObjetosAdapter;
import foundobjectapp.jhojan.com.foundobjectapp.models.Objeto;

public class ListObjetoActivity extends AppCompatActivity {

    RecyclerView rvObjeto;

    List<Objeto> objetos;

    ObjetosAdapter adapter;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_objeto);

        rvObjeto = (RecyclerView) findViewById(R.id.objetoList);

        rvObjeto.setLayoutManager(new LinearLayoutManager(this));
        rvObjeto.setLayoutManager(new GridLayoutManager(this, 2));
        rvObjeto.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        objetos = new ArrayList<>();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        adapter = new ObjetosAdapter(objetos, this);

        rvObjeto.setAdapter(adapter);

        mDatabase.getReference().child("Objetos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                objetos.removeAll(objetos);
                for (DataSnapshot snapshot:
                        dataSnapshot.getChildren()){
                    Objeto objeto = snapshot.getValue(Objeto.class);
                    objetos.add(objeto);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("", "No se encontraron objetos", databaseError.toException());
            }
        });

//        rvObjeto.setOnClickListener(new AdapterView.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
//                intent.putExtra("ID", id);
//                startActivity(intent);
//            }
//        });

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
