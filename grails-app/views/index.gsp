<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Inicio</title>
    <link href="https://cdn.jsdelivr.net/npm/bootswatch@5.3.1/dist/minty/bootstrap.min.css" rel="stylesheet">
    <link href="https://bootswatch.com/_assets/css/custom.min.css" rel="stylesheet">
  </head>
  <body>

    <div class="navbar navbar-expand-lg fixed-top bg-primary" data-bs-theme="dark" style="">
      <div class="container">
        <a class="navbar-brand" href="/"><b>Beautify</b></a>
      </div>
    </div>

    <div class="container">

      <g:if test="${!session.customerId}">
        <g:form controller="customer">
          <div class="bs-component">
          <div class="col-lg-4">
            <div class="card border-primary">
              <div class="card-header">Login</div>
              <div class="card-body">
                <div class="form-group">
                  <label class="form-label mt-4">Inicie sesion con su cuenta</label>
                  <div class="form-floating mb-3">
                    <input name="email" type="email" class="form-control" id="floatingInput" value="santimazzo98@gmail.com">
                    <label for="floatingInput">Email</label>
                  </div>
                  <div class="form-floating mb-3">
                    <input name="password" type="password" class="form-control" id="floatingPassword" autocomplete="off" value="11111">
                    <label for="floatingPassword">Contraseña</label>
                  </div>
                  <div class="d-grid gap-2">
                    <g:actionSubmit class="btn btn-lg btn-primary" value="Iniciar sesion" action="login"/>
                  </div>
                  
                </div>
              </div>
            </div>
          </div>
          </div>
        </g:form>
      </g:if>
      <g:else>
        <div class="row mb-3">
          <div class="col-lg-7">
            <h1>Bienvenido a Beautify!</h1>
          </div>
        </div>
        <div class="d-grid gap-2">
          <g:link class="btn btn-lg btn-primary" controller="appointment" action="list"><b>Mis turnos</b></g:link>
          <g:link class="btn btn-lg btn-primary" controller="beautyService" action="list"><b>Reservar turno</b></g:link>
        </div>
      </g:else>
      
    </div>

    <script src="../_vendor/bootstrap/dist/js/bootstrap.bundle.min.js"></script>
    <script src="../_vendor/prismjs/prism.js" data-manual=""></script>
    <script src="../_assets/js/custom.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>

  </body>
</html>