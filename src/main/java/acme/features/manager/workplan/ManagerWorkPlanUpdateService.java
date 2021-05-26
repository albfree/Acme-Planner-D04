/*
 * ManagerWorkPlanUpdateService.java
 *
 * Copyright (C) 2012-2021 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.workplan;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.entities.tasks.TaskShare;
import acme.entities.workplans.WorkPlan;
import acme.entities.workplans.WorkPlanShare;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;
import acme.utils.SpamChecker;

@Service
public class ManagerWorkPlanUpdateService implements AbstractUpdateService<Manager, WorkPlan> {

	@Autowired
	protected ManagerWorkPlanRepository repository;
	
	@Autowired
	private SpamChecker spamChecker;
	
	@Override
	public boolean authorise(final Request<WorkPlan> request) {
		assert request != null;
		
		boolean result;
		int workPlanId;
		WorkPlan workPlan;
		int managerId;

		workPlanId = request.getModel().getInteger("id");
		workPlan = this.repository.findWorkPlanById(workPlanId);
		managerId = request.getPrincipal().getActiveRoleId();
		
		result = workPlan.getManager().getId() == managerId;
		
		return result;
	}
	
	@Override
	public void bind(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
		
		request.getModel().setAttribute("hasTasks", !entity.getTasks().isEmpty());
		
		if (!entity.getTasks().isEmpty()) {
			
			/*
			 * Sonarlint marca como inadecuado lo siguiente por no
			 * usar isPresent para asegurar que el valor está, pero
			 * en este caso no es necesario porque se comprueba previamente
			 * que la lista está llena de tareas.
			 */
			
			final Date minDate = entity.getTasks().stream().map(Task::getStartExecutionPeriod).min(Date::compareTo).get();
			final Date maxDate = entity.getTasks().stream().map(Task::getEndExecutionPeriod).max(Date::compareTo).get();
			
			final Calendar minCalendar = Calendar.getInstance();
			minCalendar.setTime(minDate);
			minCalendar.add(Calendar.DAY_OF_MONTH, -1);
			minCalendar.set(Calendar.HOUR_OF_DAY, 8);
			
			final int minMonth = minCalendar.get(Calendar.MONTH) + 1;
			
			String stringMinMonth = "";
			if (minMonth < 10) {
			    stringMinMonth = "0" + minMonth;
			} else {
			    stringMinMonth = String.valueOf(minMonth);
			}
			
			final Calendar maxCalendar = Calendar.getInstance();
			maxCalendar.setTime(maxDate);
			maxCalendar.add(Calendar.DAY_OF_MONTH, 1);
			maxCalendar.set(Calendar.HOUR_OF_DAY, 17);
			
			final int maxMonth = maxCalendar.get(Calendar.MONTH) + 1;
			
			String stringMaxMonth = "";
			if (maxMonth < 10) {
			    stringMaxMonth = "0" + maxMonth;
			} else {
			    stringMaxMonth = String.valueOf(maxMonth);
			}
			
			if (request.getLocale().equals(Locale.ENGLISH)) {
				request.getModel().setAttribute("suggestedStartDate", "Suggested start date: " 
					+ minCalendar.get(Calendar.YEAR) + "/"
					+ stringMinMonth + "/"
					+ minCalendar.get(Calendar.DAY_OF_MONTH) + " at "
					+ minCalendar.get(Calendar.HOUR_OF_DAY));
				
				request.getModel().setAttribute("suggestedEndDate", "Suggested finish date: " 
					+ maxCalendar.get(Calendar.YEAR) + "/"
					+ stringMaxMonth + "/"
					+ maxCalendar.get(Calendar.DAY_OF_MONTH) + " at "
					+ maxCalendar.get(Calendar.HOUR_OF_DAY));
			} else {
				request.getModel().setAttribute("suggestedStartDate", "Fecha de inicio recomendada: " 
					+ minCalendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ stringMinMonth + "/"
					+ minCalendar.get(Calendar.YEAR) + " a las "
					+ minCalendar.get(Calendar.HOUR_OF_DAY));
				
				request.getModel().setAttribute("suggestedEndDate", "Fecha de fin recomendada: " 
					+ maxCalendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ stringMaxMonth + "/"
					+ maxCalendar.get(Calendar.YEAR) + " a las "
					+ maxCalendar.get(Calendar.HOUR_OF_DAY));
			}
		}
		
		// Preparamos las variables para los desplegables de tareas

		final List<Task> tasksToAdd = (List<Task>) this.repository.findAllTasksByManager(request.getPrincipal().getActiveRoleId());
		final List<Task> tasksToDelete = (List<Task>) entity.getTasks();

		if (!tasksToDelete.isEmpty()) {
			request.getModel().setAttribute("tasksToDelete", tasksToDelete);

			if (!tasksToAdd.isEmpty()) {
				tasksToAdd.removeAll(tasksToDelete);
			}
		}

		request.getModel().setAttribute("hasAvailableTasks", !tasksToAdd.isEmpty());

		if (!tasksToAdd.isEmpty()) {
			request.getModel().setAttribute("tasksToAdd", tasksToAdd);
		}
	}
	
	@Override
	public void unbind(final Request<WorkPlan> request, final WorkPlan entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startExecutionPeriod", "endExecutionPeriod", "share", "totalWorkload");
		
		model.setAttribute("wpID", entity.getId());
		
		model.setAttribute("hasTasks", !entity.getTasks().isEmpty());
		
		if (!entity.getTasks().isEmpty()) {
			
			/*
			 * Sonarlint marca como inadecuado lo siguiente por no
			 * usar isPresent para asegurar que el valor está, pero
			 * en este caso no es necesario porque se comprueba previamente
			 * que la lista está llena de tareas.
			 */
			
			final Date minDate = entity.getTasks().stream().map(Task::getStartExecutionPeriod).min(Date::compareTo).get();
			final Date maxDate = entity.getTasks().stream().map(Task::getEndExecutionPeriod).max(Date::compareTo).get();
			
			final Calendar minCalendar = Calendar.getInstance();
			minCalendar.setTime(minDate);
			minCalendar.add(Calendar.DAY_OF_MONTH, -1);
			minCalendar.set(Calendar.HOUR_OF_DAY, 8);
			
			final int minMonth = minCalendar.get(Calendar.MONTH) + 1;
			
			String stringMinMonth = "";
			if (minMonth < 10) {
			    stringMinMonth = "0" + minMonth;
			} else {
			    stringMinMonth = String.valueOf(minMonth);
			}
			
			final Calendar maxCalendar = Calendar.getInstance();
			maxCalendar.setTime(maxDate);
			maxCalendar.add(Calendar.DAY_OF_MONTH, 1);
			maxCalendar.set(Calendar.HOUR_OF_DAY, 17);
			
			final int maxMonth = maxCalendar.get(Calendar.MONTH) + 1;
			
			String stringMaxMonth = "";
			if (maxMonth < 10) {
			    stringMaxMonth = "0" + maxMonth;
			} else {
			    stringMaxMonth = String.valueOf(maxMonth);
			}
			
			if (request.getLocale().equals(Locale.ENGLISH)) {
				model.setAttribute("suggestedStartDate", "Suggested start date: " 
					+ minCalendar.get(Calendar.YEAR) + "/"
					+ stringMinMonth + "/"
					+ minCalendar.get(Calendar.DAY_OF_MONTH) + " at "
					+ minCalendar.get(Calendar.HOUR_OF_DAY));
				
				model.setAttribute("suggestedEndDate", "Suggested finish date: " 
					+ maxCalendar.get(Calendar.YEAR) + "/"
					+ stringMaxMonth + "/"
					+ maxCalendar.get(Calendar.DAY_OF_MONTH) + " at "
					+ maxCalendar.get(Calendar.HOUR_OF_DAY));
			} else {
				model.setAttribute("suggestedStartDate", "Fecha de inicio recomendada: " 
					+ minCalendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ stringMinMonth + "/"
					+ minCalendar.get(Calendar.YEAR) + " a las "
					+ minCalendar.get(Calendar.HOUR_OF_DAY));
				
				model.setAttribute("suggestedEndDate", "Fecha de fin recomendada: " 
					+ maxCalendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ stringMaxMonth + "/"
					+ maxCalendar.get(Calendar.YEAR) + " a las "
					+ maxCalendar.get(Calendar.HOUR_OF_DAY));
			}
		}
		
		// Preparamos las variables para los desplegables de tareas

		final List<Task> tasksToAdd = (List<Task>) this.repository.findAllTasksByManager(request.getPrincipal().getActiveRoleId());
		final List<Task> tasksToDelete = (List<Task>) entity.getTasks();

		if (!tasksToDelete.isEmpty()) {
			model.setAttribute("tasksToDelete", tasksToDelete);

			if (!tasksToAdd.isEmpty()) {
				tasksToAdd.removeAll(tasksToDelete);
			}
		}

		model.setAttribute("hasAvailableTasks", !tasksToAdd.isEmpty());

		if (!tasksToAdd.isEmpty()) {
			model.setAttribute("tasksToAdd", tasksToAdd);
		}
	}
	
	@Override
	public WorkPlan findOne(final Request<WorkPlan> request) {
		assert request != null;

		WorkPlan result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findWorkPlanById(id);

		return result;
	}
	
	@Override
	public void validate(final Request<WorkPlan> request, final WorkPlan entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (!errors.hasErrors("title")) {
			final String title = entity.getTitle();
			errors.state(request, !this.spamChecker.isSpamText(title), "title", "manager.work-plan.form.error.contains-spam");
		}
		
		if (!errors.hasErrors("share") && entity.getShare().equals(WorkPlanShare.PUBLIC)) {
			errors.state(request, entity.getTasks().stream().noneMatch(task -> task.getShare().equals(TaskShare.PRIVATE)),
				"share", "manager.work-plan.form.error.has-private-tasks");
		}
		
		if (!errors.hasErrors("startExecutionPeriod") && !errors.hasErrors("endExecutionPeriod")) {
			errors.state(request, entity.getEndExecutionPeriod().after(entity.getStartExecutionPeriod()), 
				"endExecutionPeriod", "manager.work-plan.form.error.period-invalid");
		}
		
		if (!entity.getTasks().isEmpty()) {
			errors.state(request, entity.getTasks().stream().noneMatch(task -> task.getStartExecutionPeriod().before(entity.getStartExecutionPeriod())),
				"startExecutionPeriod", "manager.work-plan.form.error.period-tasks-invalid");
			
			errors.state(request, entity.getTasks().stream().noneMatch(task -> task.getEndExecutionPeriod().after(entity.getEndExecutionPeriod())),
				"endExecutionPeriod", "manager.work-plan.form.error.period-tasks-invalid");
		}
		
		if (request.getModel().hasAttribute("addTask")) {
			final int idTaskToAdd = request.getModel().getInteger("addTask");

			if (idTaskToAdd != -1) {

				final Task taskToAdd = this.repository.findAllTasksByManager(request.getPrincipal().getActiveRoleId()).stream().filter(
					task -> task.getId() == idTaskToAdd).findAny().orElse(null);

				if (taskToAdd != null) {
					
					if (entity.getTasks().contains(taskToAdd)) {
						errors.state(request, false, "addTask", "manager.work-plan.form.error.task.contains.invalid");
					} else if (taskToAdd.getShare().equals(TaskShare.PRIVATE) && entity.getShare().equals(WorkPlanShare.PUBLIC)) {
						errors.state(request, false, "addTask", "manager.work-plan.form.error.task.private.invalid");
					} else if (taskToAdd.getStartExecutionPeriod().before(entity.getStartExecutionPeriod()) 
						|| taskToAdd.getEndExecutionPeriod().after(entity.getEndExecutionPeriod())) {
						errors.state(request, false, "addTask", "manager.work-plan.form.error.task.period.invalid");
					} else {
						entity.getTasks().add(taskToAdd);
					}
				}
			}
		}
		
		if (request.getModel().hasAttribute("deleteTask")) {
			final int idTaskToDelete = request.getModel().getInteger("deleteTask");

			if (idTaskToDelete != -1) {

				final Task taskToDelete = entity.getTasks().stream().filter(task -> task.getId() == idTaskToDelete).findAny().orElse(null);

				if (taskToDelete != null) {
					entity.getTasks().remove(taskToDelete);
				}
			}
		}
	}
	
	@Override
	public void update(final Request<WorkPlan> request, final WorkPlan entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
}
