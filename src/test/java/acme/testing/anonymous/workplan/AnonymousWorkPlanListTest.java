/*
 * AnonymousWorkPlanListTest.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.anonymous.workplan;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import acme.testing.AcmePlannerTest;

public class AnonymousWorkPlanListTest extends AcmePlannerTest {

	// Lifecycle management ---------------------------------------------------
	
	// Test cases -------------------------------------------------------------
	
	/*
	 * list(final int recordIndex, final String title, final String startExecutionPeriod, final String endExecutionPeriod, final String share, final String totalWorkload, final String checkTasks)
	 * 
	 * 	- Caso positivo de las acciones de listar y mostrar sobre la entidad WorkPlan por parte del rol Anonymous, así como de la acción de listar las Tasks asociadas
	 * 	  a un WorkPlan por parte del mismo rol Anonymous.
	 * 	- Para las acciones correspondientes a la entidad WorkPlan, el test espera resultados positivos comprobando que los objetos aparecen en el listado y pueden
	 * 	  mostrarse con todos sus datos. Así mismo, para la acción de listar las Tasks, el test espera resultados positivos comprobando que los identificadores de
	 * 	  las Tasks listadas se corresponden a los identificadores asociados al WorkPlan correspondiente.
	 * 	- Los datos utilizados en el fichero .csv son WorkPlans válidos, a los que se asignan los identificadores de aquellas Tasks válidas asociadas a cada WorkPlan.
	 * 
	 * */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/anonymous/workplan/list.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)	
	public void list(final int recordIndex, final String title, final String startExecutionPeriod, final String endExecutionPeriod, final String share, final String totalWorkload, final String checkTasks) {
		
		super.clickOnMenu("Anonymous", "Work plans list");		
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, startExecutionPeriod);
		super.checkColumnHasValue(recordIndex, 2, endExecutionPeriod);
		super.checkColumnHasValue(recordIndex, 3, totalWorkload);
		
		super.clickOnListingRecord(recordIndex);
		
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("startExecutionPeriod", startExecutionPeriod);
		super.checkInputBoxHasValue("endExecutionPeriod", endExecutionPeriod);	
		super.checkInputBoxHasValue("totalWorkload", totalWorkload);
		super.checkInputBoxHasValue("share", share);
		
		if(!checkTasks.isEmpty()) {
			
			By locator;
			locator = By.xpath(String.format("//button[normalize-space()='%s']", "View tasks"));
			super.clickAndWait(locator);
			
			if(checkTasks.contains(".")) {
				final String taskIds[] = checkTasks.split("\\.");
				
				for (int i = 0; i < taskIds.length; i++) {
					super.clickOnListingRecord(i);
					super.checkInputBoxHasValue("id", taskIds[i]);
					super.clickOnReturnButton("Return");
				}
				
			} else {
				super.clickOnListingRecord(0);
				super.checkInputBoxHasValue("id", checkTasks);
			}
		}
	}
	
	// Ancillary methods ------------------------------------------------------

}
