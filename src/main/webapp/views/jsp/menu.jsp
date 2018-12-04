<nav class="navbar navbar-expand-md bg-success navbar-dark">
    <div class="container">
      <a class="navbar-brand" href="#">Gestion des etudiants
        <br>
      </a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbar2SupportedContent" aria-controls="navbar2SupportedContent" aria-expanded="false" aria-label="Toggle navigation"> <span class="navbar-toggler-icon"></span> </button>
      <div class="collapse navbar-collapse text-center justify-content-end" id="navbar2SupportedContent">
        <ul class="navbar-nav">
          <li class="nav-item">
          	<div src="" style="border:solid white 0.01em;border-radius:100%; width:40px; height: 40px; "></div>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/UserController/deconnecter">Deconnecter
              <br>
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/UserController/profile">Mon profile
              <br>
            </a>
          </li> 
        </ul> 
      </div>
    </div>
  </nav>
  <style>
	 .card{border:solid 1px} .col-md-3{border-right: solid 0.5px}  .col{padding-left:0;} #contenu{padding-left:20px;} 
</style>