package foundobjectapp.jhojan.com.foundobjectapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import foundobjectapp.jhojan.com.foundobjectapp.R;
import foundobjectapp.jhojan.com.foundobjectapp.models.Objeto;

/**
 * Created by JShanksX13 on 2/04/2017.
 */

public class ObjetosAdapter extends RecyclerView.Adapter<ObjetosAdapter.ObjetosviewHolder>{

        List<Objeto> objetoList;

        public ObjetosAdapter(List<Objeto> objetoList) {
                this.objetoList = objetoList;
        }

        @Override
        public ObjetosviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objeto, parent, false);
                ObjetosviewHolder holder = new ObjetosviewHolder(v);
                return holder;
        }

        @Override
        public void onBindViewHolder(ObjetosviewHolder holder, int position) {
                Objeto objeto = objetoList.get(position);
                holder.nameObjeto.setText(objeto.getObjeto());
        }

        @Override
        public int getItemCount() {
                return objetoList.size();
        }

        public static class ObjetosviewHolder extends RecyclerView.ViewHolder{

                TextView nameObjeto;

                public ObjetosviewHolder(View itemView) {
                        super(itemView);
                        nameObjeto = (TextView) itemView.findViewById(R.id.fullname_text);

                }
        }
}