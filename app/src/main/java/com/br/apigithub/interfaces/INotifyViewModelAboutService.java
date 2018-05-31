package com.br.apigithub.interfaces;

import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Issue;
import com.br.apigithub.beans.Pull;

import java.util.List;

/**
 * Created by rlima on 30/05/18.
 */

public interface INotifyViewModelAboutService {
    void returnListRepos(List<GithubRepository> list);

    void returnRepo(GithubRepository repository);

    void returnIssues(List<Issue> issues);

    void returnPulls(List<Pull> pulls);

    void notifyOnError(Throwable throwable);
}
