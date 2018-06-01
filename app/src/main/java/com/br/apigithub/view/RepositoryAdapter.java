package com.br.apigithub.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rlima on 31/05/18.
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {
    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class RepositoryViewHolder extends RecyclerView.ViewHolder {
    private TextView repoName;
    private TextView userName;
    private TextView totalIssues;
    private TextView totalPulls;

    public RepositoryViewHolder(View itemView) {
        super(itemView);
//        repoName = itemView.findViewById(R.id.);
    }

    public TextView getRepoName() {
        return repoName;
    }

    public void setRepoName(TextView repoName) {
        this.repoName = repoName;
    }

    public TextView getUserName() {
        return userName;
    }

    public void setUserName(TextView userName) {
        this.userName = userName;
    }

    public TextView getTotalIssues() {
        return totalIssues;
    }

    public void setTotalIssues(TextView totalIssues) {
        this.totalIssues = totalIssues;
    }

    public TextView getTotalPulls() {
        return totalPulls;
    }

    public void setTotalPulls(TextView totalPulls) {
        this.totalPulls = totalPulls;
    }
}
