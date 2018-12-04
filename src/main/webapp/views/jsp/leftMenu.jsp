
<ul class="nav nav-pills flex-column"> 

  <li class="nav-item  ">
    <a class="nav-link"><u>Menu :</u></a>
  </li>
  
  <li class="nav-item  ">
    <a class="nav-link" href="<%=request.getContextPath()%>/EtudiantController/index">Liste etudiants</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" href="<%=request.getContextPath()%>/EtudiantController/ajouter">Nouveau etudiant</a>
  </li>
  
  <form name="f" class="form-inline" action="<%=request.getContextPath()%>/EtudiantController/index">
  <li class="nav-item" style="padding:15px">
 	<br>
  	<u>Rechercher un etudiant</u><br>
  	Par nom : 
	  <div class="input-group">
	    <input value="<%= request.getAttribute("motCleNom")!=null?request.getAttribute("motCleNom"):"" %>" type="text" name="motCleNom" class="form-control form-control-sm" placeholder="Mot-cle"><br>
	    <div class="input-group-append">
	      <button class="btn btn-primary" type="submit">Go</button>
	    </div>
	  </div> 
  </li>
  
  <li class="nav-item" style="padding:15px"> 
  	Par CNE : 
	  <div class="input-group">
	    <input value="<%= request.getAttribute("motCleCne")!=null?request.getAttribute("motCleCne"):"" %>" type="text" name="motCleCne" class="form-control form-control-sm" placeholder="Mot-cle"><br>
	    <div class="input-group-append">
	      <button class="btn btn-primary" type="submit">Go</button>
	    </div>
	  </div> 
  </li>
  
  <li class="nav-item" style="padding:15px"> 
  	Par filiere : 
	  <div class="input-group">
	    <input  value="<%= request.getAttribute("motCleFiliere")!=null?request.getAttribute("motCleFiliere"):"" %>" type="text" name="motCleFiliere" class="form-control form-control-sm" placeholder="Mot-cle"><br>
	    <div class="input-group-append">
	      <button class="btn btn-primary" type="submit">Go</button>
	    </div>
	  </div> 
  </li> 
  
  	<li style="padding-left:15px">  
  	<button  type="button"
     	onclick="document.f.motCleNom.value='';document.f.motCleCne.value='';document.f.motCleFiliere.value='';document.f.filiere.value='';">Effacer<br>
   	</button>
   	</li>
  </form>
  
  
</ul>
<br>
<br>
<br>
