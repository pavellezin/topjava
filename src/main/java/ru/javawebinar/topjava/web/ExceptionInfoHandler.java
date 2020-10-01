package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND, null);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) throws MalformedURLException {
        return logAndGetErrorInfo(req, e, true, DATA_ERROR, duplicateMessage(req));
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, null);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422 REST and UI
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ErrorInfo illegalRestOrUiRequestDataError(HttpServletRequest req, Exception e) {

        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, collectErrorInfo(e));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR, null);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType, String detailMessage) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        String errorInfoDetails = detailMessage == null ? e.getMessage() : detailMessage;
        return new ErrorInfo(req.getRequestURL(), errorType, errorInfoDetails);
    }

    private static String collectErrorInfo(Exception e) {
        String delimiter = e instanceof BindException ? "</br>" : "; ";
        BindingResult result = e instanceof BindException ? ((BindException) e).getBindingResult() : ((MethodArgumentNotValidException) e).getBindingResult();
        return result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining(delimiter));
    }

    private static String duplicateMessage(HttpServletRequest req) throws MalformedURLException {
        String url = Arrays.stream(req.getRequestURL().toString().split("/"))
                .reduce((a, b) -> b)
                .orElse(null);
        String detailMessage;
        ResourceBundle rb = getResourceBundle();
        switch (Objects.requireNonNull(url)) {
            case "users":
            case "register":
                detailMessage = rb.getString("app.user.duplicate");
                break;
            case "meals":
                detailMessage = rb.getString("app.meal.duplicate");
                break;
            default:
                detailMessage = "Duplicate items";
                break;
        }
        return detailMessage;
    }

    private static ResourceBundle getResourceBundle() throws MalformedURLException {
        String path = System.getenv("TOPJAVA_ROOT") + File.separator + "config" + File.separator + "messages";
        File file = new File(path);
        URL[] urls = {file.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(urls);
        return ResourceBundle.getBundle("app", Locale.getDefault(), loader);
    }
}