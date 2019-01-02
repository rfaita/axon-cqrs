package com.cqrs.commands.server.helper;

import com.cqrs.model.dto.ErrorDTO;
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rfaita
 */
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({AxonServerRemoteCommandHandlingException.class})
    @ResponseBody
    public ResponseEntity<List<ErrorDTO>> exceptionHandler(AxonServerRemoteCommandHandlingException e) {
        return new ResponseEntity<>(
                e.getExceptionDescriptions().stream()
                        .map(s -> ErrorDTO.builder().errorMessage(s).build())
                        .collect(Collectors.toList()),
                HttpStatus.BAD_REQUEST);
    }

}
