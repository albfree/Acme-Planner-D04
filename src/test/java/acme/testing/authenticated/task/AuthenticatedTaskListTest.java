package acme.testing.authenticated.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AuthenticatedTaskListTest extends AcmePlannerTest{
	
	/*
     * listAll(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String description, final String visibility, final String link):
     *
     *         - Caso positivo de la acción list sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados positivos comprobando que es posible listar y visualizar las tareas siendo un usuario autenticado.
     *         - Los datos utilizados en el fichero .csv son tareas válidas.            
     *             
     */
	
	@ParameterizedTest
	@CsvFileSource(resources="/authenticated/task/list_all.csv", encoding="utf-8", numLinesToSkip = 1 )
	@Order(10)
	public void listAll(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String description, final String visibility, final String link) {
		super.signIn("manager2", "manager2");
		super.clickOnMenu("Authenticated", "Tasks list");
		//Comprobamos que todos los campos tienen valor
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startPeriod);
		super.checkColumnHasValue(recordIndex, 2, endPeriod);
		super.checkColumnHasValue(recordIndex, 3, workload);
		//Hacemos click en lo que le pasemos como parametro
		super.clickOnListingRecord(recordIndex);
		//comprobamos que los campos de la tarea autenticada seleccionada estan completos
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startExecutionPeriod", startPeriod);
		super.checkInputBoxHasValue("endExecutionPeriod", endPeriod);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("share", visibility);
		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}
}
