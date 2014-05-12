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
		    <form action="${Request.contextPath}/cliente/${cliente.id}" method="PUT">
		        <div>
		            <label for="nome">Nome:</label>
		            <@spring.showErrors "cliente.nome", "errors" /><br />
		            <@spring.formInput "cliente.nome", "style='width:250px',size='30'"/>
		        </div>
		        <br/>
		        <div>
		            <label for="email">Email:</label>
		            <@spring.showErrors "cliente.email", "errors" /><br />
		            <@spring.formInput "cliente.email", "style='width:250px',size='30'"/>
		        </div>        
		        <br/>
		 
		        <div class="submit">
		            <input id="atualizar" type="submit" value="Atualizar Cliente"/>
		        </div>		        
		        
		    </form>
		</div>
		
		<ul>
			<li><a href="${Request.contextPath}/cliente">Voltar</a></li>
		</ul>
	</div>
	<div id="plugin-body"></div>
	<div id="plugin-update"></div>
  </div>

</body>
</html>
