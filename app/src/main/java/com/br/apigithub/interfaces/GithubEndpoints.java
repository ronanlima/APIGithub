package com.br.apigithub.interfaces;

import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Pull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rlima on 29/05/18.
 */

public interface GithubEndpoints {
    @GET("users/{user}/repos")
    Call<List<GithubRepository>> getReposFromUser(@Path("user") String user);

    @GET("repos/{user}/{repo}")
    Call<GithubRepository> getEspecificRepoFromUser(@Path("user") String user, @Path("repo") String repo);

    @GET("{fullNameRepo}/pulls/{number}")
    Call<Pull> getPullsFromRepo(@Path("fullNameRepo") String repo, @Path("number") Integer number);

    @GET("{fullNameRepo}/issues/{number}")
    Call<Pull> getIssuesFromRepo(@Path("fullNameRepo") String repo, @Path("number") Integer number);
}
