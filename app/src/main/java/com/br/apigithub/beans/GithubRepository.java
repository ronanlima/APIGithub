package com.br.apigithub.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rlima on 29/05/18.
 */

public class GithubRepository {
    private String name;
    private String full_name;
    private Integer id;
    private List<Issue> issues;
    private List<Pull> pulls;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Issue> getIssues() {
        if (issues == null) {
            issues = new ArrayList<>();
        }
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public List<Pull> getPulls() {
        if (pulls == null) {
            pulls = new ArrayList<>();
        }
        return pulls;
    }

    public void setPulls(List<Pull> pulls) {
        this.pulls = pulls;
    }
}
