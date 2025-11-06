package model;

public class UsuarioSesion {
    private static int idUsuario;

    public static void setIdUsuario(int id) {
        idUsuario = id;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }
}
