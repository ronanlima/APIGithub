package com.br.apigithub.aac;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.br.apigithub.MyApplication;
import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Issue;
import com.br.apigithub.beans.Pull;
import com.br.apigithub.interfaces.IGithubServiceProvider;
import com.br.apigithub.interfaces.INotifyViewModelAboutService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by rlima on 30/05/18.
 */

public class RepositoryViewModel extends ViewModel implements INotifyViewModelAboutService {
    @Inject
    IGithubServiceProvider serviceProvider;
    private MutableLiveData<GithubRepository> githubLiveData;
    private MutableLiveData<List<Issue>> issuesLiveData;
    private MutableLiveData<List<Pull>> pullsLiveData;
    private String userName;
    private String repo;

    public void init() {
        MyApplication.getComponent().inject(this);
    }

    public void searchReposFromUser(String userName, Integer page, Integer limit) {
        serviceProvider.retrieveReposFromUser(userName, page, limit, this);
    }

    public void searchRepo(String userName, String repo, Integer page, Integer limit) {
        this.userName = userName;
        this.repo = repo;
        serviceProvider.retrieveRepo(userName, repo, page, limit, this);
    }

    public MutableLiveData<GithubRepository> getGithubLiveData() {
        if (githubLiveData == null) {
            githubLiveData = new MutableLiveData<>();
        }
        return githubLiveData;
    }

    public MutableLiveData<List<Issue>> getIssuesLiveData() {
        if (issuesLiveData == null) {
            issuesLiveData = new MutableLiveData<>();
        }
        return issuesLiveData;
    }

    public MutableLiveData<List<Pull>> getPullsLiveData() {
        if (pullsLiveData == null) {
            pullsLiveData = new MutableLiveData<>();
        }
        return pullsLiveData;
    }

    @Override
    public void returnListRepos(List<GithubRepository> list) {
        githubLiveData.postValue(list.get(0));
    }

    @Override
    public void returnRepo(GithubRepository repository) {
        getGithubLiveData().postValue(repository);
        serviceProvider.getIssues(userName, repo, this);
    }

    @Override
    public void returnIssues(List<Issue> issues) {
        getIssuesLiveData().postValue(issues);
        serviceProvider.getPulls(userName, repo, 1, 6, this);
    }

    @Override
    public void returnPulls(List<Pull> pulls) {
        getPullsLiveData().postValue(pulls);
    }

    @Override
    public void notifyOnError(Throwable throwable) {

    }
}
