package com.card.tes.CardTeste.Controller;
import com.card.tes.CardTeste.Busca.ConsultarCnpj;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/ms-sefaz")
public class RestController {
    @RequestMapping(value = "/ConsultarCnpjReceita", method = RequestMethod.POST)
    public ResponseEntity<Object> findPage(@RequestBody ConsultarCnpj ConsultarCnpj) {
        if(ConsultarCnpj.getCnpj() != null) {
            System.out.println(ConsultarCnpj.getCnpj());
            ConsultarCnpj cnpjTeste = new ConsultarCnpj();
            return ResponseEntity.ok().body(cnpjTeste.findCnpj(ConsultarCnpj.getCnpj()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O campo cnpj deve estar no corpo da requisicao");
    }
}
