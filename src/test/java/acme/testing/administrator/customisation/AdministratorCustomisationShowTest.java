/*
 * EmployerApplicationUpdateTest.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.administrator.customisation;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.AcmePlannerTest;

public class AdministratorCustomisationShowTest extends AcmePlannerTest {

	// Test cases -------------------------------------------------------------
	
	/*
     * showPositive(final String spamWords, final String treshold ) :
     *
     *         - Caso positivo de la acción Show sobre la entidad Customisation por parte del rol Administrador.
     *         - El test espera resultados positivos comprobando que las palabras spam se muestran correctamente.
     *         - Los datos utilizados en el fichero .csv son las palabras y el umbral determinados por defecto 
     *         en la aplicación.
     */
	
	@ParameterizedTest
	@CsvFileSource(resources = "/administrator/customisation/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)	
	public void showPositive(final String spamWords, final String treshold ) {		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Administrator", "Customisation parameters");		
			
		super.checkInputBoxHasValue("spamWords", spamWords);

		super.checkInputBoxHasValue("threshold", treshold);

		super.signOut();
	}	

}
