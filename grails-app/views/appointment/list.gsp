<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mis turnos</title>
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
          <h1><b>Mis turnos</b></h1>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-7">
          <g:if test="${appointments.count { it.cancelled } == appointments.size}">
            No has reservado ningun turno aun.
          </g:if>
        </div>
      </div>

      <g:each var="appointment" in="${appointments}">
        <g:if test="${!appointment.cancelled}">
          <div class="row">
            <div class="col-lg-20">
              <div class="bs-component">
                <div class="card border-primary mb-3" style="max-width: 20rem;">
                  <g:link action="detail" id="${appointment.id}" style="text-decoration:none">
                    <div class="card-header"><b>${appointment.beautyService.name}</b></div>
                    <div class="card-body">
                      <p>${appointment.timeRange}</p>
                      <p>$${appointment.servicePriceWhenBooked}</p>
                    </div>
                  </g:link>
                </div>
              </div>
            </div>
          </div>
        </g:if>
      </g:each>

    </div>

  </body>
</html>
