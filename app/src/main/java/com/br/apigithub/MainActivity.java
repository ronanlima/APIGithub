package com.br.apigithub;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.br.apigithub.aac.RepositoryViewModel;
import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Issue;
import com.br.apigithub.beans.Pull;
import com.br.apigithub.fragments.IssueFragment;
import com.br.apigithub.fragments.PullRequestFragment;
import com.br.apigithub.utils.PermissionUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
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
    private RepositoryViewModel repoViewMode;
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    Observer<GithubRepository> observerRepository = new Observer<GithubRepository>() {
        @Override
        public void onChanged(@Nullable GithubRepository repo) {
            repository = repo;
        }
    };

    Observer<List<Issue>> observerIssues = new Observer<List<Issue>>() {
        @Override
        public void onChanged(@Nullable List<Issue> issues) {
            repository.setIssues(issues);
        }
    };

    Observer<List<Pull>> observerPulls = new Observer<List<Pull>>() {
        @Override
        public void onChanged(@Nullable List<Pull> pulls) {
            repository.setPulls(pulls);
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

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));

        int cor = getResources().getColor(R.color.branco);

        tabLayout.setTabTextColors(cor, cor);
        tabLayout.addTab(tabLayout.newTab().setText("Issues"));
        tabLayout.addTab(tabLayout.newTab().setText("PR's"));

        tabLayout.setOnTabSelectedListener(this);

        viewPager.addOnPageChangeListener(new TabLayoutListener(tabLayout));
        viewPager.setVisibility(View.VISIBLE);

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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}

class TabLayoutListener extends TabLayout.TabLayoutOnPageChangeListener {

    public TabLayoutListener(TabLayout tabLayout) {
        super(tabLayout);
    }
}

class TabsAdapter extends FragmentStatePagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return IssueFragment.newInstance();
        }
        return PullRequestFragment.newInstance();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
