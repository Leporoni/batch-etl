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

        // Cria o pipeline
        Pipeline pipeline = Pipeline.create();

        log.info("Starting the pipeline");
        // Lê as linhas do arquivo de texto
        BufferedReader reader = new BufferedReader(new FileReader("/home/leporoni/IdeaProjects/batch-etl/src/main/resources/input/contracts.txt"));

        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.length() >= 103) { // Verifica se a linha tem o tamanho mínimo necessário
                // Extrai os campos usando substring
                String contractNumber = linha.substring(0, 8).trim();
                String personName = linha.substring(9, 50).trim();
                String cpf = linha.substring(50, 61).trim();
                String email = linha.substring(62, 103).trim();

                // Cria um objeto Contract
                Contract contract = new Contract(
                        contractNumber,
                        personName,
                        cpf,
                        email
                );

                // Obtém a conexão com o banco de dados
                DataBasePersist databasePersist = new DataBasePersist();
                Connection connection = databasePersist.getConnection();

                // Insere o registro no banco de dados
                PreparedStatement statement = connection.prepareStatement("INSERT INTO contracts (contractNumber, personName, cpf, email) VALUES (?, ?, ?, ?)");
                statement.setString(1, contract.getCotractNumber());
                statement.setString(2, contract.getPersonName());
                statement.setString(3, contract.getCpf());
                statement.setString(4, contract.getEmail());
                statement.executeUpdate();

                // Fecha a conexão com o banco de dados
                databasePersist.closeConnection();
                log.info("Inserted " + contract);
            } else {
                log.warning("Linha com formato inválido: " + linha);
            }
        }

        // Executa o pipeline
        pipeline.run();
        log.info("Pipeline executed");
    }
}
