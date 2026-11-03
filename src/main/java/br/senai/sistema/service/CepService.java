package br.senai.sistema.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    public CepResponse consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, CepResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    // Classe auxiliar para mapear a resposta do ViaCEP
    public static class CepResponse {
        private String cep;
        private String logradouro;
        private String bairro;
        private String localidade;
        private String uf;

        // Getters e Setters
        public String getCep() { return cep; }
        public void setCep(String cep) { this.cep = cep; }

        public String getLogradouro() { return logradouro; }
        public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

        public String getBairro() { return bairro; }
        public void setBairro(String bairro) { this.bairro = bairro; }

        public String getLocalidade() { return localidade; }
        public void setLocalidade(String localidade) { this.localidade = localidade; }

        public String getUf() { return uf; }
        public void setUf(String uf) { this.uf = uf; }
    }
}