package com.br.apigithub;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.br.apigithub.aac.RepositoryViewModel;
import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.interfaces.IGithubServiceProvider;
import com.br.apigithub.interfaces.RetrofitServiceContract;
import com.br.apigithub.utils.PermissionUtils;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
//    https://api.github.com/repos/octokit/octokit.rb/pulls
//    https://api.github.com/repos/octokit/octokit.rb/issues?state=open
//    https://api.github.com/orgs/octokit/repos
//    https://api.github.com/users/ronanlima/repos
//    https://api.github.com/repos/ronanlima/VitrineEscritores
//    https://api.github.com/repos/octokit/octokit.rb/issues/1021 -> issue específica (number)
//    https://api.github.com/repos/octokit/octokit.rb/pulls/1021 -> pr específica (number)

    @Inject
    IGithubServiceProvider serviceProvider;
    @Inject
    RetrofitServiceContract bla;

    private List<GithubRepository> listRepos;
    private RepositoryViewModel repoViewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyApplication.getComponent().inject(this);

        repoViewMode = ViewModelProviders.of(this).get(RepositoryViewModel.class);
        repoViewMode.getReposGithubLiveData().observe(this, new Observer<List<GithubRepository>>() {
            @Override
            public void onChanged(@Nullable List<GithubRepository> list) {
                listRepos = list;
            }
        });
        repoViewMode.init(serviceProvider);
        PermissionUtils.requestPermissions(this, 1, Arrays.asList(Manifest.permission.INTERNET));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            repoViewMode.searchReposFromUser("olivierlacan", 1, 10);
        }
    }
}
