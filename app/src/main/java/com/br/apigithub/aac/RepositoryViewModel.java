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
    private MutableLiveData<String> msgError;
    private String userName;
    private String repo;

    public void init() {
        MyApplication.getComponent().inject(this);
    }

    public void searchReposFromUser(String userName, Integer page, Integer limit) {
        serviceProvider.retrieveReposFromUser(userName, page, limit, this);
    }

    public void searchRepo(String userNameRepo, Integer page, Integer limit) {
        String aux[] = userNameRepo.split("/");
        userName = aux[0];
        repo = aux[1];
        serviceProvider.retrieveRepo(userName, repo, page, limit, this);
    }

    public void updateIssues(Integer page, Integer limit) {
        serviceProvider.getIssues(userName, repo, page, limit, true,this);
    }

    public void updatePulls(Integer page, Integer limit) {
        serviceProvider.getPulls(userName, repo, page, limit, true, this);
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

    public MutableLiveData<String> getMsgError() {
        if (msgError == null) {
            msgError = new MutableLiveData<>();
        }
        return msgError;
    }

    @Override
    public void returnListRepos(List<GithubRepository> list) {
        githubLiveData.postValue(list.get(0));
    }

    @Override
    public void returnRepo(GithubRepository repository, Integer page, Integer limit) {
        getGithubLiveData().postValue(repository);
        serviceProvider.getIssues(userName, repo, page, limit, false,this);
    }

    @Override
    public void returnIssues(List<Issue> issues, Integer page, Integer limit) {
        getIssuesLiveData().postValue(issues);
        serviceProvider.getPulls(userName, repo, page, limit, false, this);
    }

    @Override
    public void updateIssues(List<Issue> issues) {
        List<Issue> oldValue = getIssuesLiveData().getValue();
        oldValue.addAll(issues);
        getIssuesLiveData().postValue(oldValue);
    }

    @Override
    public void updatePulls(List<Pull> pulls) {
        List<Pull> oldValue = getPullsLiveData().getValue();
        oldValue.addAll(pulls);
        getPullsLiveData().postValue(oldValue);
    }

    @Override
    public void returnPulls(List<Pull> pulls) {
        getPullsLiveData().postValue(pulls);
    }

    @Override
    public void notifyOnError(Throwable throwable) {
        getMsgError().postValue(throwable.getMessage());
    }
}
