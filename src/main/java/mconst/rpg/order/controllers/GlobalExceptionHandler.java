package mconst.rpg.order.controllers;

import mconst.rpg.order.models.exceptions.ConflictException;
import mconst.rpg.order.models.exceptions.ExceptionBody;
import mconst.rpg.order.models.exceptions.InternalServerException;
import mconst.rpg.order.models.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = { NotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody onNotFoundException(NotFoundException exception) {
        return exception.getBody();
    }

    @ExceptionHandler(value = {InternalServerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void onInternalServerException(InternalServerException exception) {
        return;
    }

    @ResponseBody
    @ExceptionHandler(value = { ConflictException.class })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody onConflictException(ConflictException exception) {
        return exception.getBody();
    }
}
