package com.orderflow.cozinhaservice.controller;

import com.orderflow.cozinhaservice.service.PedidoCozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pedido-cozinha")
public class PedidoCozinhaController {

    private final PedidoCozinhaService pedidoCozinhaService;

    @PutMapping("/finalizarPreparo/{idPedido}")
    public ResponseEntity<Void> finalizarPreparoPedido(@PathVariable Long idPedido) {
        pedidoCozinhaService.finalizarPreparoPedido(idPedido);
        return ResponseEntity.ok().build();
    }
}
