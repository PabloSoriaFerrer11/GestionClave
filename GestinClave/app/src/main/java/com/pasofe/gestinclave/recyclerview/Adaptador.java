package com.pasofe.gestinclave.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.pasofe.gestinclave.R;
import com.pasofe.gestinclave.accesoadatos.sqlAplicacion;

import java.util.Arrays;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    private final String[] meses = {"Enero", "Febrero", "Marzo", "Abril","Mayo", "Junio",
                                    "Julio", "Agosto", "Septiembre", "Octubre", "Noviebre", "Diciembre"};
    private double[] detalleIngreso = new double[12];
    private double[] detalleGasto = new double[12];

    private sqlAplicacion claseSQL;

    //
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        claseSQL = new sqlAplicacion(v.getContext());
        detalleIngreso = claseSQL.cogerIngresoTodosMeses();
        detalleGasto = claseSQL.cogerGastoTodosMeses();

        System.out.println(Arrays.toString(detalleGasto));
        System.out.println(Arrays.toString(detalleIngreso));
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextMes.setText(meses[position]);
        holder.mTextDescIngreso.setText(String.valueOf(detalleIngreso[position]) + " €");
        holder.mTextDescGasto.setText(String.valueOf(detalleGasto[position]) + " €");

    }


    //
    @Override
    public int getItemCount() {
        return meses.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextMes, mTextDescIngreso, mTextDescGasto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextMes = itemView.findViewById(R.id.textViewMes);
            mTextDescIngreso = itemView.findViewById(R.id.textViewIngresos);
            mTextDescGasto = itemView.findViewById(R.id.textViewGastos);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    //Por la posición sabriamos cual ha tocado
                }
            });
        }
    }
}
