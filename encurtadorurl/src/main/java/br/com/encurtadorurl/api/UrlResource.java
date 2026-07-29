/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.encurtadorurl.api;

/**
 *
 * @author cesar
 */

import br.com.encurtadorurl.dto.ErroResponse;
import br.com.encurtadorurl.dto.UrlRequest;
import br.com.encurtadorurl.dto.UrlResponse;
import br.com.encurtadorurl.entity.Url;
import br.com.encurtadorurl.service.UrlService;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/urls")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UrlResource {
    private final UrlService service = new UrlService();
    
    @Context
    private UriInfo uriInfo;

    @POST
    public Response criar(UrlRequest request) {
        try {
            Url url = service.criar(
                    request.getUrlOriginal(),
                    request.getAlias()
            );

            String urlEncurtada
                    = uriInfo.getBaseUri()
                            .toString()
                            .replace("/api/", "/r/")
                    + url.getCodigo();

            return Response.ok(
                    new UrlResponse(urlEncurtada)
            ).build();

        } catch (IllegalArgumentException e) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(new ErroResponse(e.getMessage()))
                    .build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Url> urls = service.listar();
        return Response.ok(urls).build();
    }
    
    @DELETE
    public Response excluirTodas() {
        service.excluirTodas();
        return Response.noContent().build();
    }
}
