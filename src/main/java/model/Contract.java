package model;
public class Contract {

    private String cotractNumber;
    private String personName;
    private String cpf;
    private String email;

    public Contract(String cotractNumber, String personName, String cpf, String email) {
        this.cotractNumber = cotractNumber;
        this.personName = personName;
        this.cpf = cpf;
        this.email = email;
    }

    public String getCotractNumber() {
        return cotractNumber;
    }

    public void setCotractNumber(String cotractNumber) {
        this.cotractNumber = cotractNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
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