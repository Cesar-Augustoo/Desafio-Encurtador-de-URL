/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.encurtadorurl.dto;

/**
 *
 * @author cesar
 */

public class UrlResponse {
    private String urlEncurtada;

    public UrlResponse(String urlEncurtada) {
        this.urlEncurtada = urlEncurtada;
    }

    public String getUrlEncurtada() {
        return urlEncurtada;
    }
}
