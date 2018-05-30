package com.br.apigithub.services;

import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Pull;
import com.br.apigithub.interfaces.GithubEndpoints;
import com.br.apigithub.interfaces.GithubRepositoryContract;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rlima on 29/05/18.
 */

public class GithubService implements GithubRepositoryContract {
    @Inject
    RetrofitService service;

    @Override
    public void retrieveRepo(String nameUser, String nameRepository, Integer page, Integer limit) {
        service.getRetrofit().create(GithubEndpoints.class).getEspecificRepoFromUser(nameUser, nameRepository).enqueue(new Callback<GithubRepository>() {
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
    public void retrieveReposFromUser(String user, Integer page, Integer limit) {
        service.getRetrofit().create(GithubEndpoints.class).getReposFromUser(user).enqueue(new Callback<GithubRepository>() {
            @Override
            public void onResponse(Call<GithubRepository> call, Response<GithubRepository> response) {
                // mandar um objeto List<GithubRepository> de volta no listener
            }

            @Override
            public void onFailure(Call<GithubRepository> call, Throwable t) {

            }
        });
    }

    @Override
    public void getInfoPullRequest(String fullNameRepo, Integer number) {
        service.getRetrofit().create(GithubEndpoints.class).getPullsFromRepo(fullNameRepo, number).enqueue(new Callback<Pull>() {
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
        service.getRetrofit().create(GithubEndpoints.class).getIssuesFromRepo(fullNameRepo, number).enqueue(new Callback<Pull>() {
            @Override
            public void onResponse(Call<Pull> call, Response<Pull> response) {

            }

            @Override
            public void onFailure(Call<Pull> call, Throwable t) {

            }
        });
    }
}
