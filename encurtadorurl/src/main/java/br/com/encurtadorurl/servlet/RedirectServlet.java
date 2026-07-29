package br.com.encurtadorurl.servlet;

import br.com.encurtadorurl.dao.UrlDAO;
import br.com.encurtadorurl.entity.Url;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar
 */
@WebServlet("/r/*")
public class RedirectServlet extends HttpServlet {
    private final UrlDAO urlDAO = new UrlDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String caminho = request.getPathInfo();

        if (caminho == null || caminho.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String codigo = caminho.substring(1);

        Url url = urlDAO.buscarPorCodigo(codigo);

        if (url != null) {
            response.sendRedirect(url.getUrlOriginal());
        } else {
            response.sendRedirect(request.getContextPath() + "/erro.xhtml");
        }
    }
}