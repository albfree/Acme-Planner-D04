package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerTaskDeleteTest extends AcmePlannerTest {

	/*
     * deletePositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String nextTitle):
     *
     *         - Caso positivo de la acción delete sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados positivos comprobando que la entidad seleccionada se ha borrado y ya no existe en listado.
     *         - Los datos utilizados en el fichero .csv son tareas válidas
     *         
     */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void deletePositive(final int recordIndex, final String title) {
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Tasks list");
		
		super.checkNotPanicExists();
		
		super.checkColumnHasValue(recordIndex, 0, title);
		
		super.clickOnListingRecord(recordIndex);

		super.clickOnSubmitButton("Delete");
		
		super.signOut();
	}
	
	/*
     * deleteNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload):
     *
     *         - Caso negativo de la acción delete sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados negativos comprobando que aparecen errores al intentar eliminar la entidad seleccionada.
     *         - Los datos utilizados en el fichero .csv son tareas inválidas
     *             - El test comprueba que se violan las siguientes restricciones:
     *             - Restriccion 1: No debe pertenecer a un plan de trabajo.
     *             
     */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void deleteNegative(final int recordIndex, final String title) {
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Tasks list");
		
		super.checkNotPanicExists();
		
		super.checkColumnHasValue(recordIndex, 0, title);
		
		super.clickOnListingRecord(recordIndex);

		super.clickOnSubmitButton("Delete");

		super.checkErrorsExist();

		super.signOut();
	}
	
	/*
     * authorizeNegative():
     *
     *         - Caso negativo de la acción delete sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados negativos comprobando que un usuario no autorizado intente acceder a la ruta especificada.
     *         
     */
	@Test
	@Order(30)
	public void authorizeNegative() {
		super.signIn("administrator", "administrator");
		
		super.navigate("/manager/task/delete", null);
		
		super.checkPanicExists();
		
		super.signOut();
	}
}
