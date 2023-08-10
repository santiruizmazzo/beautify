<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Servicios de belleza</title>
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
      
      <div class="row mb-3">
        <div class="col-lg-7">
          <g:link class="btn btn-outline-primary" url="/">Atras</g:link>
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-lg-7">
          <h1><b>Servicios de belleza</b></h1>
        </div>
      </div>
      
      <div class="row">
        <div class="col-lg-7">
          <g:if test="${!beautyServices}">
            No hay ningun servicio de belleza disponible.
          </g:if>
        </div>
      </div>

      <g:each var="beautyService" in="${beautyServices}">
        <div class="row">
          <div class="col-lg-20">
            <div class="bs-component">
              <div class="card border-primary mb-3" style="max-width: 20rem;">
                <g:link action="show" id="${beautyService.id}" style="text-decoration:none">
                  <div class="card-header"><b>${beautyService.name}</b></div>
                  <div class="card-body">
                    <p>${beautyService.description}</p>
                  </div>
                </g:link>
              </div>
            </div>
          </div>
        </div>
      </g:each>

    </div>

  </body>
</html>
