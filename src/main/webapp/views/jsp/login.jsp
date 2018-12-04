<!DOCTYPE html> 
<html> 
<head>
	<meta charset="utf-8">
	<title>login</title>
	<link href="../css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container alert-info">

<br><br>

<div class="col-md-4 offset-md-4"> 
<div class="card-primary card " >
	<h6 class="card-header">G-ETUDIANTS</h6>
	<div class="card-body">
	
		<form action="<%=request.getContextPath()%>/UserController/login" method="post">
		<table class="table" style="padding:0; margin:0">
			<tr>
				<td  style="border:none;" colspan="2">Login :</td> 
			</tr>
				
				<% if( request.getAttribute("errorLogin")!=null ){ %>
					<tr> <td style="border:none;" colspan="2" class="text-danger">Username/Password est incorrect</td> </tr>
				<% } %>
			
			<tr>
				<td  style="border:none;">Username</td>
				<th  style="border:none;">
					<input name="username" class="form-control form-sm"/>
				</th>
			</tr>
			<tr>
				<td  style="border:none;">Password</td>
				<th  style="border:none;">
					<input name="password" type="password" class="form-control form-sm"/>
				</th>
			</tr>
			<tr>
				<th><button type="submit" name="connecter">Connect</button></th>
				<th></th>
			</tr> 
		</table>
		</form>
	</div>
</div>
</div>
</body>
</html>