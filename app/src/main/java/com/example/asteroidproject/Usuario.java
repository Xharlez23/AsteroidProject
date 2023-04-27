package com.example.asteroidproject;

public class Usuario {
    int id,identificador;
    String email,firt_name,last_name,password;

    public Usuario() {
    }

    public Usuario(int identificador, String email, String firt_name, String last_name, String password) {
        this.identificador = identificador;
        this.email = email;
        this.firt_name = firt_name;
        this.last_name = last_name;
        this.password = password;
    }

    // comprobar si los datos estan nulos
    public boolean isNull(){
        if( email.equals("") && firt_name.equals("") &&
                last_name.equals("") && password.equals("")){
            return false;
        } else{
            return true;
        }
    }
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", identificador=" + identificador +
                ", email='" + email + '\'' +
                ", firt_name='" + firt_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirt_name() {
        return firt_name;
    }

    public void setFirt_name(String firt_name) {
        this.firt_name = firt_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
