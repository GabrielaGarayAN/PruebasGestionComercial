#language: es
#Autor: Aragunde Mauro.-
#Fecha de actualización: 28.06.2017
Característica: Baja de Sucursal

  ## POR AHORA SOLO ESTÁ LA BAJA LÓGICA DE LA SUCURSAL, QUE LA INHABILITA,
  ## PERO LAS ENTIDADES ASOCIADAS A LA MISMA NO SON	MODIFICADAS
  Antecedentes: 
    Dado que estoy logueado

  @bajaCO @TestngScenario
  Escenario: Dar de baja una sucursal existente sin envíos asociados
    Dado que quiero dar de baja la sucursal "Turdera"
    Cuando confirmo la baja de la sucursal
    Entonces la sucursal deja de existir
