package com.orderflow.garcomservice.controller;

import com.orderflow.garcomservice.dto.request.GarcomRequestDTO;
import com.orderflow.garcomservice.dto.response.GarcomResponseDTO;
import com.orderflow.garcomservice.service.GarcomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/garcom")
public class GarcomController {

    private final GarcomService garcomService;

    @PostMapping("/criar")
    public ResponseEntity<GarcomResponseDTO> criarPedido(@RequestBody @Valid GarcomRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(garcomService.gerarPedido(dto));
    }

    @GetMapping("/buscarPedidoMesa/{numeroMesa}")
    public ResponseEntity<GarcomResponseDTO> buscarPedidoPeloNumeroMesa(@PathVariable("numeroMesa") Integer numeroMesa) {
        return ResponseEntity.ok(garcomService.buscarPedidoPeloNumeroMesa(numeroMesa));
    }

    @PutMapping("/encerrarPedido/{numeroMesa}")
    public ResponseEntity<GarcomResponseDTO> encerrarPedido(@PathVariable("numeroMesa") Integer numeroMesa) {
        return ResponseEntity.ok(garcomService.encerraPedido(numeroMesa));
    }
}


