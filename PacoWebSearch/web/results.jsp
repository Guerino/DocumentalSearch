<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="style/simple.css" type="text/css" />
<link rel="stylesheet" href="style/pagination.css" type="text/css" />
<script type="text/javascript">
//Funcion selectora de la cantidad de items por pagina
 function onItemChange(item) {
    var items = (item.options[item.selectedIndex].value);
    if(items){
        document.search.action = 'search';
        document.getElementById('ItemSelected').value = items;
        document.search.submit();
    }
 }
</script>
<title>Buscador Paco - Resultados</title>
</head><body>
<table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="0" class="header">
  <tr>
    <td width="16%" align="center"> 
    <a href="http://localhost:8080/PacoWebSearch" target="_self" class="linkIndex">
    <h1><span class="paco">Paco</span> <span class="search">Search</span></h1></a> </td>
    <td width="84%" align="left">
    <s:form action="search" method="post" id="search">
        <s:textfield name="query"  maxLength="120" size="65" cssClass="text" />
         &nbsp;<s:submit value="Buscar" cssClass="submit" />
      <input type="hidden" name="ItemSelected" id="ItemSelected"/>
    </s:form>
    </td>
  </tr>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr><td></td>
    <td height="22" class="info">
        <s:if test="%{CurrentPage > 1}">
            Pagina <s:property value="CurrentPage"/> de aproximadamente <s:if test="%{totalDoc == 0}"><s:property value="totalDoc" /> </s:if>
            <s:else><s:property value="totalDoc-1" /> resultados  </s:else>(<s:property value="time" /> segundos)
        </s:if>
        <s:else>
            Aproximadamente <s:if test="%{totalDoc == 0}"><s:property value="totalDoc" /> </s:if>
            <s:else><s:property value="totalDoc-1" /> resultados  </s:else>(<s:property value="time" /> segundos)          
        </s:else>
     </td>
  </tr> 
    <tr>
      <td width="10%" ></td>
      <td>
          <s:iterator value="listDoc" var="doc">
          <div class="result"><h4><a href="download?file=<s:property value="%{#doc.Path}" />" target="_self"><s:property value="%{#doc.Name}" /></a></h4>
      <p>Tamaño: <s:property value="%{#doc.SizeToString}" /></p>
      <p class="path"><s:property value="%{#doc.Path}" /> </p></div>
          </s:iterator></td>
    </tr>  
  <tr><td height="18" colspan="2"></td></tr>
  <s:if test="%{LastPage > 1}">
  <tr>
      <td >&nbsp;</td>
         <td><ul id="pagination">
         <li class="active">
                Pagina <s:property value="CurrentPage"/> de <s:property value="LastPage"/>  
         </li> 
         <s:if test="%{IsFirtsPage == true}">
            <li class="previous">
             <a href="search?query=<s:property value="q"/>&numberPage=<s:property value="FirtsPage"/>&ItemSelected=<s:property value="ItemSelected"/>">&laquo; Primero</a> 
            </li>            
         </s:if> 
         <s:if test="%{IsPreviusPage == true}">
            <li class="previous">
 <a href="search?query=<s:property value="q"/>&numberPage=<s:property value="PreviusPage"/>&ItemSelected=<s:property value="ItemSelected"/>"> &lt; Anterior</a> 
            </li>            
         </s:if>                  
    <s:iterator value="listPages" var="i">
    <s:if test="%{CurrentPage == #i}">
        <li class="active"><s:property value="#i"/></li>
    </s:if>
        <s:else>
          <li><a href="search?query=<s:property value="q"/>&numberPage=<s:property value="#i"/>&ItemSelected=<s:property value="ItemSelected"/>">
            <s:property value="#i"/></a></li>
        </s:else>
    </s:iterator>
   <s:if test="%{IsNextPage == true}">
    <li class="previous">
        <a href="search?query=<s:property value="q"/>&numberPage=<s:property value="NextPage"/>&ItemSelected=<s:property value="ItemSelected"/>" >Siguiente &gt;</a> 
    </li>            
   </s:if>           
   <s:if test="%{IsLastPage == true}">
    <li class="previous">
        <a href="search?query=<s:property value="q"/>&numberPage=<s:property value="LastPage"/>&ItemSelected=<s:property value="ItemSelected"/>">Ultimo &raquo;</a> 
    </li>            
   </s:if>
 <li class="itemsPerPage">
    Resultados por pagina  <s:select id="items" list="listItemsPerPage" name="items" value="itemSelected" onchange="onItemChange(this)"/>
 </li>
   </ul></tr></s:if>
  <tr><td height="25px" colspan="2"></td></tr>
  <tr><td height="30px" colspan="2" align="center" class="footer">UTN-FRC-DLC | Javier G&uuml;erino Palacios, Leg. 55624 &copy;2012-2013</td></tr>
</table>
</body>
</html>