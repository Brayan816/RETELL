package app.jabrex.assot;

public class CONSULTA {
    private String nombre,email,contacto,direccion;
    public CONSULTA() {
    }

    public CONSULTA(String nombre, String email, String contacto, String direccion) {
        this.nombre = nombre;
        this.email = email;
        this.contacto = contacto;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
