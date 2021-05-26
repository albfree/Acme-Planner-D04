
package acme.testing.manager.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class ManagerWorkPlanUpdateTest extends AcmePlannerTest {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------
	
	/*
	 * - La feature que se comprueba en este test es el actualizar planes de trabajo sin errores por un manager.
	 * - El test comprobará que los datos que se muestran en el listado y en los detalles de los planes de trabajo son correctos
	 * después de actualizarlos. Además, si después de actualizar el plan de trabajo éste tiene tareas, comprobará que las tareas
	 * son las que deben ser mediante el id.
	 * 
	 */

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/workplan/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void updatePositive(final int recordIndex, final String title, final String startExecutionPeriod, final String endExecutionPeriod, final String share, final String totalWorkload, final String addTask, final String deleteTask,
		final String checkTasks, final String newTitle) {
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Work plans list");
		
		super.checkColumnHasValue(recordIndex, 0, title);

		super.clickOnListingRecord(recordIndex);

		super.fillInputBoxIn("title", newTitle);
		super.fillInputBoxIn("startExecutionPeriod", startExecutionPeriod);
		super.fillInputBoxIn("endExecutionPeriod", endExecutionPeriod);
		super.fillInputBoxIn("share", share);

		final By inputLocatorAddTask = By.name("addTask");

		if (addTask != null && super.exists(inputLocatorAddTask)) {
			super.fillInputBoxIn("addTask", addTask);
		}

		final By inputLocatorDeleteTask = By.name("deleteTask");

		if (deleteTask != null && super.exists(inputLocatorDeleteTask)) {
			super.fillInputBoxIn("deleteTask", deleteTask);
		}

		super.clickOnSubmitButton("Update");

		super.checkColumnHasValue(recordIndex, 0, newTitle);
		super.checkColumnHasValue(recordIndex, 1, startExecutionPeriod);
		super.checkColumnHasValue(recordIndex, 2, endExecutionPeriod);
		super.checkColumnHasValue(recordIndex, 3, share);

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", newTitle);
		super.checkInputBoxHasValue("startExecutionPeriod", startExecutionPeriod);
		super.checkInputBoxHasValue("endExecutionPeriod", endExecutionPeriod);
		super.checkInputBoxHasValue("share", share);
		super.checkInputBoxHasValue("totalWorkload", totalWorkload);

		if (!checkTasks.isEmpty()) {

			By locator;
			locator = By.xpath(String.format("//button[normalize-space()='%s']", "View work plan tasks"));
			super.clickAndWait(locator);

			if (checkTasks.contains(".")) {
				final String tasksIds[] = checkTasks.split("\\.");

				for (int i = 0; i < tasksIds.length; i++) {
					super.clickOnListingRecord(i);
					super.checkInputBoxHasValue("id", tasksIds[i]);
					super.clickOnReturnButton("Return");
				}

			} else {
				super.clickOnListingRecord(0);
				super.checkInputBoxHasValue("id", checkTasks);
			}
		}

		super.signOut();
	}
	
	/*
	 * - La feature que se comprueba en este test es el actualizar planes de trabajo con errores por un manager.
	 * - Los datos utilizados en el fichero .csv son planes de trabajo no válidos
	 * 		- El test comprueba que se violan las siguientes restricciones:
	 * 			- Restricción 1: Un plan de trabajo que contiene tareas privadas no puede ser público.
	 * 			- Restricción 2: La entidad no debe contener spam.
	 * 			- Restricción 3: El final del periodo de ejecución debe ser posterior al de inicio.
	 * 			- Restricción 4: No se puede añadir una tarea que está fuera del periodo de ejecución del plan de trabajo.
	 * 
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/workplan/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void updateNegative(final int recordIndex, final String title, final String startExecutionPeriod, final String endExecutionPeriod, final String share, final String addTask, final String newTitle) {
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Work plans list");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		
		super.clickOnListingRecord(recordIndex);
		
		super.fillInputBoxIn("title", newTitle);
		super.fillInputBoxIn("startExecutionPeriod", startExecutionPeriod);
		super.fillInputBoxIn("endExecutionPeriod", endExecutionPeriod);
		super.fillInputBoxIn("share", share);
		
		final By inputLocatorAddTask = By.name("addTask");

		if (addTask != null && super.exists(inputLocatorAddTask)) {
			super.fillInputBoxIn("addTask", addTask);
		}
		
		super.clickOnSubmitButton("Update");
		
		super.checkErrorsExist();

		super.signOut();
	}

	// Ancillary methods ------------------------------------------------------

}
