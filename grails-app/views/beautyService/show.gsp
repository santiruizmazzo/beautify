<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reservar turno</title>
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
          <g:link class="btn btn-outline-primary" controller="beautyService" action="index">Atras</g:link>
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-lg-7">
          <h1>${beautyService.name}</h1>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-7">
          <div class="bs-component">
            <p>Descripcion: ${beautyService.description}</p>
            <p>Categoria: ${beautyService.category.toString().toLowerCase()}</p>
            <p>Precio del servicio: $${beautyService.price}</p>
            <p>Duracion del servicio: ${beautyService.duration} minutos</p>
          </div>
        </div>
      </div>

      <g:form controller="appointment">
        <input name="beautyServiceId" type="hidden" value="${beautyService.id}"/>
        <input name="sendingDate" type="hidden" value="${java.time.LocalDateTime.now().minusSeconds(5)}">
        <div class="row mb-3">
          <div class="col-lg-7">
            <g:datePicker name="startDate" precision="minute" relativeYears="${[0..0]}" default="${java.time.LocalDate.now()}"/>
          </div>
        </div>
        <div class="row mb-3">
          <div class="col-lg-7">
            <g:actionSubmit class="btn btn-primary btn-lg" value="Reservar" action="make"/>
          </div>
        </div>
      </g:form>

      <g:if test="${flash.error}">
        <div class="row">
          <div class="col-lg-5">
            <div class="bs-component">
              <div class="alert alert-dismissible alert-secondary">
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                <strong>Error!</strong>
                <p>${flash.error.join(', ')}</p>
              </div>
            </div>
          </div>
        </div>
      </g:if>

    </div>
  </body>
</html>
