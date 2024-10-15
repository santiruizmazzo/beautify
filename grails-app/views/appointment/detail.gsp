<!doctype html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Detalle del turno</title>
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
          <g:link class="btn btn-outline-primary" controller="appointment" action="list">Atras</g:link>
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-lg-7">
          <h1>${appointment.beautyService.name}</h1>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-20">
          <p>Categoria: ${appointment.beautyService.category.toString().toLowerCase()}</p>
          <p>Precio del servicio reservado: $${appointment.servicePriceWhenBooked}</p>
          <p>${appointment.timeRange}</p>
        </div>
      </div>

      <g:if test="${appointment.attended}">
        <g:if test="${appointment.isRated()}">
          <div class="col-lg-3">
            <p>Calificacion: ${appointment.rating}/5</p>
            <div class="progress">
              <div class="progress-bar" role="progressbar" aria-valuenow="${appointment.rating}" aria-valuemin="0" aria-valuemax="5" style="width: ${appointment.rating/5*100}%;"></div>
            </div>
            <p>Comentario: "${appointment.comment}"</p>
          </div>
        </g:if>
        <g:elseif test="${appointment.isWithinRatingTime()}">
          <g:form controller="appointment">
            <input name="appointmentId" type="hidden" value="${appointment.id}"/>
            
            <div class="row">
              <div class="col-lg-3">
                <g:select class="form-select" name="rating" from="${0..5}" noSelection="['':'Califica tu experiencia']"/>
             </div>
            </div>
            
            <div class="row mb-3">
              <div class="col-lg-5">
                <label for="exampleTextarea" class="form-label mt-4">Escriba un comentario sobre su experiencia</label>
                <textarea name="comment" class="form-control" id="exampleTextarea" rows="3"></textarea>                
              </div>
            </div>

            <g:actionSubmit action="rate" class="btn btn-primary btn-lg" value="Enviar feedback"/>
          </g:form>
        </g:elseif>
      </g:if>
      <g:else>
        <g:if test="${appointment.isWithinCancellationTime()}">
          <g:form controller="appointment">
            <input name="id" type="hidden" value="${appointment.id}"/>
            <g:actionSubmit action="cancel" class="btn btn-secondary btn-lg" value="Cancelar" onclick="return confirm('¿Seguro queres eliminar el turno?')"/>
          </g:form>
        </g:if>
        <g:elseif test="${!appointment.canStillAttend()}">
          <span class="badge bg-danger">No asististe</span>
        </g:elseif>
      </g:else>
    </div>

  </body>
</html>
