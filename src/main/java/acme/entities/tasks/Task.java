package acme.entities.tasks;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.roles.Manager;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task extends DomainEntity {
	
	//Serialisation identifier 

	protected static final long serialVersionUID = 1L;

	//Attributes
	
	@NotBlank
	@Length(min = 1, max = 80)
	protected String title;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date startExecutionPeriod;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date endExecutionPeriod;
	
	@NotNull
	@Min(0)
	@Digits(integer = 7, fraction = 2)
	protected Double  workload;
	
	@NotBlank
	@Length(min = 1, max = 255)
	protected String description;
	
	@NotNull
	protected TaskShare share;
	
	@URL
	@Length(max = 255)
	protected String link;
	
	//Derived attributes
	
	@Transient
	public Double maxWorkload() {
		final long diffMillies = Math.abs(this.endExecutionPeriod.getTime()-this.startExecutionPeriod.getTime());
		final long diffMinutes = TimeUnit.MINUTES.convert(diffMillies, TimeUnit.MILLISECONDS);
		final long minutes = diffMinutes%60;
		final long hours = (diffMinutes-minutes)/60;
		return (hours*1.0)+(minutes*0.01);
	}

	//Relationships
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Manager manager;
	
}
