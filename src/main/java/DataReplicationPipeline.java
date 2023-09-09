import connection.DataBasePersist;
import lombok.extern.slf4j.Slf4j;
import model.Contract;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

@Slf4j
public class DataReplicationPipeline {
    public static void main(String[] args) throws Exception {

        Logger log = Logger.getLogger(DataReplicationPipeline.class.getName());

        // Cria o pipeline
        Pipeline pipeline = Pipeline.create();

        // Lê as linhas do arquivo de texto
        BufferedReader reader = new BufferedReader(new FileReader("/home/leporoni/IdeaProjects/batch-etl/src/main/resources/input/contracts.txt"));

        String linha;
        while ((linha = reader.readLine()) != null) {
            // Divide a linha em quatro campos
            String[] campos = linha.split(" ");

            // Cria um objeto Contract
            Contract contract = new Contract(campos[0], campos[1], campos[2], campos[3]);

            // Obtém a conexão com o banco de dados
            DataBasePersist databasePersist = new DataBasePersist();
            Connection connection = databasePersist.getConnection();

            // Insere o registro no banco de dados
            PreparedStatement statement = connection.prepareStatement("INSERT INTO contracts (contractNumber, name, cpf, email) VALUES (?, ?, ?, ?)");
            statement.setString(1, contract.getCotractNumber());
            statement.setString(2, contract.getName());
            statement.setString(3, contract.getCpf());
            statement.setString(4, contract.getEmail());
            statement.executeUpdate();

            // Fecha a conexão com o banco de dados
            databasePersist.closeConnection();
        }

        // Executa o pipeline
        pipeline.run();
    }
}
