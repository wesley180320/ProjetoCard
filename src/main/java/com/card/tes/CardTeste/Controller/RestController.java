package com.card.tes.CardTeste.Controller;
import com.card.tes.CardTeste.Busca.ConsultarCnpj;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/Card")
public class RestController {
    @RequestMapping(value = "/ConsultarCnpj", method = RequestMethod.POST)
    public ResponseEntity<Object> findPage(@RequestBody ConsultarCnpj ConsultarCnpj) {
        if(ConsultarCnpj.getCnpj() != null) {
            System.out.println(ConsultarCnpj.getCnpj());
            ConsultarCnpj cnpjTeste = new ConsultarCnpj();
            return ResponseEntity.ok().body(cnpjTeste.findCnpj(ConsultarCnpj.getCnpj()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo cnpj deve estar no corpo da requisicao");
    }
}
