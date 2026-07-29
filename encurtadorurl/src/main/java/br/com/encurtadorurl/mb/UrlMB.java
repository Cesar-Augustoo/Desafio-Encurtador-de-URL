/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.encurtadorurl.mb;

import br.com.encurtadorurl.dao.UrlDAO;
import br.com.encurtadorurl.entity.Url;
import br.com.encurtadorurl.service.UrlService;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
/**
 *
 * @author cesar
 */
@Named
@ViewScoped
public class UrlMB implements Serializable {
    private Url url;
    private List<Url> urls;
    private String linkEncurtado;
    private UrlDAO dao = new UrlDAO();
    private final UrlService service = new UrlService();
    private String ultimaUrlGerada;

    @PostConstruct
    public void init() {
        novo();
        carregarLista();
    }

    public void salvar() {
        if (url==null || url.getUrlOriginal().trim().isEmpty()) {
            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_WARN,
                                    "Atenção!",
                                    "Informe URL original."
                            )
                    );
            return;
        }
        try {
            Url codigo = service.criar(
                    url.getUrlOriginal(),
                    url.getCodigo()
            );
            ultimaUrlGerada = getUrlBase() + "/r/" + codigo.getCodigo();
            novo();
            carregarLista();
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance()
                    .addMessage(
                            null,
                            new FacesMessage(
                                    FacesMessage.SEVERITY_WARN,
                                    "Alias já utilizado.",
                                    "Informe um alias diferente."
                            )
                    );
        }
    }

    private void novo() {
        url = new Url();
    }

    private void carregarLista() {
        urls = dao.listar();
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }
    
    public String getLinkEncurtado() {
        return linkEncurtado;
    }
    
    public String getUrlBase() {
        ExternalContext ec
                = FacesContext.getCurrentInstance()
                        .getExternalContext();

        return ec.getRequestScheme()
                + "://"
                + ec.getRequestServerName()
                + ":"
                + ec.getRequestServerPort()
                + ec.getRequestContextPath();
    }
    
    public String getUltimaUrlGerada() {
        return ultimaUrlGerada;
    }
    
    public void excluirTodas() {
        service.excluirTodas();
        carregarLista();
        ultimaUrlGerada = null;

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Sucesso",
                        "Todas as URLs foram removidas."
                ));

    }
}
