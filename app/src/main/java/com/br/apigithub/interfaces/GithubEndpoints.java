package com.br.apigithub.interfaces;

import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Pull;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rlima on 29/05/18.
 */

public interface GithubEndpoints {
    @GET("users/{user}/repos")
    Call<GithubRepository> getReposFromUser(@Query("user") String user);

    @GET("repos/{user}/{repo}")
    Call<GithubRepository> getEspecificRepoFromUser(@Query("user") String user, @Query("repo") String repo);

    @GET("{fullNameRepo}/pulls/{number}")
    Call<Pull> getPullsFromRepo(@Query("fullNameRepo") String repo, @Query("number") Integer number);

    @GET("{fullNameRepo}/issues/{number}")
    Call<Pull> getIssuesFromRepo(@Query("fullNameRepo") String repo, @Query("number") Integer number);
}
