
package acme.testing.administrator.dashboard;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;

import com.google.common.collect.Lists;

import acme.testing.AcmePlannerTest;

public class AdministratorDashboardShowTest extends AcmePlannerTest {
	
	/*
	 * showPositiveTasksTable(final String expectedValues):
	 * 	
	 * 		- Caso positivo de la acción Show de la entidad Dashboard sobre la tabla de Tareas por parte del administrador.
	 * 		- Este test espera resultados positivos comprobando caso a caso que los cálculos realizados por la aplicación
	 * sean correctos.
	 * 		- Los datos utilizados en el fichero .csv son el resultado de calcular de manera externa el valor de las métricas
	 * correspondientes con los datos almacenados en el archivo populate-sample. 
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/dashboard/show-positive-taskTable.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void showPositiveTasksTable(final String expectedValues) {

		final List<String> values = Lists.newArrayList(expectedValues.split("-"));

		By locator;

		super.signIn("administrator", "administrator");

		super.clickOnMenu("Administrator", "Dashboard");

		for (int i = 1; i < values.size(); i++) {

			locator = By.xpath("//table[1]/tbody/tr[" + i + "]/td");

			final String rowValue = super.locateOne(locator).getText();

			Assertions.assertEquals(rowValue, values.get(i-1));

		}

		super.signOut();
	}

	/*
	 * showPositiveTasksTable(final String expectedValues):
	 * 	
	 * 		- Caso positivo de la acción Show de la entidad Dashboard sobre la tabla de Planes de Tareas 
	 * por parte del administrador.
	 * 		- Este test espera resultados positivos comprobando caso a caso que los cálculos realizados por la aplicación
	 * sean correctos.
	 * 		- Los datos utilizados en el fichero .csv son el resultado de calcular de manera externa el valor de las métricas 
	 * correspondientes con los datos almacenados en el archivo populate-sample. 
	 */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/dashboard/show-positive-workplansTable.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void showPositiveWorkplansTable(final String expectedValues) {

		final List<String> values = Lists.newArrayList(expectedValues.split("-"));

		By locator;

		super.signIn("administrator", "administrator");

		super.clickOnMenu("Administrator", "Dashboard");

		for (int i = 1; i < values.size(); i++) {

			locator = By.xpath("//table[2]/tbody/tr[" + i + "]/td");

			final String rowValue = super.locateOne(locator).getText();

			Assertions.assertEquals(rowValue, values.get(i-1));

		}

		super.signOut();
	}

}
