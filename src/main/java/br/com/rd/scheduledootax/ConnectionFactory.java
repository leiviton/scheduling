package br.com.rd.scheduledootax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public Connection getConnection(int filial, String login, String senha) throws SQLException {

		try {

			Class.forName("oracle.jdbc.OracleDriver");

		} catch (ClassNotFoundException e) {

			throw new SQLException(e);

		}

		String conexao = null;

		switch (filial) {
		case 900:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = DBEMBPD-SCAN)(PORT = 1521)))(CONNECT_DATA = (SERVICE_NAME = svembpd.raiadrogasil.com.br)))";
			break;
		case 903:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = DBBMAPD-SCAN)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = svbmapd.raiadrogasil.com.br)))";
			break;
		case 905:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = DBSJPPD-SCAN)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = svsjppd.raiadrogasil.com.br)))";
			break;
		case 908:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = DBRIBPD-SCAN)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = svribpd.raiadrogasil.com.br)))";
			break;
		case 1444:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = DBBUTPD-SCAN)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = svbutpd.raiadrogasil.com.br)))";
			break;
		case 1445:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = DBCONPD-SCAN)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = svconpd.raiadrogasil.com.br)))";
			break;
		case 1446:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = DBGOIPD-SCAN)(PORT = 1521)))(CONNECT_DATA = (SERVICE_NAME = svgoipd.raiadrogasil.com.br)))";
			break;
		case 2023:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS = (PROTOCOL = TCP)(HOST = DBRECPD-SCAN)(PORT = 1521))(CONNECT_DATA =(SERVER = DEDICATED)(SERVICE_NAME = svrecpd.raiadrogasil.com.br)))";
			break;
		case 2376:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = dbsalpd.raiadrogasil.com.br)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = svsalpd.rd.com.br)))";
			break;
		case 102:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = RDP-SCAN)(PORT = 1521)) (ADDRESS = (PROTOCOL = TCP)(HOST = RDS-SCAN)(PORT = 1521))(CONNECT_DATA = (SERVER = DEDICATED)(SERVICE_NAME = rdprod.raiadrogasil.com.br)))";
			break;
		case 103:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = RDS-SCAN)(PORT = 1521))(ADDRESS = (PROTOCOL = TCP)(HOST = RDP-SCAN)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = rdstdb.raiadrogasil.com.br)))";
			break;
		case 100: // MsafDesenv
			conexao = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = 192.1.1.115)(PORT = 1521))) (CONNECT_DATA = (SERVICE_NAME = msafd01.raiadrogasil.com.br)))";
			break;
		case 101: // MSAFP01
			conexao = "jdbc:oracle:thin:@(DESCRIPTION = (ADDRESS_LIST = (ADDRESS = (PROTOCOL = TCP)(HOST = lxmsafdbprod)(PORT = 1521)) (ADDRESS = (PROTOCOL = TCP)(HOST = dball02)(PORT = 1521)) )(CONNECT_DATA = (SERVICE_NAME = msafprod.raiadrogasil.com.br)))";
			break;
		case 1022:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = 192.1.1.150)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = r102d.raiadrogasil.com.br)))";
			break;
		case 1023:
			conexao = "jdbc:oracle:thin:@(DESCRIPTION =(ADDRESS_LIST =(ADDRESS = (PROTOCOL = TCP)(HOST = 192.1.1.150)(PORT = 1521)))(CONNECT_DATA =(SERVICE_NAME = r102h.raiadrogasil.com.br)))";
			break;
		}

		return DriverManager.getConnection(conexao, login, senha);

	}

}
