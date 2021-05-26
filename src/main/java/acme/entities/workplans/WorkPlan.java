
package acme.entities.workplans;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;

import acme.entities.roles.Manager;
import acme.entities.tasks.Task;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WorkPlan extends DomainEntity {

	//Serialisation identifier 

	protected static final long		serialVersionUID	= 1L;

	//Attributes
	
	@NotBlank
	@Length(min = 1, max = 80)
	protected String title;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date					startExecutionPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date					endExecutionPeriod;

	@NotNull
	protected WorkPlanShare			share;

	//Derived attributes

	@Transient
	public Double getTotalWorkload() {
		Integer sumaHoras = 0;
		Integer sumaMinutos = 0;
		Integer horas;
		Integer minutos;
		Double decimal;
		Double res = 0.0;
		
		if(!this.tasks.isEmpty()) {
			for(final Task t : this.tasks) {
				decimal = t.getWorkload()%1;
				decimal = Math.round(decimal*100.0)/100.0;
				minutos = (int) (decimal*100);
				horas = (int) ((t.getWorkload()-decimal)/1.0);
				sumaHoras+=horas;
				sumaMinutos+=minutos;
			}
			
			final Integer minutosFinal = sumaMinutos % 60;
			final Integer horasFinal = sumaHoras + ((sumaMinutos-minutosFinal)/60);
			
			res = (horasFinal*1.0) + (minutosFinal/100.);
		}
		
		return res;
	}
	
	//Relationships
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	protected Collection<@Valid Task>	tasks;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Manager manager;

}
