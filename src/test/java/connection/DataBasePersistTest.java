package connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class DataBasePersistTest {

    @Mock
    private Connection connection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConnection() throws SQLException {
        DataBasePersist dataBasePersist = mock(DataBasePersist.class);
        when(dataBasePersist.getConnection()).thenReturn(connection);

        Connection actualConnection = dataBasePersist.getConnection();

        assertNotNull(actualConnection);
        verify(dataBasePersist, times(1)).getConnection();
    }

    @Test
    void testCloseConnection() throws SQLException {
        DataBasePersist dataBasePersist = mock(DataBasePersist.class);
        doNothing().when(dataBasePersist).closeConnection();

        dataBasePersist.closeConnection();

        verify(dataBasePersist, times(1)).closeConnection();
    }
}
