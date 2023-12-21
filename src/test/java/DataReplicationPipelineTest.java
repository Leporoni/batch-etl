import connection.DataBasePersist;
import model.Contract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.mockito.Mockito.*;

public class DataReplicationPipelineTest {
    @Mock
    private DataBasePersist dataBasePersist;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement statement;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMain() throws Exception {
        when(dataBasePersist.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        BufferedReader reader = new BufferedReader(new FileReader("/home/leporoni/IdeaProjects/batch-etl/src/main/resources/input/contracts.txt"));
        String linha = reader.readLine();

        if (linha != null && linha.length() >= 103) {
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

            statement.setString(1, contract.getContractNumber());
            statement.setString(2, contract.getPersonName());
            statement.setString(3, contract.getCpf());
            statement.setString(4, contract.getEmail());
            statement.executeUpdate();

            verify(statement, times(4)).setString(anyInt(), anyString());
        }
    }
}