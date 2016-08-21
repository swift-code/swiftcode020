package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import forms.SignupForm;
import models.Profile;
import models.User;
import play.api.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;

/**
 * Created by lubuntu on 8/21/16.
 */
public class Application extends Controller
{
    @Inject
    FormFactory formFactory;

    @Inject
    ObjectMapper objectMapper;
    public Result signup()
    {
        Form<SignupForm> form = formFactory.form(SignupForm.class).bindFromRequest();
        if(form.hasErrors())
        {
            return ok(form.errorsAsJson());
        }

        Profile profile = new Profile(form.data().get("firstName"), form.data().get("lastName"));
        profile.db().save(profile);

        User user = new User(form.data().get("email"), form.data().get("password"));
        user.profile = profile;
        User.db().save(user);

    }
}

