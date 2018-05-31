package com.br.apigithub.dagger.components;

import com.br.apigithub.MainActivity;
import com.br.apigithub.dagger.modules.GithubModule;
import com.br.apigithub.dagger.modules.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rlima on 30/05/18.
 */

@Singleton
@Component(modules = {RetrofitModule.class, GithubModule.class})
public interface RetrofitComponent {
    void inject(MainActivity mainActivity);
}
