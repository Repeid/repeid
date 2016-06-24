package org.repeid.testsuite;

import org.repeid.services.resources.OrganizationsResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.PrintWriter;

public class ApplicationServlet extends HttpServlet {

    private static final String LINK = "<a href=\"%s\" id=\"%s\">%s</a>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = "";
        if (req.getRequestURI().endsWith("auth")) {
            title = "REPEID_RESPONSE";
        } else if (req.getRequestURI().endsWith("logout")) {
            title = "LOGOUT_REQUEST";
        } else {
            title = "APP_REQUEST";
        }

        PrintWriter pw = resp.getWriter();
        pw.printf("<html><head><title>%s</title></head><body>", title);
        UriBuilder base = UriBuilder.fromUri("http://localhost:8081/repeid");
        //pw.printf(LINK, OrganizationsResource.accountUrl(base).build("test"), "account", "account");

        pw.print("</body></html>");
        pw.flush();
    }

}
