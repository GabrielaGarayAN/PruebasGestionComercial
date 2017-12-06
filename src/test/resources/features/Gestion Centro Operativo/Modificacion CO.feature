#language: es
#Autor: Aragunde Mauro.-
#Fecha de actualización: 28.06.2017
Característica: Modificar Sucursal

  Antecedentes: 
    Dado que estoy logueado

  @modificarCO
  Escenario: Modificación nombre y nomenclatura
    Dado que quiero cambiar el responsable de la sucursal "Turdera"
    Cuando cambio el responsable a "Aldano Pelusso"
    Entonces el responsable de la sucursal pasa a ser "Aldano Pelusso"
    Dado que quiero cambiar el nombre y nomenclatura de la sucursal "Turdera"
    Cuando cambio el nombre de "Turdera" por "Turdera2" y su nomenclatura de "TUR" a "TUR2"
    Entonces la sucursal "Turdera" pasa a llamarse "Turdera2" y su nomenclatura es "TUR2"
