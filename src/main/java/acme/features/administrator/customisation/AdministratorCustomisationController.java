package acme.features.administrator.customisation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.customisations.Customisation;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/customisation")
public class AdministratorCustomisationController extends AbstractController<Administrator, Customisation> {

	//Internal state
	
	@Autowired
	private AdministratorCustomisationShowService	showService;

	@Autowired
	private AdministratorCustomisationUpdateService	updateService;

	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() { 
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}
	
}
