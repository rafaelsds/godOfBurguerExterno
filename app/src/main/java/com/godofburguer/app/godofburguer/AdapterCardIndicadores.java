package com.godofburguer.app.godofburguer;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.godofburguer.app.godofburguer.entidades.Avaliacao;
import java.util.ArrayList;
import java.util.List;

public class AdapterCardIndicadores extends RecyclerView.Adapter<ViewIndicadores>{
    List<Avaliacao> list = new ArrayList<>();
    List<ViewIndicadores> myViewHoldersReference = new ArrayList<>();
    private Avaliacao avaliacao = new Avaliacao();

    public Avaliacao getAvaliacao(){
        avaliacao.setSatisfacao(myViewHoldersReference.get(0).getPontuacao());
        avaliacao.setQualidade(myViewHoldersReference.get(1).getPontuacao());
        avaliacao.setAgilidade(myViewHoldersReference.get(2).getPontuacao());

        return avaliacao;
    }

    public AdapterCardIndicadores() {}

    public void itemSet(String titulo, String descricao) {
        Avaliacao indicador = new Avaliacao();
        indicador.setTitulo(titulo);
        indicador.setDescricao(descricao);

        list.add(indicador);
    }

    @Override
    public ViewIndicadores onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_indicadores,viewGroup,false);
        return new ViewIndicadores(view);
    }

    public void onBindViewHolder(ViewIndicadores myViewHolder, int position) {
        Avaliacao myObject = list.get(position);
        myViewHoldersReference.add(myViewHolder);

        myViewHolder.bind(myObject);
        myViewHolder.tituloCard.setText(myObject.getTitulo());
        myViewHolder.descricaoCard.setText(myObject.getDescricao());

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
     }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
