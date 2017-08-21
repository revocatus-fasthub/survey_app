package tz.co.fasthub.survey.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.service.AnswerService;

/**
 * Created by naaminicharles on 7/27/17.
 */
@Component
public class AnswerValidator implements Validator {

    private final AnswerService answerService;

    @Autowired
    public AnswerValidator(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Answer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Answer answer = (Answer) o;

        ValidationUtils.rejectIfEmpty(errors, "ans", "NotEmpty");
        if (answer.getAns().length() < 1) {
            errors.rejectValue("ans", "Size.answer.ans");
        }

    }

}
