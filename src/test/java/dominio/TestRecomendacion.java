package dominio;

import dominio.*;
import dominio.recomendaciones.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestRecomendacion {


    @Test
    public void rechazaRecomendacionNoTieneOchoCaracteresMinimo() {

        Recomendacion recomendacion = new RecomendacionMinimo8Caracteres();

        assertEquals(recomendacion.esRecomendable("adeaf"), false);

    }


    @Test
    public void aceptaRecomendacionTieneOchoCaracteresMinimo() {

        Recomendacion recomendacion = new RecomendacionMinimo8Caracteres();

        assertEquals(recomendacion.esRecomendable("airmortvdeaf"), true);

    }


    @Test
    public void rechazaRecomendacionTieneDosCaracteresConsecutivosRepetidos() {

        Recomendacion recomendacion = new RecomendacionSinCaracteresConsecutivosIguales();

        assertEquals(recomendacion.esRecomendable("aabbccddddeee"), false);

    }


    @Test
    public void aceptaRecomendacionNoTieneDosCaracteresConsecutivosRepetidos() {

        Recomendacion recomendacion = new RecomendacionSinCaracteresConsecutivosIguales();

        assertEquals(recomendacion.esRecomendable("airmortvdaf"), true);

    }


    @Test
    public void rechazaRecomendacionTieneDosCaracteresAscendentesConsecutivos() {

        Recomendacion recomendacion = new RecomendacionSinCaracteresAscendentesConsecutivos();

        assertEquals(recomendacion.esRecomendable("abcdefghijk"), false);

    }


    @Test
    public void aceptaRecomendacionNoTieneDosCaracteresAscendentesConsecutivos() {

        Recomendacion recomendacion = new RecomendacionSinCaracteresAscendentesConsecutivos();

        assertEquals(recomendacion.esRecomendable("a1b2c5h5j8e9c"), true);

    }


}