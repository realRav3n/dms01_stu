import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test
{

    public static void main(String [] args) throws ClassNotFoundException, SQLException
    {
       Class.forName("com.mysql.jdbc.Driver");
       String url = "jdbc:mysql://localhost:3306/q_dms?useUnicode=true&characterEncoding=UTF-8";
       String user = "root";
       String password = "";
       Connection conn = DriverManager.getConnection(url, user, password);
       Statement st = conn.createStatement();
       String sql = "insert into userdetails values(7, 'qwq', 'qwq', 1, '打电动', '33', '带专')";
       st.executeUpdate(sql);
    }
}