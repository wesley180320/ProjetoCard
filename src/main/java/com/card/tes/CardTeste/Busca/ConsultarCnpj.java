package com.card.tes.CardTeste.Busca;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

@Entity
public class ConsultarCnpj implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private Long cnpj;

    private static final long serialVersionUID = 1662742902120616904L;

    public ConsultarCnpj() {
    }

    public ConsultarCnpj(Long id, Long cnpj) {
        this.id = id;
        this.cnpj = cnpj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public static boolean validarCNPJ(long cnpj) {
        String strCnpj = String.format("%014d", cnpj);

        if (strCnpj == null || strCnpj.length() != 14)
            return false;

        int soma = 0;
        String cnpj_calc = strCnpj.substring(0, 12);

        char[] chr_cnpj = strCnpj.toCharArray();
        for (int i = 0; i < 4; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
                soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
        }
        int dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        // Calcula o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 5; i++) {
            if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
                soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
        }
        for (int i = 0; i < 8; i++) {
            if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
                soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
        }
        dig = 11 - (soma % 11);
        cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

        return strCnpj.equals(cnpj_calc);
    }

    public Object findCnpj(Long cnpj) {
        if (validarCNPJ(cnpj) == true) {
            try {
                URL url = new URL("https://receitaws.com.br/v1/cnpj/" + cnpj);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(sb.toString());
                System.out.println(node);
                return node;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return "Não foi possivel retornar um cnpj valido, por favor verificar o cnpj digitado.";
    }
}




