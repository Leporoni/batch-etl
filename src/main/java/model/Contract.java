package model;
public class Contract {
    private String contractNumber;
    private String personName;
    private String cpf;
    private String email;

    public Contract(String contractNumber, String personName, String cpf, String email) {
        this.contractNumber = contractNumber;
        this.personName = personName;
        this.cpf = cpf;
        this.email = email;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public String getPersonName() {
        return personName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }
}