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
import com.br.apigithub.beans.Issue;
import com.br.apigithub.beans.Pull;
import com.br.apigithub.utils.PermissionUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
//    https://api.github.com/repos/octokit/octokit.rb/pulls
//    https://api.github.com/repos/octokit/octokit.rb/issues?state=open
//    https://api.github.com/orgs/octokit/repos
//    https://api.github.com/users/ronanlima/repos
//    https://api.github.com/repos/ronanlima/VitrineEscritores
//    https://api.github.com/repos/octokit/octokit.rb/issues/1021 -> issue específica (number)
//    https://api.github.com/repos/octokit/octokit.rb/pulls/1021 -> pr específica (number)
//    https://api.github.com/repos/olivierlacan/keep-a-changelog/issues
//    /repos/:owner/:repo/issues/:number
//    /repos/:owner/:repo/pulls

    private GithubRepository repository;
    private List<Issue> listIssues;
    private List<Pull> listPulls;
    private RepositoryViewModel repoViewMode;

    Observer<GithubRepository> observerRepository = new Observer<GithubRepository>() {
        @Override
        public void onChanged(@Nullable GithubRepository repo) {
            repository = repo;
        }
    };

    Observer<List<Issue>> observerIssues = new Observer<List<Issue>>() {
        @Override
        public void onChanged(@Nullable List<Issue> issues) {
            listIssues = issues;
        }
    };

    Observer<List<Pull>> observerPulls = new Observer<List<Pull>>() {
        @Override
        public void onChanged(@Nullable List<Pull> pulls) {
            listPulls = pulls;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repoViewMode = ViewModelProviders.of(this).get(RepositoryViewModel.class);
        repoViewMode.getGithubLiveData().observe(this, observerRepository);
        repoViewMode.getIssuesLiveData().observe(this, observerIssues);
        repoViewMode.getPullsLiveData().observe(this, observerPulls);
        repoViewMode.init();

        if (PermissionUtils.validate(this, 1, Manifest.permission.INTERNET)) {
            repoViewMode.searchRepo("octokit", "octokit.rb", 1, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length != 0) {
            if (!PermissionUtils.isPermissaoConcedida(grantResults)) {
//                JFSteelDialog dialog = AlertUtils.criarAlerta(mContext.getResources().getString(R.string.titulo_alerta_permissoes), mContext.getResources().getString(R.string.msg_permissao), JFSteelDialog.TipoAlertaEnum.ALERTA, false, new JFSteelDialog.OnClickDialog() {
//                    @Override
//                    public void onClickPositive(View v, String tag) {
//
//                    }
//
//                    @Override
//                    public void onClickNegative(View v, String tag) {
//                        finish();
//                    }
//
//                    @Override
//                    public void onClickNeutral(View v, String tag) {
//
//                    }
//                });
//                dialog.show(getSupportFragmentManager(), "dialog");
            } else {
                repoViewMode.searchReposFromUser("olivierlacan", 1, 10);
            }
        }
    }
}
