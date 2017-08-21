package tz.co.fasthub.survey.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.service.QuestionService;
/**
 * Created by naaminicharles on 7/27/17.
 */
@Component
public class QuestionValidator implements Validator {

    private final QuestionService questionService;

    @Autowired
    public QuestionValidator(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Question.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Question question = (Question) o;

        ValidationUtils.rejectIfEmpty(errors, "qsn", "NotEmpty");
        if (question.getQsn().length() < 1) {
            errors.rejectValue("qsn", "Size.question.qsn");
        }
        ValidationUtils.rejectIfEmpty(errors, "type", "NotEmpty");
        if (question.getType().length()<1)
        {
                errors.rejectValue("type", "Size.question.type");
        }

    }

}
