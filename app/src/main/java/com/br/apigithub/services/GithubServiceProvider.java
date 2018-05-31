package com.br.apigithub.services;

import android.support.annotation.NonNull;

import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Issue;
import com.br.apigithub.beans.Pull;
import com.br.apigithub.interfaces.GithubEndpoints;
import com.br.apigithub.interfaces.IGithubServiceProvider;
import com.br.apigithub.interfaces.INotifyViewModelAboutService;
import com.br.apigithub.interfaces.RetrofitServiceContract;

import java.util.ArrayList;
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
    public void retrieveRepo(String nameUser, String nameRepository, Integer page, Integer limit, final INotifyViewModelAboutService listener) {
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getEspecificRepoFromUser(nameUser, nameRepository).enqueue(new Callback<GithubRepository>() {
            @Override
            public void onResponse(Call<GithubRepository> call, Response<GithubRepository> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.returnRepo(response.body());
                } else {
                    listener.returnRepo(null);
                }
            }

            @Override
            public void onFailure(Call<GithubRepository> call, Throwable t) {
                listener.notifyOnError(t);
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
                listener.notifyOnError(t);
            }
        });
    }

    @Override
    public void getIssues(String userName, String nameRepository, final INotifyViewModelAboutService listener) {
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getIssuesFromRepo(userName, nameRepository).enqueue(new Callback<List<Issue>>() {
            @Override
            public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Issue> list = getRealIssues(response);
                    listener.returnIssues(list);
                } else {
                    listener.returnIssues(null);
                }
            }

            @Override
            public void onFailure(Call<List<Issue>> call, Throwable t) {
                listener.notifyOnError(t);
            }
        });
    }

    /**
     * De acordo com a documentação do github, um pull request é considerado uma issue. Sendo assim,
     * se o elemento não contiver pull_request, ele é uma issue legítima.
     * @param response
     * @return
     */
    @NonNull
    private List<Issue> getRealIssues(Response<List<Issue>> response) {
        List<Issue> list = new ArrayList<>();
        for (Issue issue : response.body()) {
            if (issue.getPull_request() == null) {
                list.add(issue);
            }
        }
        return list;
    }

    @Override
    public void getPulls(String userName, String nameRepository, Integer page, Integer limit, final INotifyViewModelAboutService listener) {
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getPullsFromRepo(userName, nameRepository, page, limit).enqueue(new Callback<List<Pull>>() {
            @Override
            public void onResponse(Call<List<Pull>> call, Response<List<Pull>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.returnPulls(response.body());
                } else {
                    listener.returnPulls(null);
                }
            }

            @Override
            public void onFailure(Call<List<Pull>> call, Throwable t) {
                listener.notifyOnError(t);
            }
        });
    }

    @Override
    public void getInfoPullRequest(String fullNameRepo, Integer number) {
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getSinglePull(fullNameRepo, number).enqueue(new Callback<Pull>() {
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
        service.getRetrofitService().getRetrofit().create(GithubEndpoints.class).getSingleIssue(fullNameRepo, number).enqueue(new Callback<Issue>() {
            @Override
            public void onResponse(Call<Issue> call, Response<Issue> response) {

            }

            @Override
            public void onFailure(Call<Issue> call, Throwable t) {

            }
        });
    }
}
