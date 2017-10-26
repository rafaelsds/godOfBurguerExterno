package com.godofburguer.app.godofburguer;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.godofburguer.app.godofburguer.entidades.Indicador;
import java.util.ArrayList;
import java.util.List;

public class ViewIndicadores extends RecyclerView.ViewHolder implements OnClickListener{
    CardView cardView;
    TextView tituloCard;
    TextView descricaoCard;
    ImageButton btn1, btn2, btn3, btn4, btn5;
    List<ImageButton> btnList = new ArrayList<>();
    List<Integer> resultadoAvalicao = new ArrayList<>();

    public ViewIndicadores(View itemView){
        super(itemView);

        btn1 = (ImageButton) itemView.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (ImageButton) itemView.findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (ImageButton) itemView.findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (ImageButton) itemView.findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (ImageButton) itemView.findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        btnList.add(btn1);
        btnList.add(btn2);
        btnList.add(btn3);
        btnList.add(btn4);
        btnList.add(btn5);

        tituloCard = (TextView) itemView.findViewById(R.id.titulo_card);
        descricaoCard = (TextView) itemView.findViewById(R.id.descricao_card);
        cardView = (CardView) itemView.findViewById(R.id.card);
    }

    public void bind(Indicador r){
    }

    public void setSelectds(int pontos) {
        clearAllBtns();
        for (int i = 0; i < pontos; i++) {
            btnList.get(i).setBackgroundResource(R.drawable.icon_star_selected);
        }
    }

    public void clearAllBtns() {
        for (ImageButton btn : btnList){
            btn.setBackgroundResource(R.drawable.icon_star);
        }
    }

    @Override
    public void onClick(View v) {
        this.getAdapterPosition(); //Index do card selecionado
        setSelectds(Integer.parseInt(v.getTag().toString()));
    }
}