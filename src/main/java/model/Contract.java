package model;
public class Contract {

    private String cotractNumber;
    private String name;
    private String cpf;
    private String email;

    // Construtor
    public Contract(String cotractNumber, String name, String cpf, String email) {
        this.cotractNumber = cotractNumber;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

    // Getters e setters
    public String getCotractNumber() {
        return cotractNumber;
    }

    public void setCotractNumber(String cotractNumber) {
        this.cotractNumber = cotractNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}