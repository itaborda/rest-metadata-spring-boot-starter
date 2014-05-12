<#import "/spring.ftl" as spring/>
<!-- < # assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/> -->
<!doctype html>
<html>
<head>
<title>Pluginize</title>

<!--
<link rel="stylesheet" type="text/css" media="all"
	href="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
	-->
<link rel="stylesheet" type="text/css" media="all"
	href="${request.contextPath}/resources/css/comps/bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="all"
	href="${request.contextPath}/resources/css/comps/ui4entity/multiselect.css">
<link rel="stylesheet" type="text/css" media="all"
	href="${request.contextPath}/resources/css/main.css">

</head>
<body ng-app="ui4entity" >

	<div class="container" ng-controller="IndexCtrl" ng-init="init()">
		<h2>UI4Entity - OIM Framework</h2>

		<div id="form" class="row vertical-center-row">
		
			<div id="form-content"></div>
			
			<button type="button" ng-click="load()" class="btn">Load</button>
		</div>
	</div>

	<script type="text/javascript">
		function IndexCtrl($scope, $http, $compile, ui4entity) {

			$scope.init = function() {
			};

			$scope.load = function() {
				$http.get(rootContext + 'test/getEntity.json/1').success(function(data) {
					ui4entity.makePut(data, rootContext, $scope, "#form-content");
				});
			};
		}
	</script>

<!--
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.13/angular.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<script type="text/javascript"
	src="https://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
-->

<script type="text/javascript"
	src="${request.contextPath}/resources/js/comps/angular.min.js"></script>
<script type="text/javascript"
	src="${request.contextPath}/resources/js/comps/jquery.min.js"></script>
<script type="text/javascript"
	src="${request.contextPath}/resources/js/comps/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${request.contextPath}/resources/js/comps/ui-bootstrap-0.6.0.min.js"></script>

<script type="text/javascript"
	src="${request.contextPath}/resources/js/comps/ui4entity/multiselect.js"></script>
<script type="text/javascript"
	src="${request.contextPath}/resources/js/comps/ui4entity/ui4entity.js"></script>

<script>var rootContext = '${request.contextPath}/';</script>


</body>
</html>