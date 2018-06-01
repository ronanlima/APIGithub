package com.br.apigithub.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.apigithub.R;
import com.br.apigithub.beans.Issue;
import com.br.apigithub.beans.Pull;

import java.util.List;

/**
 * Created by rlima on 31/05/18.
 */

public class PullAdapter extends RecyclerView.Adapter<PullViewHolder> {
    private List<Pull> list;
    private Context context;

    public PullAdapter(List<Pull> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public PullViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_pull, parent, false);
        return new PullViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PullViewHolder holder, int position) {
        Pull i = list.get(position);
        holder.getTitulo().setText(i.getTitle());
        holder.getNumero().setText(i.getNumber().toString());
    }

    @Override
    public int getItemCount() {
        return list != null && !list.isEmpty() ? list.size() : 0;
    }
}

class PullViewHolder extends RecyclerView.ViewHolder {
    private TextView numero;
    private TextView titulo;
    private TextView descricao;

    public PullViewHolder(View itemView) {
        super(itemView);
        setTitulo((TextView) itemView.findViewById(R.id.titulo_pull));
        setNumero((TextView) itemView.findViewById(R.id.numero_pull));
    }

    public TextView getNumero() {
        return numero;
    }

    public void setNumero(TextView numero) {
        this.numero = numero;
    }

    public TextView getTitulo() {
        return titulo;
    }

    public void setTitulo(TextView titulo) {
        this.titulo = titulo;
    }

    public TextView getDescricao() {
        return descricao;
    }

    public void setDescricao(TextView descricao) {
        this.descricao = descricao;
    }
}
