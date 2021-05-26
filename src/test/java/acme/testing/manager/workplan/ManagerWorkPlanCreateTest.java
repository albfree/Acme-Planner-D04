
package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanCreateTest extends AcmePlannerTest {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	/*
	 * - La feature que se comprueba en este test es el crear planes de trabajo sin errores por un manager.
	 * - El test comprobará que los datos que se muestran en el listado y en los detalles de los planes de trabajo son correctos
	 * después de crearlos.
	 * 
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/workplan/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String title, final String startExecutionPeriod, final String endExecutionPeriod, final String share) {
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Create work plan");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startExecutionPeriod", startExecutionPeriod);
		super.fillInputBoxIn("endExecutionPeriod", endExecutionPeriod);
		super.fillInputBoxIn("share", share);
		super.clickOnSubmitButton("Create");

		super.clickOnMenu("Manager", "Work plans list");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startExecutionPeriod);
		super.checkColumnHasValue(recordIndex, 2, endExecutionPeriod);
		super.checkColumnHasValue(recordIndex, 3, share);

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startExecutionPeriod", startExecutionPeriod);
		super.checkInputBoxHasValue("endExecutionPeriod", endExecutionPeriod);
		super.checkInputBoxHasValue("share", share);

		super.signOut();
	}

	/*
	 * - La feature que se comprueba en este test es el crear planes de trabajo con errores por un manager.
	 * - Los datos utilizados en el fichero .csv son planes de trabajo no válidos
	 * 		- El test comprueba que se violan las siguientes restricciones:
	 * 			- Restricción 1: La entidad no debe contener spam.
	 * 			- Restricción 2: El final del periodo de ejecución debe ser posterior al de inicio.
	 * 
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/workplan/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegative(final int recordIndex, final String title, final String startExecutionPeriod, final String endExecutionPeriod, final String share) {
		super.signIn("manager2", "manager2");

		super.clickOnMenu("Manager", "Create work plan");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startExecutionPeriod", startExecutionPeriod);
		super.fillInputBoxIn("endExecutionPeriod", endExecutionPeriod);
		super.fillInputBoxIn("share", share);
		super.clickOnSubmitButton("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	// Ancillary methods ------------------------------------------------------

}
