
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="consumoelectricojsp.web.controller.ServletUtils"%>

<%! String PAGE_SIZE_COOKIE_NAME = "pageSizeCookie"; %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/style.css"/>
        <title>Consumo Eléctrico JSP Ampliado</title>
    </head>
    <body>
        <% 
            Cookie pageSizeCookie = ServletUtils.getCookie(request, PAGE_SIZE_COOKIE_NAME);
            String pageSize = (pageSizeCookie != null) ? pageSizeCookie.getValue() : "";
        %>
        <div class="container">
            <nav>
                <h1>Consumo Eléctrico JSP</h1>
                <p>por mothcrown</p>
            </nav>
            <div class="section">
                <div class="form">
                    <form action="clientcontroller" method="GET">
                        <fieldset>
                            <legend>Número de salidas por página</legend>
                            <label for="pageText">
                                <span>Salidas: </span>
                                <input type="text" id="pageText" name="pageSize" value="<%= pageSize %>" required>
                            </label>
                        </fieldset>
                        <fieldset>
                            <legend>Datos del cliente</legend>
                            <label for="clientText">
                                <span>Nombre: </span>
                                <input type="text" id="clientText" name="nameToSearch" required>
                            </label>
                        </fieldset>
                        <!-- Shhh, no hace falta, pero me gusta la simetría -->
                        <input type="hidden" id="page" name="page" value="1" />
                        <input type="submit" value="Enviar" />
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
