package com.sue.biz;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BizHelper {
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public boolean isValideEmail(String defaultEmail) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(defaultEmail);
        return matcher.matches();
    }
}
