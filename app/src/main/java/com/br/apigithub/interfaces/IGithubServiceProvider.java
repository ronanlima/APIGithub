package com.br.apigithub.interfaces;

/**
 * Created by rlima on 29/05/18.
 */

public interface IGithubServiceProvider {
    void retrieveRepo(String userName, String nameRepository, Integer page, Integer limit, INotifyViewModelAboutService listener);

    void retrieveReposFromUser(String user, Integer page, Integer limit, INotifyViewModelAboutService notifyViewModelAboutService);

    void getIssues(String userName, String nameRepository, INotifyViewModelAboutService listener);

    void getPulls(String userName, String nameRepository, Integer page, Integer limit, INotifyViewModelAboutService listener);

    void getInfoPullRequest(String fullNameRepo, Integer number);

    void getInfoIssue(String fullNameRepo, Integer number);
}
