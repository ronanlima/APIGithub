package com.br.apigithub.interfaces;

/**
 * Created by rlima on 29/05/18.
 */

public interface IGithubServiceProvider {
    void retrieveRepo(String nameUser, String nameRepository, Integer page, Integer limit);

    void retrieveReposFromUser(String user, Integer page, Integer limit, INotifyViewModelAboutService notifyViewModelAboutService);

    void getInfoPullRequest(String fullNameRepo, Integer number);

    void getInfoIssue(String fullNameRepo, Integer number);
}
