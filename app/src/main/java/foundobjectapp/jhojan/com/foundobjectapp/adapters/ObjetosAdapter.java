package foundobjectapp.jhojan.com.foundobjectapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import foundobjectapp.jhojan.com.foundobjectapp.R;
import foundobjectapp.jhojan.com.foundobjectapp.activities.DetallesActivity;
import foundobjectapp.jhojan.com.foundobjectapp.models.Objeto;

/**
 * Created by JShanksX13 on 2/04/2017.
 */

public class ObjetosAdapter extends RecyclerView.Adapter<ObjetosAdapter.ObjetosviewHolder>{

        private Activity activity;
        List<Objeto> objetoList;
        Context ctx;

        public ObjetosAdapter(List<Objeto> objetoList, Context ctx) {
                this.objetoList = objetoList;
                this.ctx = ctx;
        }

        public ObjetosAdapter(Activity activity, List<Objeto> objetos){
                this.activity = activity;
        }

        @Override
        public ObjetosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objeto, parent, false);
                ObjetosviewHolder holder = new ObjetosviewHolder(v, ctx, (ArrayList<Objeto>) objetoList);
                return holder;
        }

        @Override
        public void onBindViewHolder(ObjetosviewHolder holder, int position) {
                final Objeto objeto = objetoList.get(position);
                holder.nameObjeto.setText(objeto.getObjeto());
                //holder.imageView.setImageURI(Uri.parse(objeto.getImagen().toString()));
        }

        @Override
        public int getItemCount() {
                return objetoList.size();
        }

        public static class ObjetosviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

                TextView nameObjeto;
                //ImageView imageView;
                ArrayList<Objeto> objetos = new ArrayList<Objeto>();
                Context ctx;

                public ObjetosviewHolder(View itemView, Context ctx, ArrayList<Objeto> objetos) {
                        super(itemView);
                        this.objetos = objetos;
                        this.ctx = ctx;
                        itemView.setOnClickListener(this);
                        nameObjeto = (TextView) itemView.findViewById(R.id.fullname_text);
                        //imageView = (ImageView) itemView.findViewById(R.id.picture_image);

                }

                @Override
                public void onClick(View view) {
                        int position = getAdapterPosition();
                        Objeto objeto = this.objetos.get(position);
                        Intent intent = new Intent(this.ctx, DetallesActivity.class);
                        intent.putExtra("ID", objeto.getId());
                        intent.putExtra("OBJETO", objeto.getObjeto());
                        intent.putExtra("FECHA", objeto.getFecha());
                        intent.putExtra("LUGAR", objeto.getLugar());
                        intent.putExtra("DESCRIP", objeto.getDescripción());
                        intent.putExtra("IMG", objeto.getImagen());
                        this.ctx.startActivity(intent);
                }
        }
}