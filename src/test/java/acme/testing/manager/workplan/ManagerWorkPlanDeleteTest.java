
package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanDeleteTest extends AcmePlannerTest {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	/*
	 * - La feature que se comprueba en este test es la eliminación de planes de trabajo por parte de un manager.
	 * - El test comprobará que la entidad seleccionada se ha borrado y ya no existe en listado.
	 * 
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/workplan/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void delete(final int recordIndex, final String title) {
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Work plans list");

		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);

		super.clickOnSubmitButton("Delete");

		super.signOut();
	}

	// Ancillary methods ------------------------------------------------------

}
