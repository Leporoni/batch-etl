import connection.DataBasePersist;
import lombok.extern.java.Log;
import model.Contract;
import org.apache.beam.sdk.Pipeline;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Logger;

@Log
public class DataReplicationPipeline {
    public static void main(String[] args) throws Exception {
        Logger log = Logger.getLogger(DataReplicationPipeline.class.getName());

        Pipeline pipeline = Pipeline.create();

        log.info("Starting the pipeline");

        BufferedReader reader = new BufferedReader(new FileReader("/home/leporoni/IdeaProjects/batch-etl/src/main/resources/input/contracts.txt"));

        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.length() >= 103) {
                String contractNumber = linha.substring(0, 8).trim();
                String personName = linha.substring(9, 50).trim();
                String cpf = linha.substring(50, 61).trim();
                String email = linha.substring(62, 103).trim();

                Contract contract = new Contract(
                        contractNumber,
                        personName,
                        cpf,
                        email
                );

                DataBasePersist databasePersist = new DataBasePersist();
                Connection connection = databasePersist.getConnection();

                PreparedStatement statement = connection.prepareStatement("INSERT INTO contracts_db.contracts (contractNumber, personName, cpf, email) VALUES (?, ?, ?, ?)");
                statement.setString(1, contract.getContractNumber());
                statement.setString(2, contract.getPersonName());
                statement.setString(3, contract.getCpf());
                statement.setString(4, contract.getEmail());
                statement.executeUpdate();

                databasePersist.closeConnection();
                log.info("Inserted contract number: " + contract.getContractNumber() + " into the database");
            } else {
                log.warning("Linha com formato inválido: " + linha);
            }
        }

        pipeline.run();
        log.info("Pipeline executed");
    }
}
