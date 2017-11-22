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
import android.widget.Button;
import android.widget.TextView;

import com.godofburguer.app.godofburguer.controller.SincronizaBancoWs;
import com.godofburguer.app.godofburguer.entidades.Lanche;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CarrinhoActivity extends Activity {

    private RecyclerView recyclerView;
    private Intent intent;
    private List<Lanche> list;
    private Button btnFinalizar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);
        inicialise();
        botoes();
    }

    @Override
    public void onBackPressed(){
        Intent it = new Intent(CarrinhoActivity.this, PedidosActivity.class);
        startActivity(it);
        finish();
    }


    public void inicialise(){
        intent = getIntent();
        list = new ArrayList<>();

        if(intent.getSerializableExtra("lanche") != null){
            Lanche i = (Lanche) intent.getSerializableExtra("lanche");
            list.add(i);
        }

        btnFinalizar = (Button)findViewById(R.id.btnFinalizarPedido);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerCarrinho);
        recyclerView.setLayoutManager(new LinearLayoutManager(CarrinhoActivity.this));
        recyclerView.setAdapter(new CarrinhoActivity.NotesAdapter(CarrinhoActivity.this,list));

    }

    public void botoes(){
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(CarrinhoActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Pedido finalizado com sucesso!")
                        .setConfirmText("Ok")
                        .showCancelButton(false)

                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                                Intent it = new Intent(CarrinhoActivity.this, AvaliacaoActivity.class);
                                startActivity(it);
                                finish();
                            }
                        })
                        .show();
            }
        });
    }

    private class NotesAdapter extends RecyclerView.Adapter<CarrinhoActivity.NotesAdapter.ViewHolder> {

        private List<Lanche> mNotes;
        private Context mContext;
        AlertDialog alerta;


        private NotesAdapter(Context context, List<Lanche> notes) {
            mNotes = notes;
            mContext = context;
        }


        @Override
        public CarrinhoActivity.NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View notesView = inflater.inflate(R.layout.card_lanches_carrinho, parent, false);

            CarrinhoActivity.NotesAdapter.ViewHolder viewHolder = new CarrinhoActivity.NotesAdapter.ViewHolder(notesView);
            return viewHolder;
        }


        private Context getContext() {
            return mContext;
        }

        @Override
        public void onBindViewHolder(CarrinhoActivity.NotesAdapter.ViewHolder viewHolder, final int position) {

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


            final TextView valor = viewHolder.valorUnitario;
            valor.setText(String.valueOf(notes.getValor()));

            final TextView valorTotal = viewHolder.valorTotal;
            valorTotal.setText(valor.getText());

            final TextView qtCarrinho = viewHolder.qtCarrinho;

            viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer qt = Integer.parseInt(qtCarrinho.getText().toString())+1;
                    qtCarrinho.setText(qt.toString());

                    DecimalFormat df = new DecimalFormat("#.##");
                    Float vlTotal = Float.parseFloat(valor.getText().toString()) * (Integer.parseInt(qtCarrinho.getText().toString()));
                    valorTotal.setText(df.format(vlTotal).toString());
                }
            });


            viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(qtCarrinho.getText().toString()) >1){
                        Integer qt = Integer.parseInt(qtCarrinho.getText().toString())-1;
                        qtCarrinho.setText(qt.toString());

                        DecimalFormat df = new DecimalFormat("#.##");
                        Float vlTotal = Float.parseFloat(valor.getText().toString()) * (Integer.parseInt(qtCarrinho.getText().toString()));
                        valorTotal.setText(df.format(vlTotal).toString());
                    }
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
            TextView lanche, insumos, valorUnitario, valorTotal, observacaoCarrinho, qtCarrinho;
            CardView card;
            Button btnAdd, btnRemove;

            public ViewHolder(View itemView) {
                super(itemView);

                observacaoCarrinho = (TextView)itemView.findViewById(R.id.tvtObservacaoCarrinho);
                lanche = (TextView)itemView.findViewById(R.id.descricaoLancheCarrinho);
                insumos = (TextView)itemView.findViewById(R.id.tvtInsumosCarrinho);
                valorTotal = (TextView)itemView.findViewById(R.id.tvtValorTotalLancheCarrinho);
                valorUnitario = (TextView)itemView.findViewById(R.id.tvtValorLancheCarrinho);
                qtCarrinho = (TextView)itemView.findViewById(R.id.tvtQtCarrinho);

                card = (CardView)itemView.findViewById(R.id.cardCarrinho);
                btnAdd = (Button)itemView.findViewById(R.id.btnAddCarrinho);
                btnRemove = (Button)itemView.findViewById(R.id.btnRemoveCarrinho);

            }
        }
    }

}
