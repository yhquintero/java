/**
 * Demostración de los puntos a) a d) del Equipo 7 (SoundFlow).
 * La guía no da las duraciones ni los títulos: se usan datos de ejemplo con 4 canciones.
 */
public class Main {

    public static void main(String[] args) {
        SoundFlow playlist = new SoundFlow();

        // (id, título, artista, duración en segundos)
        playlist.insertar(new Cancion("C01", "Amanecer", "Banda Norte", 210));
        playlist.insertar(new Cancion("C02", "Río", "Los Faros", 185));
        playlist.insertar(new Cancion("C03", "Noche Azul", "Banda Norte", 245));
        playlist.insertar(new Cancion("C04", "Último Tren", "Luna Roja", 190));

        // Punto a)
        System.out.println("a) NavegarAdelanteYAtras():");
        System.out.println(playlist.NavegarAdelanteYAtras());

        // Punto b): mover la última canción (C04) al inicio
        System.out.println("b) MoverCancionAlInicio(\"C04\"): " + playlist.MoverCancionAlInicio("C04"));
        System.out.println("   Tras moverla:");
        System.out.println(playlist.NavegarAdelanteYAtras());
        System.out.println("   Punteros coherentes: " + playlist.esConsistente());

        // Punto c)
        System.out.println("c) DuracionTotalPlaylist(): " + playlist.DuracionTotalPlaylist());

        // Punto d)
        System.out.println("d) EliminarCancionPorArtista(\"Banda Norte\") eliminó: "
                + playlist.EliminarCancionPorArtista("Banda Norte") + " canciones");
        System.out.println("   SoundFlow final: " + playlist + " | coherente: " + playlist.esConsistente());
    }
}
