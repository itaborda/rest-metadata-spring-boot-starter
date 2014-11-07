<#import "/spring.ftl" as spring/>
   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${Request.contextPath}/resources/css/style.css"/>

<title>Atualizar</title>
</head>
<body>
  <div id="wrap">
  
  	<div id="menu">
    	<#include "../menu.ftl">
    </div>
    <div id="main">
    	<@spring.bind "cliente" />
    	<div id="body">
		        <div>
		            <label for="nome">Nome:</label>
		            <span>${cliente.nome}</span>
		        </div>
		        <br/>
		        <div>
		            <label for="email">Email:</label>
		            <span>${cliente.email}</span>
		        </div>        
		        <br/>
		</div>
		
		<ul>
			<li><a href="${Request.contextPath}/cliente">Voltar</a></li>
		</ul>
	</div>
	<div id="plugin-body"></div>
	<div id="plugin-show"></div>
  </div>

</body>
</html>
