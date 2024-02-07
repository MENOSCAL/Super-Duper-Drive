package com.udacity.jwdnd.course1.cloudstorage.model;

public class Credential {
    private Integer credentialId;
    private Integer userId;
    private String url;
    private String username;
    private String kei;
    private String password;
    private String decryptedPassword;

    public Credential() {}

    public Credential(Integer credentialId, Integer userId, String url, String username, String kei, String password) {
        this.credentialId = credentialId;
        this.userId = userId;
        this.url = url;
        this.username = username;
        this.kei = kei;
        this.password = password;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKei() {
        return kei;
    }

    public void setKei(String kei) {
        this.kei = kei;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }
}
