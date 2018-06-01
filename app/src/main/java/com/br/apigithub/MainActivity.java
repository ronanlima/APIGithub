package com.br.apigithub;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.br.apigithub.aac.RepositoryViewModel;
import com.br.apigithub.beans.GithubRepository;
import com.br.apigithub.beans.Issue;
import com.br.apigithub.beans.Pull;
import com.br.apigithub.fragments.IssueFragment;
import com.br.apigithub.fragments.PullRequestFragment;
import com.br.apigithub.utils.PermissionUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private GithubRepository repository;
    private RepositoryViewModel repoViewModel;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String query;
    private String msgError;
    private ProgressDialog progressDialog;

    Observer<GithubRepository> observerRepository = new Observer<GithubRepository>() {
        @Override
        public void onChanged(@Nullable GithubRepository repo) {
            repository = repo;
        }
    };

    Observer<List<Issue>> observerIssues = new Observer<List<Issue>>() {
        @Override
        public void onChanged(@Nullable List<Issue> issues) {
            getProgressDialog().show();
            FragmentManager fm = getSupportFragmentManager();
            IssueFragment fragment = (IssueFragment) fm.getFragments().get(1);
            fragment.setIssues(issues);

            if (repository.getIssues() == null) {
                fragment.setAdapter();
            } else {
                fragment.updateAdapter();
            }
            repository.setIssues(issues);
        }
    };

    Observer<List<Pull>> observerPulls = new Observer<List<Pull>>() {
        @Override
        public void onChanged(@Nullable List<Pull> pulls) {
            getProgressDialog().show();
            FragmentManager fm = getSupportFragmentManager();
            PullRequestFragment fragment = (PullRequestFragment) fm.getFragments().get(2);
            fragment.setPulls(pulls);

            if (repository.getPulls() == null) {
                fragment.setAdapter();
            } else {
                fragment.updateAdapter();
            }
            repository.setPulls(pulls);
        }
    };

    Observer<String> observerMsgError = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            msgError = s;
            getProgressDialog().dismiss();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repoViewModel = ViewModelProviders.of(this).get(RepositoryViewModel.class);
        repoViewModel.getGithubLiveData().observe(this, observerRepository);
        repoViewModel.getIssuesLiveData().observe(this, observerIssues);
        repoViewModel.getPullsLiveData().observe(this, observerPulls);
        repoViewModel.getMsgError().observe(this, observerMsgError);
        repoViewModel.init();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), this));

        int cor = getResources().getColor(R.color.branco);

        tabLayout.setTabTextColors(cor, cor);
        tabLayout.addTab(tabLayout.newTab().setText("Issues"));
        tabLayout.addTab(tabLayout.newTab().setText("PR's"));

        tabLayout.setOnTabSelectedListener(this);

        viewPager.addOnPageChangeListener(new TabLayoutListener(tabLayout));
        viewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.contains("/")) {
                    Toast.makeText(getApplicationContext(), "A busca deve seguir o padrão usuário/repositório", Toast.LENGTH_LONG).show();
                } else {
                    setQuery(query);
                    if (PermissionUtils.validate(MainActivity.this, 1, Manifest.permission.INTERNET)) {
                        repoViewModel.searchRepo(query, 1, 10);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length != 0) {
            if (!PermissionUtils.isPermissaoConcedida(grantResults)) {
//                Dialog dialog = AlertUtils.criarAlerta(mContext.getResources().getString(R.string.titulo_alerta_permissoes), mContext.getResources().getString(R.string.msg_permissao), Dialog.TipoAlertaEnum.ALERTA, false, new Dialog.OnClickDialog() {
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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public RepositoryViewModel getRepoViewModel() {
        return repoViewModel;
    }

    public GithubRepository getRepository() {
        return repository;
    }

    public void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getResources().getString(R.string.text_carregando));
    }

    public ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            initProgressDialog();
        }
        return progressDialog;
    }

    public void stopLoading() {
        if (getProgressDialog().isShowing()) {
            getProgressDialog().dismiss();
        }
    }
}

class TabLayoutListener extends TabLayout.TabLayoutOnPageChangeListener {

    public TabLayoutListener(TabLayout tabLayout) {
        super(tabLayout);
    }
}

class TabsAdapter extends FragmentStatePagerAdapter {
    private MainActivity activity;

    public TabsAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        this.activity = activity;
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
