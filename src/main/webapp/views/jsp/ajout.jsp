 

<!DOCTYPE html> 
<html> 
<head>
	<meta charset="utf-8">
	<title>Ajout d'etudiant</title>
	<link href="../css/bootstrap.min.css" rel="stylesheet">
  	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</head>
  
<body class="alert-info"> 
    <div class="container">
	<jsp:include page="menu.jsp" />
	
    <div class="card-primary card" >
	<div class="row">
    <div class="col-md-3" >
    	<jsp:include page="leftMenu.jsp" />
   	</div>
            <div class="col">
            	<h6 class="card-header">Ajout d'etudiant</h6> 
            
            	<div id="contenu">
            	<h6>Entrez les information de l'etudiant ci-dessous :</h6><br>
              	<form method="post" action="ajouter">
              	<table class="table">
	                <thead>
	                  <tr>
	                    <th class="text-center">Propriete</th>
	                    <th class="text-center">Valeur
	                      <br> </th>
	                  </tr>
	                </thead>
	                <tbody> 
	                	<tr>
	                    <td>Nom</td>
	                    <td>
	                      <input name="nom" class="form-control">
	                    </td>
	                  	</tr>
	                  <tr>
	                    <td>CNE</td>
	                    <td>
	                      <input name="cne" class="form-control">
	                    </td>
	                  </tr>
	                  <tr>
	                    <td>Filiere
	                      <br> </td>
	                    <td>
	                      <input name="filiere" class="form-control">
	                    </td>
	                  </tr>
	                  <tr>
	                    <td>Age</td>
	                    <td>
	                     	<input name="age" class="form-control">
	                     	<% if( request.getAttribute("errorAge")!=null ){ %>
								<small style="border:none;" colspan="2" class="text-danger">L'age doit etre numeric au moins 18 ans</small> 
							<% } %>
	                     </td>
	                  </tr> 
	                  
	                  <tr>
	                    <td>&nbsp;</td>
	                    <td> 
	                    <br>
	                      <button class="btn btn-primary" type="submit">Ajouter
	                        <br> </button>
	                        <button class="btn btn-secondary" type="reset">Effacer<br>
	                    	</button>
	                    </td>
	                  </tr>
	                </tbody>
              	</table>
              	</form>
              	</div>
          	</div>
    </div>
    </div>
 	</div> 
  

</body>

</html>