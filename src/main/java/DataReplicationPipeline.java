import connection.DataBasePersist;
import lombok.extern.java.Log;
import model.Contract;
import org.apache.beam.sdk.Pipeline;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log
public class DataReplicationPipeline {
    public static void main(String[] args) throws Exception {
        Logger log = Logger.getLogger(DataReplicationPipeline.class.getName());

        Pipeline pipeline = Pipeline.create();

        log.info("Starting the pipeline");

        try (BufferedReader reader = new BufferedReader(new FileReader("/home/leporoni/IdeaProjects/batch-etl/src/main/resources/input/contracts.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("/home/leporoni/IdeaProjects/batch-etl/src/main/resources/output/contracts_return.txt"))) {

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
                    log.info("Writing contract number: " + contract.getContractNumber() + " to the return file");

                } else {
                    log.warning("Linha com formato inválido: " + linha);
                }
            }

            DataBasePersist databasePersist = new DataBasePersist();
            Connection connection = databasePersist.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM contracts_db.contracts");
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                String contractNumber = resultSet.getString("contractNumber");
                String personName = resultSet.getString("personName");
                String cpf = resultSet.getString("cpf");
                String email = resultSet.getString("email");

                SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
                String currentDate = dateFormat.format(new Date());

                String lineToWrite = String.format("%-8s %-41s %-11s %-41s %-8s", contractNumber, personName, cpf, email, currentDate);
                writer.write(lineToWrite);
                writer.newLine();
            }

            pipeline.run();
            log.info("Pipeline executed");
        } catch (IOException | SQLException e) {
            log.severe("Error during processing: " + e.getMessage());
        }
    }
}
