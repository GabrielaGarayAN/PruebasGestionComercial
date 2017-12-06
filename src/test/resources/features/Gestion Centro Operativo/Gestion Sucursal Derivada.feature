#language: es
#Autor: Aragunde Mauro.-
#Fecha de actualización: 28.06.2017
#Descripción: Esta funcionalidad se configuraba desde un campo dentro del formulario de alta/modificación de CO	pero se removió del mismo. 
# Se dejan los escenarios a modo informativo.
Característica: Sucursal Derivada

  @noAutomatizable
  Escenario: Alta de envío para sucursal derivada
    Dado que "Temperley" tiene como sucursal derivada a "Burzaco"
    Cuando doy de alta un envio con un contrato con distribucion de tipo "Central"
    Y con un Codigo Postal destino asignado a "Temperley"
    Entonces el envio es distribuido por "Burzaco"

  # Se coonfigura por la matriz de derivacion de sucursales segun contrato
  # SOLO APLICA EN ENVÍOS DE TIPO 'Cabecera'
  @noAutomatizable
  Escenario: Alta de envío según matriz de derivacion de sucursales
    Dado el CP "1833" es atendido normalmente por "Temperley"
    Pero el contrato "XX" tiene configurado que para el CP "1833" lo distribuye "Burzaco"
    Cuando doy de alta un envio de tipo "Cabecera" con el contrato "XX" al CP "1833"
    Entonces el envio es distribuido por "Burzaco"
