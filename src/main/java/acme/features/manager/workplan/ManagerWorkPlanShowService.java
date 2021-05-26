/*
 * ManagerWorkPlanShowService.java
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
import acme.entities.workplans.WorkPlan;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class ManagerWorkPlanShowService implements AbstractShowService<Manager, WorkPlan> {

	@Autowired
	protected ManagerWorkPlanRepository repository;

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
		int workPlanId;

		workPlanId = request.getModel().getInteger("id");
		result = this.repository.findWorkPlanById(workPlanId);

		return result;
	}

}
