package acme.features.manager.task;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.workplans.WorkPlan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class ManagerTaskByWorkPlanListService implements AbstractListService<Manager, Task> {
	
	@Autowired
	protected ManagerTaskRepository repository;

	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		boolean result;
		int workplanId;
		WorkPlan workplan;
		int managerId;

		workplanId = request.getModel().getInteger("id");
		workplan = this.repository.findWorkPlanById(workplanId);
		managerId = request.getPrincipal().getActiveRoleId();
		
		result = workplan.getManager().getId() == managerId;

		return result;
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startExecutionPeriod", "endExecutionPeriod", "workload");
	}

	@Override
	public Collection<Task> findMany(final Request<Task> request) {
		assert request != null;

		Collection<Task> result;
		final int wpID = request.getModel().getInteger("id");

		result = this.repository.findTasksByWorkPlanId(wpID);

		return result;
	}

}
