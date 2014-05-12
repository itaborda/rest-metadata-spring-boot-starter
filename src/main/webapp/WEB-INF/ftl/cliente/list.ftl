<#import "/spring.ftl" as spring/>
   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${Request.contextPath}/resources/css/style.css"/>
<title>Listar</title>
</head>
<body>
  <div id="wrap">
  
  	<div id="menu">
    	<#include "../menu.ftl">
    </div>
    <div id="main">
	  	<h2>Cliente</h2>
		<ul>
			<li><a href="${Request.contextPath}/cliente">Ver todos</a></li>
			<li><a href="${Request.contextPath}/cliente/form">Novo Cliente</a></li>
		</ul>
    	<div id="body">
	
		    <#if clientes?has_content>
		        <table width="600px">
		            <tr>
		                <thead>
		                    <th>Id</th>                    
		                    <th>Nome</th>
		                    <th>E-mail</th>
		                    <th>Atualizar</th>
		                    <th>Excluir</th>
		                </thead>
		            </tr>
		            <#list clientes as cliente>
		                <tr>
		                    <td>${cliente.id}</td>                    
		                    <td>${cliente.nome}</td>
		                    <td>${cliente.email}</td>
		                    <td>
		                        <form action="${Request.contextPath}/cliente/${cliente.id}/form" method="GET">
		                            <input alt="Atualizar Cliente" src="${Request.contextPath}/resources/img/update.png" title="Atualizar Cliente" type="image" value="Atualizar Cliente"/>
		                        </form>
		                    </td>
		                    <td>
		                        <form action="${Request.contextPath}/cliente/${cliente.id}" method="DELETE">
		                            <input alt="Excluir Cliente" src="${Request.contextPath}/resources/img/delete.png" title="Excluir Cliente" type="image" value="Excluir Cliente"/>
		                        </form>
		                    </td>
		                </tr>
		            </#list>
		        </table>
		    <#else>
				Não há clientes cadastrados.
			</#if>

		</div>
    	<div id="plugin-body"></div>
	</div>
  </div>

</body>
</html>
