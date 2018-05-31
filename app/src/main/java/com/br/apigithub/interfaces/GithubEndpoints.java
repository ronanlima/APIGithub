package com.br.apigithub.interfaces;

import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Issue;
import com.br.apigithub.beans.Pull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by rlima on 29/05/18.
 */

public interface GithubEndpoints {
    @GET("users/{user}/repos")
    Call<List<GithubRepository>> getReposFromUser(@Path("user") String user);

    @Headers({
            "User-Agent: com.br.apigithub"
    })
    @GET("repos/{user}/{repo}")
    Call<GithubRepository> getEspecificRepoFromUser(@Path("user") String user, @Path("repo") String repo);

    @Headers({
            "User-Agent: com.br.apigithub"
    })
    @GET("/repos/{user}/{repo}/issues")
    Call<List<Issue>> getIssuesFromRepo(@Path("user") String user, @Path("repo") String repo);

    @Headers({
            "User-Agent: com.br.apigithub"
    })
    @GET("/repos/{user}/{repo}/pulls")
    Call<List<Pull>> getPullsFromRepo(@Path("user") String user, @Path("repo") String repo, @Query("page") Integer page, @Query("per_page") Integer perPage);

    @Headers({
            "User-Agent: com.br.apigithub"
    })
    @GET("{fullNameRepo}/pulls/{number}")
    Call<Pull> getSinglePull(@Path("fullNameRepo") String repo, @Path("number") Integer number);

    @Headers({
            "User-Agent: com.br.apigithub"
    })
    @GET("{fullNameRepo}/issues/{number}")
    Call<Issue> getSingleIssue(@Path("fullNameRepo") String repo, @Path("number") Integer number);
}
