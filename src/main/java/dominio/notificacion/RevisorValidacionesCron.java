package dominio.notificacion;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import model.RepositorioEntidades;


public class RevisorValidacionesCron implements Job {


	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		RepositorioEntidades.getInstance().validarEntidades();


	}

}
