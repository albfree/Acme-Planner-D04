
package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanListTest extends AcmePlannerTest {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------
	
	/*
	 * - Las features que se comprueban en este test son el listar y mostrar los planes de trabajo por un manager.
	 * - El test comprobará que los datos que se muestran en el listado y en los detalles de los planes de trabajo son correctos.
	 * 
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/workplan/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void list(final int recordIndex, final String title, final String startExecutionPeriod, final String endExecutionPeriod, final String share, final String totalWorkload) {
		super.signIn("manager1", "manager1");

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
		super.checkInputBoxHasValue("totalWorkload", totalWorkload);

		super.signOut();
	}

	// Ancillary methods ------------------------------------------------------

}
