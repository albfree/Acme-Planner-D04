# README.txt
#
# Copyright (C) 2012-2021 Rafael Corchuelo.
#
# In keeping with the traditional purpose of furthering education and research, it is
# the policy of the copyright owner to permit non-commercial use and redistribution of
# this software. It has been tested carefully, but it is not guaranteed for any particular
# purposes.  The copyright owner does not offer any warranties or representations, nor do
# they accept any liabilities with respect to them.

This is the Starter Project, which is intended to be a core learning asset for the students
who have enroled the Design and Testing subject of the Software Engineering curriculum of the 
University of Sevilla.  This project helps them start working on their new information system 
projects.

This is Acme Planner D04, the fourth project of Group 11 for Design & Testing 2 subject.

# Apunte sobre Clever Cloud: debido a un error que se desconocía, en la aplicación desplegada las
URLs que contienen /manager provocan errores. Para solucionarlo, se modifica el campo 'context' del war.json
y para probar la aplicación desplegada habrá que añadir a la URL que proporciona Clever Cloud
lo siguiente: /Acme-Planner

### TERCERA ENTREGA - Interpretaciones y consideraciones:

- Hay servicios en los que no se alcanza el 60% debido a que los métodos unbind no se ejecutan, por ejemplo,
en los update no se ejecutan ya que la petición GET se hace contra el show y al pulsar el botón Update se envía el POST.
Aún así, la lógica de negocio importante como las validaciones se ejecutan.

Dichos servicios son: ManagerWorkPlanUpdateService, ManagerWorkPlanDeleteService, AdministratorCustomisationUpdateService,
ManagerTaskDeleteService

### SEGUNDA ENTREGA - Interpretaciones y consideraciones:

- La funcionalidad de hacerse manager no está implementada porque no se pide como requisito. Para solventar
este problema, se añade al initial-data.xml un manager con las siguientes credenciales: manager / manager

- Se ruega, por favor, no realizar pruebas con horas muy cercanas (entorno a menos de dos horas) puesto que 
JPQL tiene en cuenta la hora de base de datos sin sumar las 2 horas de menos con las que se guarda una entidad.
En cambio, en Java sí se tiene en cuenta la hora original y puede provocar incongruencias con respecto a los
requisitos.

- Al no concretarse en los requisitos lo contrario, se interpreta que no es necesario incluir @Past para
el atributo 'moment' de la entidad Shout. Al realizarse la creación de un Shout, siempre estará en
pasado porque así se ha implementado en el servicio correspondiente. La decisión de no incluir 
@Past se toma para poder introducir ejemplos de la entidad Shout con fecha de creación posterior 
a la del entregable, de forma que se simplifica la corrección al profesor al listar Shouts.

- En atributos de texto para los que no se pide un máximo de longitud como, por ejemplo, el link opcional
en un Shout, se limitan a 255 caracteres porque más resulta en un error al guardar en base de datos.

- Para la entidad Task se pide 500 caracteres como máximo en el atributo 'description', pero el framework 
no da la posibilidad a aumentar un campo de texto a 500 caracteres en base de datos, por tanto, se limita 
al máximo posible que son 255.

- Se interpreta como Task finalizada aquella cuyo final de periodo de ejecución es anterior al momento actual.

- Se pide como requisito que una Task al ser creada debe tener el periodo de ejecución en el futuro. Se interpreta
que al actualizarla no hace falta realizar dicha comprobación puesto que para una actualización de título, por ejemplo,
obligaría a cambiar las fechas del periodo de ejecución.

- Dado que en el nivel C se especifica que un usuario Anonymous no puede listar ni mostrar aquellas tareas que sean
privadas y/o haya terminado su periodo de ejecución, interpretaremos lo mismo a la hora de listar y mostrar las
tareas de un WorkPlan por parte de un usuario Anonymous.

- No se dejará eliminar una tarea mientras pertenezca a un WorkPlan, se mostrará un mensaje de información que
avise al usuario de que debe eliminar primero la tarea de la lista de tareas de los WorkPlans a los que pertenezca.

- Aunque no se solicita en los requisitos, se añade un atributo título a la entidad WorkPlan para facilitar la
identificación al listar y mostrar.