package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerTaskListAllTest extends AcmePlannerTest {

	/*
     * listAllPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String description, final String visibility, final String link):
     *
     *         - Caso positivo de la acción list sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados positivos comprobando que las entidades aparecen en el listado y pueden mostrarse con todos sus datos.
     *         - Los datos utilizados en el fichero .csv son tareas válidas
     *         
     */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listAllPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String description, final String visibility, final String link) { 
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Tasks list");
		
		super.checkNotPanicExists();
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startPeriod);
		super.checkColumnHasValue(recordIndex, 2, endPeriod);
		super.checkColumnHasValue(recordIndex, 3, workload);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startExecutionPeriod", startPeriod);
		super.checkInputBoxHasValue("endExecutionPeriod", endPeriod);
		super.checkInputBoxHasValue("workload", workload);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("share", visibility);
		super.checkInputBoxHasValue("link", link);

		super.signOut();
	}
	
	/*
     * authorizeNegative():
     *
     *         - Caso negativo de la acción list sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados negativos comprobando que un usuario no autorizado intente acceder a la ruta especificada.
     *         
     */
	@Test
	@Order(20)
	public void authorizeNegative() {
		super.signIn("administrator", "administrator");
		
		super.navigate("/manager/task/list", null);
		
		super.checkPanicExists();
		
		super.signOut();
	}
	
}
