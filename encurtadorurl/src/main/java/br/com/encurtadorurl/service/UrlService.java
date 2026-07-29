/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.encurtadorurl.service;

/**
 *
 * @author cesar
 */

import br.com.encurtadorurl.dao.UrlDAO;
import br.com.encurtadorurl.entity.Url;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UrlService {
    private final UrlDAO dao = new UrlDAO();

    public Url criar(String urlOriginal, String alias) {
        if (urlOriginal == null || urlOriginal.isBlank()) {
            throw new IllegalArgumentException(
                    "URL original obrigatória."
            );
        }
        
        if (!urlOriginal.matches("^(?i)https?://.*")) {
            urlOriginal = "https://" + urlOriginal;
        }
        
        Url url = new Url();
        url.setUrlOriginal(urlOriginal);

        if (alias != null && !alias.trim().isEmpty()) {
            alias = alias.trim();
            
            if (dao.existeCodigo(alias)) {
                throw new IllegalArgumentException(
                    "Alias já utilizado."
                );
            }

            url.setCodigo(alias);
        } else {
            url.setCodigo(gerarCodigoUnico());
        }

        url.setDataCriacao(LocalDateTime.now());
        dao.salvar(url);
        return url;
    }


    private synchronized String gerarCodigoUnico() {
        String codigo;
        do {
            codigo = UUID.randomUUID()
                    .toString()
                    .substring(0, 6);
        } while (dao.existeCodigo(codigo));
        return codigo;
    }
    
    public void excluirTodas() {
        dao.excluirTodas();
    }
    
    public List<Url> listar() {
        return dao.listar();
    }
}
