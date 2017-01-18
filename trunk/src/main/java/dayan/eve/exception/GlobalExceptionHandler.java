package dayan.eve.exception;

import dayan.eve.model.JsonResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xsg on 1/18/2017.
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        LOGGER.error("url: {},error: {}", request.getRequestURI(), e.toString());
        return new JsonResult(ErrorCN.DEFAULT_SERVER_ERROR, false);
    }

    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public JsonResult loginErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws
            Exception {
        LOGGER.error("url: {},error: {}", request.getRequestURI(), e.toString());
        return new JsonResult(ErrorCN.Login.UN_LOGIN, false);
    }

    @ExceptionHandler(value = EveException.class)
    @ResponseBody
    public JsonResult eveErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws
            Exception {
        LOGGER.error("url: {},error: {}", request.getRequestURI(), e.toString());
        return new JsonResult(e.getMessage(), false);
    }

}
