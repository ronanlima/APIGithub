package com.br.apigithub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
//    https://api.github.com/repos/octokit/octokit.rb/pulls
//    https://api.github.com/repos/octokit/octokit.rb/issues?state=open
//    https://api.github.com/orgs/octokit/repos
//    https://api.github.com/users/ronanlima/repos
//    https://api.github.com/repos/ronanlima/VitrineEscritores
//    https://api.github.com/repos/octokit/octokit.rb/issues/1021 -> issue específica (number)
//    https://api.github.com/repos/octokit/octokit.rb/pulls/1021 -> pr específica (number)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
