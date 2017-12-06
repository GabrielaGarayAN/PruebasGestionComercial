#language: es
#Autor: Aragunde Mauro.-
#Fecha de actualización: 28.06.2017
#Definición:
#		Una sucursal 'Y' que es nodo de otra sucursal 'X', recibe la expedición de
#		la sucursal 'X' y la envía a otra sucursal para que distribuya. Esto quiere
# 	decir que 'Y' funciona como un punto medio entre dos o más sucursales.
## El trafico a nodos es de tipo troncal, se expiden y recepcionan
## contenedores con muchos envíos, que luego se expiden a la sucursal
## de distribución. Algo similar en informática sería un router, el
## cual gestiona el tráfico entre diferentes computadoras.
Característica: Asignación de Nodos

  @noAutomatizable
  Escenario: Nodo funcionando correctamente
    Dado que se da de alta un envío de 'PSD' a 'Casilda'
    Y que 'Rosario'es nodo de la sucursal periferica 'Casilda'
    Cuando expido la colectora desde 'PSD' con destino 'Casilda'
    Entonces puedo realizar la recepción de la expediciòn de la colectora en 'Rosario'
    Y volverla a expedir a 'Casilda' en otra unidad

  @noAtomatizable
  Escenario: Nodo no configurado
    Dado que se da de alta un envío de 'PSD' a 'Casilda'
    Y que 'Rosario' NO es nodo de la sucursal periferica 'Casilda'
    Cuando expido la colectora desde 'PSD' con destino 'Casilda'
    Entonces NO debería poder realizar la recepción de la expedición de la colectora en 'Rosario'
    Pero si la recepciono en 'Rosario' me indica que esta mal canalizada
