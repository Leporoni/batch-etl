//package dofn;
//
//import model.Contract;
//import org.apache.beam.sdk.transforms.DoFn;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class PersistToPostgreSQL extends DoFn<Contract, Void> {
//    @ProcessElement
//    public void processElement(@Element Contract contract, OutputReceiver<Void> out) throws SQLException {
//        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://seu_servidor:5432/contracts_db",
//                "postgres", "123456")) {
//            String sql = "INSERT INTO Contracts (contract_number, name, cpf, email) VALUES (?, ?, ?, ?)";
//            try (PreparedStatement statement = connection.prepareStatement(sql)) {
//                statement.setString(1, contract.getCotractNumber());
//                statement.setString(2, contract.getName());
//                statement.setString(3, contract.getCpf());
//                statement.setString(4, contract.getEmail());
//                statement.executeUpdate();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//}
