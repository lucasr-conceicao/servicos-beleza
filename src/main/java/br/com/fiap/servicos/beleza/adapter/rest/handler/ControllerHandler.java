package br.com.fiap.servicos.beleza.adapter.rest.handler;

import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerHandler {


    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<JsonHandler> handlerRecursoNaoEncontradoException(Exception ex, HttpServletRequest httpServlet) {
        return montarRetorno(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), httpServlet.getRequestURI(), ex.getMessage());
    }

    private ResponseEntity<JsonHandler> montarRetorno(HttpStatus httpStatus, Integer code, String path, String mensagem) {
        return new ResponseEntity<>(new JsonHandler(LocalDateTime.now(), code , httpStatus, path, mensagem), httpStatus);
    }
}
