package com.godofburguer.app.godofburguer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.godofburguer.app.godofburguer.controller.SincronizaBancoWs;
import com.godofburguer.app.godofburguer.entidades.Lanche;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PedidosActivity extends Activity {

    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_pedido);
        inicialise();
    }

    @Override
    public void onBackPressed(){
        finish();
    }


    public void inicialise(){

        SincronizaBancoWs.atualizarLanches(new SincronizaBancoWs.CallBack<List<Lanche>>(){
            @Override
            public void call(List<Lanche> objeto){
                recyclerView = (RecyclerView)findViewById(R.id.recyclerRealizarPedido);
                recyclerView.setLayoutManager(new LinearLayoutManager(PedidosActivity.this));
                recyclerView.setAdapter(new PedidosActivity.NotesAdapter(PedidosActivity.this,objeto));
            }
        }, PedidosActivity.this);

    }


    private class NotesAdapter extends RecyclerView.Adapter<PedidosActivity.NotesAdapter.ViewHolder> {

        private List<Lanche> mNotes;
        private Context mContext;
        AlertDialog alerta;


        private NotesAdapter(Context context, List<Lanche> notes) {
            mNotes = notes;
            mContext = context;
        }


        @Override
        public PedidosActivity.NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View notesView = inflater.inflate(R.layout.card_lanches, parent, false);

            PedidosActivity.NotesAdapter.ViewHolder viewHolder = new PedidosActivity.NotesAdapter.ViewHolder(notesView);
            return viewHolder;
        }


        private Context getContext() {
            return mContext;
        }

        @Override
        public void onBindViewHolder(PedidosActivity.NotesAdapter.ViewHolder viewHolder, final int position) {

            final Lanche notes = mNotes.get(position);

            TextView lanche = viewHolder.lanche;
            lanche.setText(notes.getNome());

            TextView insumos = viewHolder.insumos;
            insumos.setText(notes.getInsumos());

            if(notes.getInsumos()!=null && !notes.getInsumos().isEmpty()){
                insumos.setVisibility(View.VISIBLE);
            }else{
                insumos.setVisibility(View.GONE);
            }

            TextView valor = viewHolder.valor;
            valor.setText("R$"+String.valueOf(notes.getValor()));

            TextView tipoLanche = viewHolder.tipoLanche;
            tipoLanche.setText(notes.getTipoLanche());

            viewHolder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(PedidosActivity.this, CarrinhoActivity.class);
                    it.putExtra("lanche", notes);
                    startActivity(it);
                    finish();
                }
            });

        }


        private void Vibrar(){
            Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long milliseconds = 50;
            rr.vibrate(milliseconds);
        }



        @Override
        public int getItemCount() {
            return mNotes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView lanche, insumos, valor, tipoLanche;
            CardView card;

            public ViewHolder(View itemView) {
                super(itemView);

                tipoLanche = (TextView)itemView.findViewById(R.id.tipoLancheCard);
                lanche = (TextView)itemView.findViewById(R.id.descricaoLancheCard);
                insumos = (TextView)itemView.findViewById(R.id.descricaoInsumosCard);
                valor = (TextView)itemView.findViewById(R.id.tvtValorCard);
                card = (CardView)itemView.findViewById(R.id.cardLanche);
            }
        }
    }

}
