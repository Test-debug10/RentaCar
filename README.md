# Proyecto RentaCar

**Equipo de Desarrollo:**
* José Valderrama
* Matias Cabezas
* Cristobal Montealegre

---

## Microservicios
1. `ms-usuarios` (Puerto: 8082)
2. `ms-vehiculos` (Puerto: 8081)
3. `ms-reservas` (Puerto: 8083) - *Integra OpenFeign*
4. `ms-pagos` (Puerto: 8084)
5. `ms-sucursales` (Puerto: 8085)
6. `ms-mantenimiento` (Puerto: 8086) - *Integra OpenFeign*
7. `ms-seguros` (Puerto: 8087) - *Integra OpenFeign*
8. `ms-extras` (Puerto: 8088)
9. `ms-evaluaciones` (Puerto: 8089) - *Integra OpenFeign*
10. `ms-notificaciones` (Puerto: 8090)
* `eureka-server` (Puerto: 8761)
* `api-gateway` (Puerto: 8080)

---

## Rutas
Todo debe pasar por: localhost:8080

Por ejemplo:
* Usuarios: `http://localhost:8080/api/v2/usuarios`
* Vehículos: `http://localhost:8080/api/v2/vehiculos`
* Reservas: `http://localhost:8080/api/v2/reservas`

---

## Swagger/Documentacion
La documentación interactiva de cada microservicio se encuentra disponible levantando el proyecto y accediendo a:
* `http://localhost:[PUERTO_DEL_MS]/swagger-ui.html`

* ---

## Indicaciones para hacer funcionar el proyecto
1) Abrir el powershell de vsc dentro de la carpeta principal del proyecto
2) Escribir * `.\start_rentacar.bat` en el powershell y esperar a que carguen los servicios.
