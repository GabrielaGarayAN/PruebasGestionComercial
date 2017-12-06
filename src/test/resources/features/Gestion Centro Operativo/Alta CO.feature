#language: es
#Autor: Aragunde Mauro.-
#Fecha de actualización: 28.06.2017

# Una vez dada de alta la sucursal es necesario configurar otros aspectos de la misma
# para que quede totalmente operativa, como por ejemplo asignarle sus ID Postales
# (referirse al feature de ID Postales).
Característica: Alta de Sucursal

Antecedentes:
	Dado que estoy logueado

  @altaCO @TestngScenario
  Escenario: Dar de alta una sucursal inexistente correctamente
    Dado que quiero crear la sucursal "Turdera"
    Cuando ingreso los siguientes datos y confirmo
      | Campo               | Valor                              |
      | Nombre CO           | Turdera                            |
      | Tipo                | Sucursal                           |
      | Responsable         | Mauro Aragunde                     |
      | Calle               | Santo Domingo 400, Turdera         |
      | Provincia           | Buenos Aires                       |
      | Localidad           | Turdera                            |
      | Telefono            | 4444 8888                          |
      | Mail                | testautomatizado@andreani.com      |
      | Horarios Atencion   | De Lunes a Viernes de 8:00 a 16:00 |
      | Region              | Bs As Sur                          |
      | Atencion al Cliente | Si                                 |
      | Usa PAD             | Si                                 |
    Entonces la sucursal se crea correctamente

	@TestngScenario
  Escenario: Dar de alta una sucursal sin todos los datos obligatorios
    Dado que quiero crear la sucursal "Turder2"
    Cuando ingreso los siguientes datos y confirmo
      | Campo               | Valor                              |
      | Nombre CO           | Turdera                            |
      | Tipo                | Sucursal                           |
      | Responsable         |                                    |
      | Calle               | Santo Domingo 400, Turdera         |
      | Provincia           | Buenos Aires                       |
      | Localidad           | Turdera                            |
      | Telefono            | 4444 8888                          |
      | Mail                | testautomatizado@andreani.com      |
      | Horarios Atencion   | De Lunes a Viernes de 8:00 a 16:00 |
      | Region              |                                    |
      | Atencion al Cliente |                                    |
      | Usa PAD             |                                    |
    Entonces veo un mensaje de indicando los datos faltantes
    Y la sucursal no se da de alta
