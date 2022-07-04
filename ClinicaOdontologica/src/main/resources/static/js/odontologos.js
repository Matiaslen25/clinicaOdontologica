const divCrudOdontologos = document.querySelector('#crudOdontologos')
let odontologos

const formularioOdontologos = document.getElementById('altaModificacionOdontologo')
const divIdOdontologoFormulario = document.getElementById('divIdOdontologo')
const inputIdOdontologo = document.getElementById('idOdontologo')
const nombre = document.getElementById('nombre')
const apellido = document.getElementById('apellido')
const matricula = document.getElementById('matricula')
const botonLimpiar = document.getElementById('botonLimpiar')

botonLimpiar.addEventListener('click', event => {
  event.preventDefault()
  divIdOdontologoFormulario.setAttribute('hidden', true)
  inputIdOdontologo.value = 0
  nombre.value = ''
  apellido.value = ''
  matricula.value = ''
})

const editarOdontologos = event => {
  const idOdontologo = Number.parseInt(event.target.id.split('-')[1])
  const odontologo = odontologos.find(odontologo => odontologo.id === idOdontologo)

  nombre.value = odontologo.nombre
  apellido.value = odontologo.apellido
  matricula.value = odontologo.matricula

  inputIdOdontologo.value = idOdontologo
  divIdOdontologoFormulario.removeAttribute('hidden')
}

const eliminarOdontologos = async event => {
  const idOdontologo = Number.parseInt(event.target.id.split('-')[1])
  const odontologo = odontologos.find(odontologo => odontologo.id === idOdontologo)

  if (odontologo && confirm(`¿Está seguro/a de querer eliminar al odontólogo ${odontologo.nombre} ${odontologo.apellido}?`)) {
    const settings = {
      method: 'DELETE',
      headers: {
        'Content-type': 'application/json; charset=UTF-8'
      }
    }

    await fetch(`http://localhost:8080/odontologos/${idOdontologo}`, settings)
    location.reload()
  }
}

const crearEditar = (id) => {
  const aEditar = document.createElement('button')
  aEditar.innerText = 'Editar'
  aEditar.href = '#'
  aEditar.id = `editarOdontologo-${id}`
  aEditar.className = 'editarOdontologo'

  return aEditar
}

const crearEliminar = (id) => {
  const aEditar = document.createElement('button')
  aEditar.innerText = 'Eliminar'
  aEditar.href = '#'
  aEditar.id = `eliminarOdontologo-${id}`
  aEditar.className = 'eliminarOdontologo'

  return aEditar
}

window.addEventListener('load', async _ => {
  const doFetch = await fetch('http://localhost:8080/odontologos')
  odontologos = await doFetch.json()

  if (odontologos && odontologos.length) {
    // <a class="edit" href="JavaScript:void(0);">Edit</a>
    divCrudOdontologos.innerHTML += '<div class="header"><div class="header-item">Id</div><div class="header-item">Nombre</div><div class="header-item">Apellido</div><div class="header-item">Matrícula</div><div class="header-item">Editar</div><div class="header-item">Eliminar</div></div>'
    divCrudOdontologos.innerHTML += '<div class="table-content">'
    for (let index = 0; index < odontologos.length; index++) {
      const odontologo = odontologos[index]

      const aEditar = crearEditar(odontologo.id)
      const divEditar = document.createElement('div')
      divEditar.className = 'row-data'
      divEditar.appendChild(aEditar)

      const aEliminar = crearEliminar(odontologo.id)
      const divEliminar = document.createElement('div')
      divEliminar.className = 'row-data'
      divEliminar.appendChild(aEliminar)

      const divRow = document.createElement('div')
      divRow.className = 'row'
      divRow.innerHTML = `<div class="row-data">${odontologo.id}</div><div class="row-data">${odontologo.nombre}</div><div class="row-data">${odontologo.apellido}</div><div class="row-data">${odontologo.matricula}</div>`
      divRow.appendChild(divEditar)
      divRow.appendChild(divEliminar)

      divCrudOdontologos.appendChild(divRow)
    }

    divCrudOdontologos.innerHTML += '</div>'

    const aEditarList = document.querySelectorAll('.editarOdontologo')
    const aEliminarList = document.querySelectorAll('.eliminarOdontologo')
    for (let index = 0; index < aEditarList.length; index++) {
      const aEditar = aEditarList[index]
      aEditar.addEventListener('click', editarOdontologos)

      const aEliminar = aEliminarList[index]
      aEliminar.addEventListener('click', eliminarOdontologos)
    }
  }
})

formularioOdontologos.addEventListener('submit', async form => {
  form.preventDefault()

  if (nombre.value && apellido.value && matricula.value) {
    const jsonData = {
      nombre: nombre.value,
      apellido: apellido.value,
      matricula: matricula.value
    }

    let url = 'http://localhost:8080/odontologos'
    let method = 'POST'

    if (Number.parseInt(inputIdOdontologo.value) > 0) {
      jsonData.id = inputIdOdontologo.value
      url += '/update'
      method = 'PUT'
    }

    const settings = {
      method: method,
      body: JSON.stringify(jsonData),
      headers: {
        'Content-type': 'application/json; charset=UTF-8'
      }
    }

    await fetch(url, settings)
    location.reload()
  } else {
    alert('Por favor, completa todos los campos')
  }
})
