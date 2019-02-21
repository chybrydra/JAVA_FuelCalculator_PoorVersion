package com.infoshare.lumato.beans;

import com.infoshare.lumato.models.User;
import com.infoshare.lumato.services.MessageService;
import com.infoshare.lumato.services.UserService;
import com.infoshare.lumato.utils.HttpUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named("editUser")
public class ChangeUserDataBean {

    private String newPasswordFirst;
    private String newPasswordSecond;

    @Inject
    private UserService userService;

    @Inject
    private MessageService messageService;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PostConstruct
    public void construct() {
        user = new User();
    }

    public String getNewPasswordFirst() {
        return newPasswordFirst;
    }

    public void setNewPasswordFirst(String newPasswordFirst) {
        this.newPasswordFirst = newPasswordFirst;
    }

    public String getNewPasswordSecond() {
        return newPasswordSecond;
    }

    public void setNewPasswordSecond(String newPasswordSecond) {
        this.newPasswordSecond = newPasswordSecond;
    }

    public void updateUser() {
        if (userService.doesUserExist(user)) {
            messageService.addUserAlreadyExistMessage();
        } else if (!userService.passwordIsOk(user)) {
            messageService.addWrongPasswordMessage();
        } else if (!this.newPasswordFirst.equals(this.newPasswordSecond)) {
            messageService.addPasswordsDoNotMatchMessage();
        } else {
            user.setPassword(this.newPasswordFirst);
            userService.updateUser(user);
        }
        HttpUtils.redirect("/app/user-management.xhtml");
    }
}
