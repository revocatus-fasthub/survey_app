package tz.co.fasthub.survey.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by naaminicharles on 7/27/17.
 */
@Component
public class UserValidator implements Validator {

    /*
    * Description for "((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"
    * (			# Start of group
          (?=.*\d)		#   must contains one digit from 0-9
          (?=.*[a-z])		#   must contains one lowercase characters
          (?=.*[A-Z])		#   must contains one uppercase characters
          (?=\\S+$)         #   no whitespace allowed in the entire string
          (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
                      .		#     match anything with previous condition checking
                        {6,20}	#        length at least 6 characters and maximum of 20
        )		# End of group
    */

    private Pattern pattern;
    private Matcher matcher;

    private static String PASSWORD_PATTERN = "(?=.*[@#$-%^&+=]).{8,20}";

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if(userService.findByEmail(user.getEmail()) != null){
            errors.rejectValue("email", "Duplicate.userForm.email");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if(user.getPassword() != null){
            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(user.getPassword());
        }
        /*else {
            errors.rejectValue("password", "NotEmpty");
        }*/
        if(!matcher.matches()){
//        if(!user.getPassword().matches(PASSWORD_PATTERN)){
//        if (user.getPassword().length() < 6 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getCpassword().equals(user.getPassword())) {
            errors.rejectValue("cpassword", "Diff.userForm.passwordConfirm");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "NotEmpty");
        if (user.getRole().isEmpty()) {
            errors.rejectValue("role", "Size.userForm.role");
        }


    }


}
