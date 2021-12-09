package Controller.api;

import Model.account.Account;
import Model.account.AccountDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class RequestValidator {

    private final List<String> errors;
    private final HttpServletRequest request;
    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^(-)?(0|[1-9]\\d+)\\.\\d+$");

    public RequestValidator(HttpServletRequest request) {
        this.errors = new ArrayList<>();
        this.request = request;
    }

    public boolean hasErrors() { return !errors.isEmpty(); }

    public List<String> getErrors() { return errors; }

    private boolean gatherError(boolean condition, String message) {
        if(condition)
            return true;

        errors.add(message);
        return false;
    }

    private boolean required(String value) { return value != null && !value.isBlank(); }

    public boolean assertMatch(String value, Pattern regexp, String msg) {
        String param = request.getParameter(value);
        boolean condition = required(param) && regexp.matcher(param).matches();
        return gatherError(condition, msg);
    }

    public boolean assertInt(String value, String msg) {
        return assertMatch(value, INT_PATTERN, msg);
    }

    public boolean assertFile(String value, String msg) throws ServletException, IOException {
        boolean condition = !(request.getPart(value).getSubmittedFileName().isEmpty());
        return gatherError(condition, msg);
    }

    public boolean assertDouble(String value, String msg) {
        return assertMatch(value, DOUBLE_PATTERN, msg);
    }

    public boolean assertEmail(String value, String msg) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
        return assertMatch(value, pattern, msg);
    }

    /*public boolean assertExistingEmail(String value, String msg) throws SQLException {
        Optional<Account> account = new AccountDAO().fetch(request.getParameter(value));

        return gatherError(account.isEmpty(), msg);
    }*/

    public boolean assertPassword(String value, String msg) {
        String password = request.getParameter(value);

        int size = password.length();
        boolean hasNumber = false;
        boolean hasUpperCase = false;

        char[] chars = password.toCharArray();
        for(char c : chars) {
            if (Character.isDigit(c))
                hasNumber = true;
            if (Character.isUpperCase(c))
                hasUpperCase = true;

            if(hasNumber && hasUpperCase)
                break;
        }

        boolean condition = (size >= 8 && hasNumber && hasUpperCase);
        return gatherError(condition, msg);
    }

    public boolean assertPhone(String value, String msg) {
        String phone = request.getParameter(value);

        int size = phone.length();
        boolean allNumbers = true;

        char[] chars = phone.toCharArray();
        for(char c : chars)
            if(Character.isLetter(c)) {
                allNumbers = false;
                break;
            }

        boolean condition = (size >= 9 && size <= 11 && allNumbers);
        return gatherError(condition, msg);
    }

    public boolean assertInts(String values, String msg) {
        String[] params = request.getParameterValues(values);
        boolean allInt = Arrays.stream(params).allMatch(param -> INT_PATTERN.matcher(param).matches());
        return gatherError(allInt, msg);
    }

    public boolean assertSize(String first, String second, String msg) {
        String[] firstList = request.getParameterValues(first);
        String[] secondList = request.getParameterValues(second);
        return gatherError(firstList.length == secondList.length, msg);
    }

}
