package dominio.notificacion;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class PlanificadorTareas {

	public void activarYEjecutarRevisorValidacionesCron() {

		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.start();

			JobDetail job = newJob(RevisorValidacionesCron.class)
					.withIdentity("job1", "group1")
					.build();

			Trigger trigger = newTrigger()
					.withIdentity("trigger1", "group1")
					.startNow()
					.withSchedule(simpleSchedule()
							.withIntervalInSeconds(5)
							.repeatForever())
					.build();

			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException ex) {
			ex.printStackTrace();
		}

	}

}
