package br.com.rd.scheduledootax;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Query {

	private static final Logger log = LoggerFactory.getLogger(Query.class);

	public void ajusteDuplicados() throws AWTException, SQLException {
		Connection conn = new ConnectionFactory().getConnection(102, "producao", "raiaprod");
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			log.info("[INFO] Iniciou o processo de ajuste estoque.");
			StringBuilder sql = new StringBuilder();
			sql.append("BEGIN " + "P_ATUALIZA_ESTOQUE_NOTA(3966793815,3966793815);" + "END;");
			query = conn.prepareStatement(sql.toString());
			rs = query.executeQuery();
		} catch (Exception e) {
			log.info("[ERROR] ajusteEstoque: " + e.getMessage());
		} finally {
			rs.close();
			query.close();
			conn.close();
		}
	}
}
