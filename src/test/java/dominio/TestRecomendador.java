package dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dominio.recomendaciones.Recomendador;

import java.io.IOException;

public class TestRecomendador {


    @Test
    public void rechazaRecomendacionRecomendadorCumpleNingunaRecomendaciones() throws IOException {

        Recomendador recomendador = new Recomendador();


        assertEquals(recomendador.esRecomendable("aaa"), false);

    }


    @Test
    public void aceptaRecomendacionRecomendadorCumpleTodasRecomendaciones() throws IOException {

        Recomendador recomendador = new Recomendador();


        assertEquals(recomendador.esRecomendable("1d8em9v7w3oM1Y"), true);

    }

}
