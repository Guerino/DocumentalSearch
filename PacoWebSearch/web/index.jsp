<%-- 
    Document   : index
    Created on : 29-ene-2013, 18:20:21
    Author     : Guerino
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/simple.css" type="text/css" />
        <title>Buscador Paco</title>
    </head>
    <body>
     <div class="mainLogo"><h1 class="logo" align="center"><span class="paco">Paco</span> <span class="search">Search</span></h1></div>
     <div align="center"><s:form action="search" method="post">
             <s:textfield name="query" maxLength="120" size="60" cssClass="text"/>&nbsp;
        <s:submit value="Buscar" cssClass="submit"/>
        </s:form> </div>
    </body>
</html>