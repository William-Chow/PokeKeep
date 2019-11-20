package com.william.pokekeep.mvp.model;

/**
 * Created by William Chow on 2019-11-19.
 */
public class RememberMeBean {

    private String email;
    private String phone;
    private String password;
    private Boolean rememberMeMark;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMeMark() {
        return rememberMeMark;
    }

    public void setRememberMeMark(Boolean rememberMeMark) {
        this.rememberMeMark = rememberMeMark;
    }

}
