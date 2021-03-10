package dominio;

import api.ServicioDeUbicacionMercadoLibreMock;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRequestMercadoLibreAPIconMockito {

    ServicioDeUbicacionMercadoLibreMock mercadoLibreMock;

    @Before
    public void setUp() throws Exception {

        this.mercadoLibreMock = new ServicioDeUbicacionMercadoLibreMock();

    }


    @Test
    public void obtenerTodosLosPaises() throws Exception {
        //Se solicita todos los datos de paises y se verifica que esten Argentina y Colombia.
        ClientResponse response = mercadoLibreMock.requestAPI("classified_locations/countries");
        assertEquals(response.getStatus(), 200);
        String json = response.getEntity(String.class);
        assertTrue(json.contains("Argentina"));
        assertTrue(json.contains("Colombia"));
    }


    @Test
    public void obtenerTodasLasMonedasDePaises() throws Exception {
        //Se solicita todos los datos de monedas y se verifica que esten las monedas de Argentina y Colombia.
        ClientResponse response = mercadoLibreMock.requestAPI("currencies");
        assertEquals(response.getStatus(), 200);
        String json = response.getEntity(String.class);
        assertTrue(json.contains("Peso argentino"));
        assertTrue(json.contains("Peso colombiano"));
    }

}
