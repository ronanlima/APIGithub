package com.br.apigithub.aac;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.interfaces.IGithubServiceProvider;
import com.br.apigithub.interfaces.INotifyViewModelAboutService;

import java.util.List;

/**
 * Created by rlima on 30/05/18.
 */

public class RepositoryViewModel extends ViewModel implements INotifyViewModelAboutService {
    private MutableLiveData<List<GithubRepository>> reposGithubLiveData;
    private IGithubServiceProvider serviceProvider;

    public void init(IGithubServiceProvider service) {
        this.serviceProvider = service;
    }

    public void searchReposFromUser(String userName, Integer page, Integer limit) {
        serviceProvider.retrieveReposFromUser(userName, page, limit, this);
    }

    public MutableLiveData<List<GithubRepository>> getReposGithubLiveData() {
        if (reposGithubLiveData == null) {
            reposGithubLiveData = new MutableLiveData<>();
        }
        return reposGithubLiveData;
    }

    @Override
    public void returnListRepos(List<GithubRepository> list) {
        reposGithubLiveData.postValue(list);
    }
}
