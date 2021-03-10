package model;

import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.usuario.Usuario;

public class RepositorioUsuarios implements WithGlobalEntityManager, TransactionalOps {


	public static List<Usuario> usuariosLogueados = new ArrayList<Usuario>();

	private static RepositorioUsuarios INSTANCIA;

	public List<Usuario> usuariosLogueados() {
		return this.usuariosLogueados;
	}

	public static RepositorioUsuarios getInstance() {
		if (INSTANCIA == null) INSTANCIA = new RepositorioUsuarios();
		return INSTANCIA;
	}

	public void agregarUsuario(Usuario usuario) {
				
		entityManager().persist(usuario);
	}
	

	public List<Usuario> getUsuariosAPersistir() {
		return usuariosLogueados;
	}

	public List<Usuario> getUsuarios() {
		
			return  entityManager()
				.createQuery("from Usuario", Usuario.class)
				.getResultList();			 
			 
	
	}
	
	
	public Usuario getUsuarioUsernamePassword(String username, String password) {		
		 
		 return entityManager()
				.createQuery("from Usuario c where c.nombre like :nombre and c.contrasenia like :contrasenia ", Usuario.class) //
				.setParameter("nombre", "%" + username + "%") 
				.setParameter("contrasenia", "%" + password + "%")
				.getResultList()
				.get(0);			
	}

	public Boolean validarQueExisteUsuario(String usuario) {
		return getUsuarios().stream().anyMatch(user -> user.getNombre().equals(usuario));
	}

	public Usuario obtenerUsuario(String Usuario) {
		
		return getUsuarios().stream().filter(ciu -> ciu.getNombre().equals(Usuario)).findAny().get();
	
	}

	public Usuario buscar(long id) {
		
		return entityManager().find(Usuario.class, id);
		
		
	}
	
	public void removerUsuarioPorIdUsuario(Long idUsuario) {
		
		Usuario usuarioRemovido = this.usuariosLogueados()
				.stream()
				.filter( usuario -> usuario.getId_usuario()
						.equals(idUsuario) )
				.findFirst()
				.get();
		
		usuariosLogueados.remove( usuarioRemovido  );
	}
	
	


}
