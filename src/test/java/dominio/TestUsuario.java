package dominio;

import dominio.usuario.TipoUsuario;
import dominio.usuario.Usuario;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestUsuario {


    @Test
    public void tieneContraseniaFuerte()  {

        Usuario gregorio = new Usuario("gregorio", "1M2F3a5r87", TipoUsuario.STANDARD);

        assertEquals(gregorio.tieneEstaContrasenia("1M2F3a5r87"), true);
    }


    @Test
    public void tieneTipoUsuarioAdmin()  {

        Usuario federico = new Usuario("administrador", "Hola5937d", TipoUsuario.ADMIN);

        assertEquals(federico.getTipoUsuario(), TipoUsuario.ADMIN);

    }

    

    @Test
    public void tieneTipoUsuarioStandard()  {

        Usuario federico = new Usuario("federico", "Hola5937d", TipoUsuario.STANDARD);

        assertEquals(federico.getTipoUsuario(), TipoUsuario.STANDARD);

    }

    
    
    

}
