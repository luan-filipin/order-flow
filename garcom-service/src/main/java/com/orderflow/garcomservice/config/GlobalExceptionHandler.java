package com.orderflow.garcomservice.config;

import com.orderflow.garcomservice.dto.response.ErroResponseDTO;
import com.orderflow.garcomservice.exception.MesaJaEstaSendoAtendidaException;
import com.orderflow.garcomservice.exception.OPedidoJaEstaEncerrado;
import com.orderflow.garcomservice.exception.OPedidoNaoExistePeloIdException;
import com.orderflow.garcomservice.exception.OPedidoNaoExistePeloNumeroMesaException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResponseDTO> handlerGeneric(RuntimeException e, HttpServletRequest request) {
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroResponseDTO);
    }

    @ExceptionHandler(MesaJaEstaSendoAtendidaException.class)
    public ResponseEntity<ErroResponseDTO> handlerMesaJaEstaSendoAtendida(RuntimeException e, HttpServletRequest request) {
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroResponseDTO);
    }

    @ExceptionHandler(OPedidoNaoExistePeloNumeroMesaException.class)
    public ResponseEntity<ErroResponseDTO> handlerOPedidoNaoExistePeloNumeroMesa(RuntimeException e, HttpServletRequest request){
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponseDTO);
    }

    @ExceptionHandler(OPedidoNaoExistePeloIdException.class)
    public ResponseEntity<ErroResponseDTO> handlerOPedidoNaoExistePeloId(RuntimeException e, HttpServletRequest request){
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponseDTO);
    }

    @ExceptionHandler(OPedidoJaEstaEncerrado.class)
    public ResponseEntity<ErroResponseDTO> handlerOPedidoJaEstaEncerrado(RuntimeException e, HttpServletRequest request){
        ErroResponseDTO erroResponseDTO = new ErroResponseDTO(
                e.getMessage(),
                HttpStatus.CONFLICT.value(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroResponseDTO);
    }


}
