#language: es
#Autor: Aragunde Mauro.-
#Fecha de actualización: 28.06.2017
# Un ID Postal es la combinación de:
# 	PAIS + PROVINCIA + CIUDAD + CODIGO POSTAL
# ID Postal: relación entre un PAIS + PROVINCIA + CIUDAD + CODIGO POSTAL
# Esa combinación debe ser única, y es inmutable, no hay modificación
# solo alta o baja.
# Cuando asignamos un ID Postal a una sucursal, quiere decir que los envios
# que tengan como destino esa ID Postal, serán distribuidos por esa sucursal.
Característica: Asignacion ID Postal

  @asignacionIDPostal
  Escenario: Asignar un ID Postal no asignado a una sucursal
    Dado que quiero asignar un ID Postal no asignado a la sucursal "Barracas"
    Cuando se lo asigno a "Barracas" con forma de atencion por sucursal
    Entonces el ID Postal deja de estar pendiente de asignación

  Escenario: Intentar asignar un ID Postal ya asignado a una sucursal
    Dado que el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" esta asignado a la sucursal de distribucion "Temperley"
    Entonces el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" no permite ser asignado a otra sucursal
    Cuando desasigno el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" de "Temperley"
    Entonces puedo asignar el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" a "Barracas"

  ## Actualmente el envío se crea igual y queda con toBranch = -1
  @noEstable
  Escenario: ID Postal no asignado a una sucursal
    Dado que el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" no esta asignado a una sucursal de distribucion
    Y por lo tanto no esta definida la relación ID Postal - Sucursal de distribución
    Cuando doy de alta un envío con ID Postal destino 1834 BUENOS AIRES TEMPERLEY
    Entonces no se va a dar de alta el número Andreani

  Escenario: ID Postal asignado a una sucursal
    Dado que el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" esta asignado a la sucursal de distribucion "Temperley"
    Y por lo tanto esta definida la relación ID Postal - Sucursal de distribución
    Cuando doy de alta un envío con ID Postal destino 1834 BUENOS AIRES TEMPERLEY
    Entonces se va a dar de alta el número Andreani
    Y la sucursal responsable de distribuir el envío es "Temperley"

  Escenario: Envíos dados de alta luego de la reasignación de ID Postal
    Dado que el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" esta asignado a la sucursal de distribucion "Temperley"
    Cuando reasigno el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" de "Temperley" a "Barracas"
    Y doy de alta un envío con ID Postal destino 1834 BUENOS AIRES TEMPERLEY
    Entonces la sucursal responsable de distribuir el envío es "Barracas"
    Cuando reasigno el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" de "Barracas" a "Temperley"
    Y doy de alta un envío con ID Postal destino 1834 BUENOS AIRES TEMPERLEY
    Entonces la sucursal responsable de distribuir el envío es "Temperley"

  @noAutomatizable
  Escenario: Envíos dados de alta antes de la reasignación de ID Postal
    Dado que el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" esta asignado a la sucursal "Temperley"
    Y hay envíos dados de alta a este ID Postal
    Cuando Reasigno el ID Postal "(1834) BUENOS AIRES,TEMPERLEY" a la sucursal "Barracas"
    Entonces los envíos que ya estaban dados de alta hay que migrarlos por Base de Datos

  @noAutomatizable
  Escenario: Configuración de "Concesionario"
    Dado que el ID postal "(2645) CORDOBA, O HIGGINS" no se atiende diariamente por que esta ciudad se encuentra lejos de la sucursal de distribución
    Cuando se configura ese ID Postal, se le asigna forma de atención por concesionario
    Entonces los envíos a ese ID Postal son distribuidos por personal externo a Andreani
    Y no deberían ser distribuidos por personal de Andreani (forma de atencion por "sucursal")

  @noAutomatizable
  Escenario: Configuración de "Sucursal"
    Dado que el ID postal "(1834) BUENOS AIRES,TEMPERLEY" se atiende diariamente porque esta ciudad se encuentra cerca de la sucursal de distribución
    Cuando se configura ese ID Postal, se le asigna forma de atención por sucursal
    Entonces los envíos a ese ID Postal son distribuidos por personal de Andreani
    Y no deberían ser distribuidos por personal externo a Andreani (forma de atencion por "concesionario")
