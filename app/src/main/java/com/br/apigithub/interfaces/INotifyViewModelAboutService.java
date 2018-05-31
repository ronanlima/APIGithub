package com.br.apigithub.interfaces;

import com.br.apigithub.beans.GithubRepository;

import java.util.List;

/**
 * Created by rlima on 30/05/18.
 */

public interface INotifyViewModelAboutService {
    void returnListRepos(List<GithubRepository> list);
}
