package empresa.model;

/**
 * Clase base que representa a una Persona genérica.
 * Empleado hereda de esta clase.
 *
 * @author Adrián Acevedo Reinoso
 */
public class Persona {
    /** Nombre de la persona. */
    public String nombre;

    /** DNI de la persona (9 caracteres). */
    public String dni;

    /** Sexo de la persona: 'M' o 'F'. */
    public char sexo;

    /**
     * Constructor completo: nombre, DNI y sexo.
     *
     * @param nombre nombre de la persona
     * @param dni    DNI de la persona
     * @param sexo   sexo de la persona ('M' o 'F')
     */
    public Persona(String nombre, String dni, char sexo) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
    }

    /**
     * Constructor reducido: solo nombre y sexo (sin DNI).
     *
     * @param nombre nombre de la persona
     * @param sexo   sexo de la persona ('M' o 'F')
     */
    public Persona(String nombre, char sexo) {
        this.nombre = nombre;
        this.sexo = sexo;
    }

    /**
     * Devuelve el nombre de la persona.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el DNI de la persona.
     *
     * @return el DNI
     */
    public String getDni() {
        return dni;
    }

    /**
     * Devuelve el sexo de la persona.
     *
     * @return 'M' o 'F'
     */
    public char getSexo() {
        return sexo;
    }

    /**
     * Permite cambiar el DNI de la persona una vez creada.
     *
     * @param nuevoDni nuevo DNI que se asigna a la persona
     */
    public void setDni(String nuevoDni) {
        dni = nuevoDni;
    }

    /**
     * Muestra por pantalla los datos básicos de la persona (nombre y DNI).
     */
    public void imprime() {
        System.out.println("Nombre: " + nombre +
                "\nDNI: " + dni);
    }
}
