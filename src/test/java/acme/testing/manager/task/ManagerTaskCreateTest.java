package acme.testing.manager.task;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class ManagerTaskCreateTest extends AcmePlannerTest {
	
	/*
     * createPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String description, final String visibility, final String link):
     *
     *         - Caso positivo de la acción create sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados positivos comprobando que se ha creado la entidad y es posible visualizar sus datos.
     *         - Los datos utilizados en el fichero .csv son tareas válidas
     *         
     */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositive(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String description, final String visibility, final String link) {
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Create task");
		
		super.checkNotPanicExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startExecutionPeriod", startPeriod);
		super.fillInputBoxIn("endExecutionPeriod", endPeriod);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("share", visibility);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmitButton("Create");

		super.clickOnMenu("Manager", "Tasks list");
		
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
     * createNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String description, final String visibility, final String link):
     *
     *         - Caso negativo de la acción create sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados negativos comprobando que existen errores al intentar crear una entidad.
     *         - Los datos utilizados en el fichero .csv son tareas inválidas
     *             - El test comprueba que se violan las siguientes restricciones:
     *             - Restricción 1: La entidad no debe contener spam. Ejemplo: Title sex
     *             - Restricción 2: La carga de trabajo debe poder llevarse a cabo durante el periodo total. Ejemplo: periodo 2021/01/01-2021/01/02, carga de trabajo 100.00
     *             - Restricción 3: La carga de trabajo debe tener como máximo 60 minutos decimales. Ejemplo: 10.90
     *             - Restricción 4: El inicio y final del periodo de trabajo deben estar en el futuro. Ejemplo: 1970/10/10 10:00
     *             - Restricción 5: El final del periodo de trabajo debe ser posterior al inicio. Ejemplo: inicio 2021/10/10 10:00, fin 2021/01/01 01:00
     *             
     */
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/task/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegative(final int recordIndex, final String title, final String startPeriod, final String endPeriod, final String workload, final String description, final String visibility, final String link) {
		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Create task");
		
		super.checkNotPanicExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("startExecutionPeriod", startPeriod);
		super.fillInputBoxIn("endExecutionPeriod", endPeriod);
		super.fillInputBoxIn("workload", workload);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("share", visibility);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmitButton("Create");

		super.checkErrorsExist();

		super.signOut();
	}
	
	/*
     * authorizeNegative():
     *
     *         - Caso negativo de la acción create sobre la entidad Task por parte del rol Manager
     *         - El test espera resultados negativos comprobando que un usuario no autorizado intente acceder a la ruta especificada.
     *         
     */
	@Test
	@Order(30)
	public void authorizeNegative() {
		super.signIn("administrator", "administrator");
		
		super.navigate("/manager/task/create", null);
		
		super.checkPanicExists();
		
		super.signOut();
	}
}
