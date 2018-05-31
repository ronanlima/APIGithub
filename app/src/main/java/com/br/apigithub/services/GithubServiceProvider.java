package com.br.apigithub.services;

import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Pull;
import com.br.apigithub.interfaces.GithubEndpoints;
import com.br.apigithub.interfaces.IGithubServiceProvider;
import com.br.apigithub.interfaces.INotifyViewModelAboutService;
import com.br.apigithub.interfaces.RetrofitServiceContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rlima on 29/05/18.
 */

public class GithubServiceProvider implements IGithubServiceProvider {

    private RetrofitServiceContract service;

    public GithubServiceProvider(RetrofitServiceContract service) {
        this.service = service;
    }

    @Override
    public void retrieveRepo(String nameUser, String nameRepository, Integer page, Integer limit) {
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getEspecificRepoFromUser(nameUser, nameRepository).enqueue(new Callback<GithubRepository>() {
            @Override
            public void onResponse(Call<GithubRepository> call, Response<GithubRepository> response) {
                // mandar um objeto GithubRepository de volta no listener
            }

            @Override
            public void onFailure(Call<GithubRepository> call, Throwable t) {

            }
        });
    }

    @Override
    public void retrieveReposFromUser(String user, Integer page, Integer limit, final INotifyViewModelAboutService listener) {
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getReposFromUser(user).enqueue(new Callback<List<GithubRepository>>() {
            @Override
            public void onResponse(Call<List<GithubRepository>> call, Response<List<GithubRepository>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.returnListRepos(response.body());
                } else {
                    listener.returnListRepos(null);
                }
            }

            @Override
            public void onFailure(Call<List<GithubRepository>> call, Throwable t) {

            }
        });
    }

    @Override
    public void getInfoPullRequest(String fullNameRepo, Integer number) {
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getPullsFromRepo(fullNameRepo, number).enqueue(new Callback<Pull>() {
            @Override
            public void onResponse(Call<Pull> call, Response<Pull> response) {

            }

            @Override
            public void onFailure(Call<Pull> call, Throwable t) {

            }
        });
    }

    @Override
    public void getInfoIssue(String fullNameRepo, Integer number) {
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getIssuesFromRepo(fullNameRepo, number).enqueue(new Callback<Pull>() {
            @Override
            public void onResponse(Call<Pull> call, Response<Pull> response) {

            }

            @Override
            public void onFailure(Call<Pull> call, Throwable t) {

            }
        });
    }
}
