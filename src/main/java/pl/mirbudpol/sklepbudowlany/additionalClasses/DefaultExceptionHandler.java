package pl.mirbudpol.sklepbudowlany.additionalClasses;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.mirbudpol.sklepbudowlany.DTO.ExceptionDTO;
import pl.mirbudpol.sklepbudowlany.exceptions.*;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(value = InvalidFormatException.class)
    public ResponseEntity<ExceptionDTO> handleInvalidFormatException(InvalidFormatException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(e.getMessage());
        exceptionDTO.setAdditionalInfo(e.getPathReference());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ExceptionDTO> handleNoSuchElementException(NoSuchElementException e) {
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(e.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(e.getBindingResult().getFieldErrors().stream().map(err-> err.getDefaultMessage()).collect(java.util.stream.Collectors.joining(", ")));
        exceptionDTO.setAdditionalInfo(HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(exceptionDTO,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage("Email jest już zajęty");
        exceptionDTO.setAdditionalInfo(HttpStatus.CONFLICT.toString());
        return new ResponseEntity<>(exceptionDTO,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NotValidPhoneNumber.class)
    public ResponseEntity<String> handleNotValidPhoneNumber(NotValidPhoneNumber e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = LackOfResources.class)
    public ResponseEntity<String> handleLackOfResources(LackOfResources e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoPermissions.class)
    public ResponseEntity<String> handleNoPermissions(NoPermissions e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = DuplicatedValueException.class)
    public ResponseEntity<String> handleDuplicatedValue(DuplicatedValueException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}