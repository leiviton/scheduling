package br.com.rd.scheduledootax;

import java.awt.AWTException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	//@Scheduled(fixedRate = 120000)
	public void ajustarEnvio() throws SQLException {
		Connection conn = new ConnectionFactory().getConnection(102, "producao", "raiaprod");
		PreparedStatement query = null;
		ResultSet rs = null;

		try {
			System.out.println("[INFO] Iniciou o processo de ajuste geral.");
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT NF.ID_NF ");
			sql.append("   ,nf.dt_emissao ");
			sql.append("   ,NF.CD_FILIAL ");
			sql.append(" FROM TB_NF NF ");
			sql.append(" INNER JOIN NFE.TB_NFE_ENVIO E ON E.ID_NF = NF.ID_NF ");
			sql.append("WHERE NF.DT_EMISSAO >= TRUNC(SYSDATE-5) ");
			sql.append("       AND E.CD_STATUS = 2 ");
			sql.append("      AND NOT EXISTS (SELECT 1 FROM TB_DFE WHERE ID_NF = NF.ID_NF) ");
			sql.append("      AND NF.DT_TIMESTAMP <= TO_DATE('08/06/2020 16:14:00','DD/MM/YY HH24:MI:SS') ");
			sql.append("      AND NF.CD_OPERACAO_FISCAL IN (1020,1001,1306) ");
			
			query = conn.prepareStatement(sql.toString());
			rs = query.executeQuery();
			while (rs.next()) {
				System.out.println("ID: " + rs.getString("ID_NF"));
				PreparedStatement queryAjuste = null;
				StringBuilder sqlAjuste = new StringBuilder();
				
				sqlAjuste.append(
						"UPDATE NFE.TB_NFE_ENVIO SET CD_STATUS=1 WHERE ID_NF =" + rs.getBigDecimal("ID_NF"));
				try {
					queryAjuste = conn.prepareStatement(sqlAjuste.toString());
					queryAjuste.executeUpdate();
				} catch (Exception e) {
					System.out
							.println("[ERROR] Erro ao ajustar NOTAa. " + e.getMessage());
				} finally {
					queryAjuste.close();
				}

			}

		} catch (Exception e) {
			log.info("[ERROR] ajustesEnvio: " + e.getMessage());
		} finally {
			rs.close();
			query.close();
			conn.close();
		}

	}

	//@Scheduled(fixedRate = 120000)
	public void ajusteDuplicados() throws AWTException, SQLException {
		Connection conn = new ConnectionFactory().getConnection(102, "producao", "raiaprod");
		PreparedStatement query = null;
		ResultSet rs = null;
		try {
			System.out.println("[INFO] Iniciou o processo de ajuste geral.");
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT CD_TELEVENDA, COUNT(*)");
			sql.append("   FROM TB_NF NF");
			sql.append("   WHERE NF.DT_EMISSAO >= TRUNC(SYSDATE-1)");
			sql.append("         AND CD_OPERACAO_FISCAL IN (1001,1)");
			sql.append("         AND CD_TELEVENDA IS NOT NULL");
//          sql.append("         AND CD_TELEVENDA = " + cdTelevenda);
			sql.append("  HAVING COUNT(*) >= 2");
			sql.append("  GROUP BY CD_TELEVENDA");
			query = conn.prepareStatement(sql.toString());
			rs = query.executeQuery();
			while (rs.next()) {

				PreparedStatement queryTelevenda = null;
				ResultSet rsTelevenda = null;
				StringBuilder sqlTelevenda = new StringBuilder();
				sqlTelevenda.append("SELECT ID_NF, CD_OPERACAO_FISCAL FROM TB_NF WHERE CD_TELEVENDA = "
						+ rs.getLong("CD_TELEVENDA") + " ORDER BY CD_TELEVENDA, NR_NF DESC");
				System.out.println("[INFO] Ajustando a televenda: " + rs.getLong("CD_TELEVENDA"));
				try {

					int ttProcessos = 1;
					queryTelevenda = conn.prepareStatement(sqlTelevenda.toString());
					rsTelevenda = queryTelevenda.executeQuery();
					while (rsTelevenda.next()) {

						if (ttProcessos == 1) {

							if (rsTelevenda.getInt("CD_OPERACAO_FISCAL") == 1001) {

								PreparedStatement queryAjuste = null;
								StringBuilder sqlAjuste = new StringBuilder();
								sqlAjuste.append(" begin");
								sqlAjuste.append(
										" UPDATE TB_NF SET CD_OPERACAO_FISCAL = 1350, CD_TELEVENDA = NULL WHERE ID_NF in ("
												+ rsTelevenda.getBigDecimal("ID_NF") + ");");
								sqlAjuste.append(" UPDATE TB_DFE SET CD_JAVA_STATUS = 99 WHERE ID_NF in ("
										+ rsTelevenda.getBigDecimal("ID_NF") + ");");
								sqlAjuste.append(" end;");
								try {
									queryAjuste = conn.prepareStatement(sqlAjuste.toString());
									queryAjuste.executeUpdate();
								} catch (Exception e) {
									System.out
											.println("[ERROR] Erro ao ajustar televenda duplicada. " + e.getMessage());
								} finally {
									queryAjuste.close();
								}

							}
							ttProcessos = ttProcessos + 1;

						} else if (ttProcessos == 2) {

							if (rsTelevenda.getInt("CD_OPERACAO_FISCAL") == 1001) {

								PreparedStatement queryReprocesso = null;
								StringBuilder sqlReprocesso = new StringBuilder();
								sqlReprocesso.append(" UPDATE NFE.TB_NFE_ENVIO SET CD_STATUS = 1 WHERE ID_NF = "
										+ rsTelevenda.getBigDecimal("ID_NF"));
								try {

									queryReprocesso = conn.prepareStatement(sqlReprocesso.toString());
									queryReprocesso.executeUpdate();

								} catch (Exception e) {
									System.out.println(
											"[ERROR] Erro ao reprocessar televenda duplicada. " + e.getMessage());
								} finally {
									queryReprocesso.close();
								}

							}
							ttProcessos = ttProcessos + 1;

						}

					}

				} catch (Exception e) {
					System.out.println("[ERROR] Erro ao consultar televenda duplicada. " + e.getMessage());
				} finally {
					queryTelevenda.close();
					rsTelevenda.close();
				}

				System.out.println("[INFO] Finalizou o ajuste da televenda: " + rs.getLong("CD_TELEVENDA"));

			}

			System.out.println("[INFO] Processo finalizado com sucesso.");

		} catch (Exception e) {
			log.info("[ERROR] ajusteDuplicados: " + e.getMessage());
		} finally {
			rs.close();
			query.close();
			conn.close();
		}
	}
}
