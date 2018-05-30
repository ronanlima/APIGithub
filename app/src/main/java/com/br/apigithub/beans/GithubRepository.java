package com.br.apigithub.beans;

/**
 * Created by rlima on 29/05/18.
 */

public class GithubRepository {
    private Issue issue;
    private Pull pull;

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public Pull getPull() {
        return pull;
    }

    public void setPull(Pull pull) {
        this.pull = pull;
    }
}
