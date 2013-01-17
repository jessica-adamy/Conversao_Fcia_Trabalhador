package tela;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import net.miginfocom.swing.MigLayout;
import conexao.Conexao;

public class App extends JFrame {

	private static final long serialVersionUID = 1L;

	private JCheckBox cboxPRODU;
	private JCheckBox cboxFABRI;
	private JTextField txtFBBanco;
	private JTextField txtVmdServidor;
	private JTextField txtVmdBanco;
	private JCheckBox cboxGRPRC;
	private JButton btn_limpa_dados;
	private JProgressBar progressBar;
	private JButton btn_processa;
	private JProgressBar progressBar2;
	private JCheckBox cboxFORNE;
	private JLabel lblNewLabel_4;
	private JTextField txtVmdServidorConsulta;
	private JLabel lblBanco;
	private JTextField txtVmdBancoConsulta;
	private JCheckBox cboxCLIEN;
	private JCheckBox cboxENDER;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		setTitle("inFarma - Conversor de dados");
		setResizable(false);
		setBounds(100, 100, 460, 314);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelTop = new JPanel();
		getContentPane().add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new MigLayout("", "[fill][grow][][grow]", "[][][][]"));
		
				JLabel lblNewLabel = new JLabel("Caminho BD Antigo");
				panelTop.add(lblNewLabel, "cell 0 0,alignx trailing");
		
				txtFBBanco= new JTextField();
				txtFBBanco.setText("C:/Tmp/BD.FDB");
				panelTop.add(txtFBBanco, "cell 1 0,growx");
				txtFBBanco.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("VMD Servidor");
		panelTop.add(lblNewLabel_1, "cell 0 1,alignx trailing");

		txtVmdServidor = new JTextField();
		txtVmdServidor.setText("localhost");
		panelTop.add(txtVmdServidor, "cell 1 1,growx");
		txtVmdServidor.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Banco");
		panelTop.add(lblNewLabel_2, "cell 2 1,alignx trailing,aligny baseline");

		txtVmdBanco = new JTextField();
		txtVmdBanco.setText("VMD");
		panelTop.add(txtVmdBanco, "cell 3 1,growx");
		txtVmdBanco.setColumns(10);
		
		lblNewLabel_4 = new JLabel("VMD de Consulta Servidor");
		panelTop.add(lblNewLabel_4, "cell 0 2,alignx trailing");
		
		txtVmdServidorConsulta = new JTextField();
		txtVmdServidorConsulta.setText("localhost");
		panelTop.add(txtVmdServidorConsulta, "cell 1 2,growx");
		txtVmdServidorConsulta.setColumns(10);
		
		lblBanco = new JLabel("Banco");
		panelTop.add(lblBanco, "cell 2 2,alignx trailing");
		
		txtVmdBancoConsulta = new JTextField();
		txtVmdBancoConsulta.setText("VMD_BASE");
		panelTop.add(txtVmdBancoConsulta, "cell 3 2,growx");
		txtVmdBancoConsulta.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel(
				"Converte uma base para o Varejo");
		panelTop.add(lblNewLabel_3, "cell 0 3 4 1");

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		class ProcessaWorker extends SwingWorker<Void, Void> {

			@Override
			protected Void doInBackground() throws Exception {
				progressBar.setValue(0);
				progressBar.setMaximum(20);
				btn_limpa_dados.setEnabled(false);
				btn_processa.setEnabled(false);
				int resp = JOptionPane.showConfirmDialog(panel, "Confirma?",
						"Processar Dados", JOptionPane.YES_NO_OPTION);
																		
				if (resp == 0) {

					Connection fb = Conexao.getFbConnection();
					Connection vmd = Conexao.getSqlConnection();
					Connection vmdConsulta = Conexao.getSqlConnectionConsulta();
						
					if (cboxFABRI.isSelected() && cboxGRPRC.isSelected()
						 && cboxPRODU.isSelected() && cboxCLIEN.isSelected() 
						 && cboxFORNE.isSelected() && cboxENDER.isSelected()) {

						// APAGANDO DADOS

						// PRODUTO
						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM PRODU");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(1);
						}

						// FABRI
						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM FABRI");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(2);
						}

						// GRPRC
						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM GRPRC");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(3);
						}
						
						//FORNE					
						if (cboxFORNE.isSelected()) {

							try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
								stmt.executeUpdate("DELETE FROM FORNE");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(4);
							}
						}
						
						//CLIEN					
						if (cboxCLIEN.isSelected()) {

							try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
								stmt.executeUpdate("DELETE FROM CLIEN");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(5);
							}
						}
						
						if (cboxENDER.isSelected()) {
						//CLXED
							try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
								stmt.executeUpdate("DELETE FROM CLXED");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(6);
							}
						//ENDER
							try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
								stmt.executeUpdate("DELETE FROM ENDER");
								stmt.close();
								System.out.println("Deletou");
								progressBar.setValue(7);
							}
						}
					}

					// IMPORTAÇÃO
						
					//FABRI
					if (cboxFABRI.isSelected()) {

						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM FABRI");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(8);
						}

						progressBar2.setValue(0);

						String fbFabri = "SELECT * FROM TBFABRICANTE";
						String vFabri = "Insert Into FABRI (Cod_Fabric, Des_Fabric, Num_Cnpj) Values (?,?,?)";
						try (PreparedStatement pVmd = vmd.prepareStatement(vFabri);
							 PreparedStatement pFb = fb.prepareStatement(fbFabri)) {
							
							ResultSet rs = pFb.executeQuery();

							// contar a qtde de registros
							int registros = contaRegistros("TBFABRICANTE");
							progressBar2.setMaximum(registros);
							registros = 0;

							while (rs.next()) {
								// grava no varejo
								pVmd.setInt(1, rs.getInt("CODIGO"));
							    pVmd.setString(2, rs.getString("DESCRICAO").length() > 25 ? rs.getString("DESCRICAO").substring(0, 24) : rs.getString("DESCRICAO"));
								pVmd.setString(3, rs.getString("CNPJ"));
								
								pVmd.executeUpdate();

								registros++;
								progressBar2.setValue(registros);
							}
							System.out.println("Funcionou Fabri");
							pVmd.close();
							pFb.close();

							progressBar2.setValue(0);
						}
						progressBar.setValue(9);
					}
						
					//GRPRC
					if (cboxGRPRC.isSelected()) {

						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM GRPRC");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(10);
						}

						progressBar2.setValue(0);

						String fbGRPRC = "SELECT * FROM TBGRUPO";
						String vGRPRC = "Insert Into GRPRC (Cod_GrpPrc, Des_GrpPrc) Values (?,?)";
						try (PreparedStatement pVmd = vmd.prepareStatement(vGRPRC);
							 PreparedStatement pFb = fb.prepareStatement(fbGRPRC)) {
							
							ResultSet rs = pFb.executeQuery();

							// contar a qtde de registros
							int registros = contaRegistros("TBGRUPO");
							progressBar2.setMaximum(registros);
							registros = 0;

							while (rs.next()) {
								// grava no varejo
								pVmd.setInt(1, rs.getInt("CODIGO"));
							    pVmd.setString(2, rs.getString("DESCRICAO"));
								
								pVmd.executeUpdate();

								registros++;
								progressBar2.setValue(registros);
							}
							System.out.println("Funcionou GRPRC");
							pVmd.close();
							pFb.close();

							progressBar2.setValue(0);
						}
						progressBar.setValue(11);
					}
						
					//PRODU
					if (cboxPRODU.isSelected()) {

						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM PRODU");
							System.out.println("Deletou");
							progressBar.setValue(12);
						}

						progressBar2.setValue(0);

						String fbPRODU = "select a.*, b.codncm as NCM from tbitem a join tbncm b on a.codncm = b.chave";
						String vPRODU = "Insert Into PRODU (Cod_Produt, Des_Produt, Des_Resumi, Des_Comple, Dat_Implan, Cod_Classi, Cod_Seccao, Cod_ClaTri, Ctr_Preco, Ctr_Lista, Ctr_Venda, Cod_Fabric, Cod_GrpPrc, Cod_EAN, Cod_Ncm, Qtd_FraVen, Qtd_EmbVen, Des_UniCom, Des_UniVen, Ctr_Origem, Cod_AbcFar, Prc_MaxVen, Num_REGMS) Values (?,?,?,?,?,'0199',1,'A','C','N','L',?,?,?,?,?,1,?,?,0,?,?,?)";
						String vPRXLJ = "Update PRXLJ set Prc_CusLiqMed = ?, Prc_CusEnt = ?, Prc_CusLiq = ?, Prc_VenAtu = ?, Flg_BlqVen = ?, Dat_UltVen = ? where Cod_Produt = ?";
						try (PreparedStatement pVmd = vmd.prepareStatement(vPRODU);
							 PreparedStatement prxljVmd = vmd.prepareStatement(vPRXLJ);
							 PreparedStatement pFb = fb.prepareStatement(fbPRODU);
								) {
							
							ResultSet rs = pFb.executeQuery();

							// contar a qtde de registros
							int registros = contaRegistros("TBITEM");
							progressBar2.setMaximum(registros);
							registros = 0;
							while (rs.next()) {
								// GRAVA NA PRODU
								pVmd.setInt(1, rs.getInt("CODITEM"));
							    pVmd.setString(2, rs.getString("DESCRICAO").length() > 40 ? rs.getString("DESCRICAO").substring(0, 39) : rs.getString("DESCRICAO"));
							    pVmd.setString(3, rs.getString("DESCRICAO").length() > 24 ? rs.getString("DESCRICAO").substring(0, 23) : rs.getString("DESCRICAO"));
							    pVmd.setString(4, rs.getString("DESCRICAO").length() > 50 ? rs.getString("DESCRICAO").substring(0, 49) : rs.getString("DESCRICAO"));
							    pVmd.setDate(5, rs.getDate("DTCAD"));
							    pVmd.setString(6, rs.getString("CODFAB"));
							    pVmd.setInt(7, rs.getInt("CODGRUPOI"));
							    pVmd.setString(8, rs.getString("CODBARRA"));
							    
							    if(!existeNCM(rs.getString("NCM"))) {
							    	cadastraNCM(rs.getString("NCM"));
							    }
						    	pVmd.setInt(9, rs.getInt("NCM") );
						    	
						    	pVmd.setString(10, rs.getString("QTDNACAIXA"));
						    	pVmd.setString(11, rs.getString("CODUNDI"));
						    	pVmd.setString(12, rs.getString("CODUNDI"));
						    	pVmd.setInt(13, rs.getInt("CODABCFARMA"));
						    	pVmd.setInt(14, rs.getInt("PMAXCONS"));
						    	pVmd.setInt(15, rs.getInt("RMSREMEDIO"));
								
								pVmd.executeUpdate();
								
								// GRAVA NA PRXLJ
								prxljVmd.setInt(1, rs.getInt("CUSTOMEDIO"));
								prxljVmd.setString(2, rs.getString("VALORULTCOMPRA"));
								prxljVmd.setString(3, rs.getString("VALORULTCOMPRA"));
								prxljVmd.setString(4, rs.getString("VALOR"));
								prxljVmd.setBoolean(5, rs.getString("ATIVO").equals("N") ? true : false);
								prxljVmd.setDate(6, rs.getDate("DTULTMODPRECO"));
								prxljVmd.setInt(7, rs.getInt("CODITEM"));
								
								prxljVmd.executeUpdate();
								
								
								cadastraCamposQueFaltamPRODU(rs.getString("CODBARRA"));
								registros++;
								progressBar2.setValue(registros);
							}
							
							System.out.println("Funcionou PRODU");
							pVmd.close();
							pFb.close();

							progressBar2.setValue(0);
						}
						progressBar.setValue(13);
					}
					
					//FORNE					
					if (cboxFORNE.isSelected()) {

						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM FORNE");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(14);
						}

						progressBar2.setValue(0);

						String fbFORNE = "select a.codfor, a.cnpj, a.rg, a.nome, a.endereco, a.bairro, a.cep, a.fone, a.fax, a.uf, a.cidade, b.codibge as codibge from tbfor a join tbcidade b on (a.codcidadei = b.codigo)";
						String vFORNE = "Insert Into FORNE (Cod_Fornec, Num_CgcCpf, Num_CgfRg, Des_RazSoc, Des_Fantas, Cod_IBGE, Des_Endere, Des_Bairro, Num_Cep, Num_Fone, Num_Fax, Flg_Bloque, Des_Estado, Des_Cidade, Cod_RegTri) Values (?,?,?,?,?,?,?,?,?,?,?,0,?,?,?)";
						try (PreparedStatement pVmd = vmd.prepareStatement(vFORNE);
							 PreparedStatement pFb = fb.prepareStatement(fbFORNE)) {
							
							ResultSet rs = pFb.executeQuery();

							// contar a qtde de registros
							int registros = contaRegistros("TBFOR");
							progressBar2.setMaximum(registros);
							registros = 0;

							while (rs.next()) {
								// grava no varejo
								pVmd.setInt(1, rs.getInt("CODFOR"));
							    pVmd.setString(2, rs.getString("CNPJ"));
							    pVmd.setString(3, rs.getString("RG").replace(".", "").replace("/", "").replace("-", ""));
							    pVmd.setString(4, rs.getString("NOME").length() > 35 ? rs.getString("NOME").substring(0, 34) : rs.getString("NOME"));
							    pVmd.setString(5, rs.getString("NOME").length() > 25 ? rs.getString("NOME").substring(0, 24) : rs.getString("NOME"));
							    pVmd.setInt(6, rs.getInt("CODIBGE"));
							    pVmd.setString(7, rs.getString("ENDERECO").length() > 25 ? rs.getString("ENDERECO").substring(0, 24) : rs.getString("ENDERECO"));
							    pVmd.setString(8, rs.getString("BAIRRO").length() > 25 ? rs.getString("BAIRRO").substring(0, 24) : rs.getString("BAIRRO"));
							    pVmd.setString(9, rs.getString("CEP"));
							    pVmd.setString(10, rs.getString("FONE"));
							    pVmd.setString(11, rs.getString("FAX"));
							    pVmd.setString(12, rs.getString("UF"));
							    pVmd.setString(13, rs.getString("CIDADE"));
							    
							    if(rs.getString("UF").equals("CE")){
							    	pVmd.setInt(14, 4);
							    }
							    if(rs.getString("UF").equals("AC") || rs.getString("UF").equals("AL") || rs.getString("UF").equals("AP") ||
								   rs.getString("UF").equals("AM") || rs.getString("UF").equals("BA") || rs.getString("UF").equals("DF") ||
								   rs.getString("UF").equals("GO") || rs.getString("UF").equals("MA") || rs.getString("UF").equals("MT") ||
								   rs.getString("UF").equals("MS") || rs.getString("UF").equals("PB") || rs.getString("UF").equals("PA") ||
								   rs.getString("UF").equals("PE") || rs.getString("UF").equals("PI") || rs.getString("UF").equals("RN") ||
								   rs.getString("UF").equals("RO") || rs.getString("UF").equals("RR") || rs.getString("UF").equals("SE") || rs.getString("UF").equals("TO")){
							    	pVmd.setInt(14, 1);
							    }
							    
							    if(rs.getString("UF").equals("ES") || rs.getString("UF").equals("MG") ||
									       rs.getString("UF").equals("PR") || rs.getString("UF").equals("RJ") || 
									       rs.getString("UF").equals("RS") || rs.getString("UF").equals("SC") || 
									       rs.getString("UF").equals("SP")){
							    	pVmd.setInt(14, 9);
							    }
							    
								// todo demais campo
								pVmd.executeUpdate();

								registros++;
								progressBar2.setValue(registros);
							}
							System.out.println("Funcionou FORNE");
							pVmd.close();
							pFb.close();

							progressBar2.setValue(0);
						}
						progressBar.setValue(15);
					}
					
					//CLIEN
					if (cboxCLIEN.isSelected()) {

						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM CLIEN");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(16);
						}

						progressBar2.setValue(0);

						String fbCLIEN = "SELECT * FROM TBCLI";
						String vCLIEN = "Insert Into CLIEN (Cod_Client, Nom_Client, Dat_Cadast, Sex_Client, Num_CpfCgc, Num_RgCgf, Num_FonCel, Des_Email, Dat_UltCpr, Des_Observ, Cod_RegTri, Cod_GrpCli, Ctr_Vencim) Values (?,?,?,null,?,?,?,?,?,?,?,1,'N')";
						try (PreparedStatement pVmd = vmd.prepareStatement(vCLIEN);
							 PreparedStatement pFb = fb.prepareStatement(fbCLIEN)) {
							
							ResultSet rs = pFb.executeQuery();

							// contar a qtde de registros
							int registros = contaRegistros("TBCLI");
							progressBar2.setMaximum(registros);
							registros = 0;

							while (rs.next()) {
								// grava no varejo
								
								System.out.println("Data de nascimento: "+rs.getDate("DTNASC"));
								pVmd.setInt(1, rs.getInt("CODCLI"));
							    pVmd.setString(2, rs.getString("NOME").length() > 35 ? rs.getString("NOME").substring(0, 34) : rs.getString("NOME"));
							    pVmd.setDate(3, rs.getDate("DTCAD"));
							    pVmd.setString(4, rs.getString("CPF"));
							    pVmd.setString(5, rs.getString("RG"));
							    pVmd.setString(6, rs.getString("CELULAR"));
							    pVmd.setString(7, rs.getString("EMAIL"));
							    pVmd.setDate(8, rs.getDate("DTULTCOMPRA"));
							    pVmd.setString(9, rs.getString("OBS"));
								
							    if(rs.getString("UF").equals("CE")){
							    	pVmd.setInt(10, 4);
							    }
							    if(rs.getString("UF").equals("AC") || rs.getString("UF").equals("AL") || rs.getString("UF").equals("AP") ||
								   rs.getString("UF").equals("AM") || rs.getString("UF").equals("BA") || rs.getString("UF").equals("DF") ||
								   rs.getString("UF").equals("GO") || rs.getString("UF").equals("MA") || rs.getString("UF").equals("MT") ||
								   rs.getString("UF").equals("MS") || rs.getString("UF").equals("PB") || rs.getString("UF").equals("PA") ||
								   rs.getString("UF").equals("PE") || rs.getString("UF").equals("PI") || rs.getString("UF").equals("RN") ||
								   rs.getString("UF").equals("RO") || rs.getString("UF").equals("RR") || rs.getString("UF").equals("SE") || rs.getString("UF").equals("TO")){
							    	pVmd.setInt(10, 1);
							    }
							    
							    if(rs.getString("UF").equals("ES") || rs.getString("UF").equals("MG") ||
									       rs.getString("UF").equals("PR") || rs.getString("UF").equals("RJ") || 
									       rs.getString("UF").equals("RS") || rs.getString("UF").equals("SC") || 
									       rs.getString("UF").equals("SP")){
							    	pVmd.setInt(10, 9);
							    }
							    
								pVmd.executeUpdate();

								registros++;
								progressBar2.setValue(registros);
							}
							System.out.println("Funcionou CLIEN");
							pVmd.close();
							pFb.close();

							progressBar2.setValue(0);
						}
						progressBar.setValue(17);
						
					}
					
					//ENDER
					if (cboxENDER.isSelected()) {

						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM CLXED");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(18);
						}
						
						try (Statement stmt = vmd.createStatement()) {
							stmt.executeUpdate("DELETE FROM ENDER");
							stmt.close();
							System.out.println("Deletou");
							progressBar.setValue(19);
						}

						progressBar2.setValue(0);

						String fbENDER = "select a.*, b.descricao as cidade from tbcli a join tbcidade b on a.codcidadei = b.codigo";
						String vENDER = "Insert Into ENDER (Cod_EndFon, Nom_Contat, Des_Endere, Des_Bairro, Num_CEP, Des_Estado, Des_Cidade, Dat_Cadast) Values (?,?,?,?,?,?,?,?)";
						String vCLXED = "Insert Into CLXED (Cod_Client, Cod_EndFon) Values (?,?)";
						String vCLIEN = "Update CLIEN set Cod_EndRes = ? where Cod_Client = ?";
						try (PreparedStatement pVmd = vmd.prepareStatement(vENDER);
							 PreparedStatement pFb = fb.prepareStatement(fbENDER);
							 PreparedStatement pVmdCLXED = vmd.prepareStatement(vCLXED);
							 PreparedStatement pVmdCLIEN = vmd.prepareStatement(vCLIEN)) {
							
							ResultSet rs = pFb.executeQuery();

							// contar a qtde de registros
							int registros = contaRegistros("TBCLI");
							progressBar2.setMaximum(registros);
							registros = 0;

							while (rs.next()) {
								// grava no varejo
								
								String wfone = "";
								if(rs.getString("FONE") != null){
								String fone = rs.getString("FONE").replaceAll(" ", "").replaceAll("\\)", "").replaceAll("\\(", "").replaceAll("\\-", "").replaceAll("\\.", "");
								
								if(!rs.getString("FONE").replaceAll(" ", "").equals("") && !existeCod_EndFon(fone)){
									wfone = fone;
								}else{
									wfone = "000"+rs.getString("CODCLI");
								}
								}else{
									wfone = "000"+rs.getString("CODCLI");
								}
								pVmd.setString(1, wfone);
							    pVmd.setString(2, rs.getString("NOME").length() > 35 ? rs.getString("NOME").substring(0, 34) : rs.getString("NOME"));
							    pVmd.setString(3, rs.getString("ENDERECO"));
							    pVmd.setString(4, rs.getString("BAIRRO"));
							    pVmd.setString(5, rs.getString("CEP"));
							    pVmd.setString(6, rs.getString("UF"));
							    pVmd.setString(7, rs.getString("CIDADE"));
							    pVmd.setDate(8, rs.getDate("DTCAD"));
								pVmd.executeUpdate();
								
								//CLXED
								pVmdCLXED.setString(1, rs.getString("CODCLI"));
								pVmdCLXED.setString(2, wfone);
								pVmdCLXED.executeUpdate();
								
								//CLIEN
								pVmdCLIEN.setString(1, wfone);
								pVmdCLIEN.setString(2, rs.getString("CODCLI"));
								pVmdCLIEN.executeUpdate();


								registros++;
								progressBar2.setValue(registros);
							}
							System.out.println("Funcionou ENDER");
							pVmd.close();
							pFb.close();

							progressBar2.setValue(0);
						}
						progressBar.setValue(20);
						
					}
					
					// JOptionPane.showMessageDialog(null,
					// "Dados importados com sucesso.");
					JOptionPane.showMessageDialog(getContentPane(),
							"Processamento de dados realizado com sucesso",
							"Informação", JOptionPane.INFORMATION_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(getContentPane(),
							"Processamento de dados cancelado", "Informação",
							JOptionPane.INFORMATION_MESSAGE);
				}

				return null;
			}

			@Override
			protected void done() {
				try {
					progressBar.setValue(0);
					btn_limpa_dados.setEnabled(true);
					btn_processa.setEnabled(true);
					getContentPane().setCursor(Cursor.getDefaultCursor());
					// Descobre como está o processo. É responsável por lançar
					// as exceptions
					get();
					// JOptionPane.showMessageDialog(getContentPane(),
					// "Processamento de dados realizado com sucesso",
					// "Informação", JOptionPane.INFORMATION_MESSAGE);
				} catch (ExecutionException e) {
					final String msg = String.format(
							"Erro ao exportar dados: %s", e.getCause()
									.toString());
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(getContentPane(),
									"Erro ao exportar: " + msg, "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					});
				} catch (InterruptedException e) {
					System.out.println("Processo de exportação foi interrompido");
				}
			}
		}

		btn_processa = new JButton("Processa");
		btn_processa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				Conexao.FB_BANCO = txtFBBanco.getText();
				Conexao.SQL_BANCO = txtVmdBanco.getText();
				Conexao.SQL_SERVIDOR = txtVmdServidor.getText();
				Conexao.SQL_SERVIDOR_CONSULTA = txtVmdServidorConsulta.getText();
				Conexao.SQL_BANCO_CONSULTA = txtVmdBancoConsulta.getText();
				new ProcessaWorker().execute();
			}
		});

		class LimpaDadosWorker extends SwingWorker<Void, Void> {

			@Override
			protected Void doInBackground() throws Exception {
				progressBar.setValue(0);
				progressBar.setMaximum(8);
				btn_limpa_dados.setEnabled(false);
				btn_processa.setEnabled(false);
				int resp = JOptionPane.showConfirmDialog(panel, "Confirma?",
						"Limpeza de Dados", JOptionPane.YES_NO_OPTION);

				if(resp == 0){
				// APAGANDO DADOS 

				// PRODUTO
				if (cboxPRODU.isSelected()) {
					try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
						stmt.executeUpdate("DELETE FROM PRODU");
						stmt.close();
						System.out.println("Deletou");
						progressBar.setValue(1);
					}
				}

				// FABRI
				if (cboxFABRI.isSelected()) {
					try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
						stmt.executeUpdate("DELETE FROM FABRI");
						stmt.close();
						System.out.println("Deletou");
						progressBar.setValue(2);
					}
				}


				// GRPRC
				if (cboxGRPRC.isSelected()) {
					try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
						stmt.executeUpdate("DELETE FROM GRPRC");
						stmt.close();
						System.out.println("Deletou");
						progressBar.setValue(3);
					}
				}
				
				//FORNE					
				if (cboxFORNE.isSelected()) {

					try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
						stmt.executeUpdate("DELETE FROM FORNE");
						stmt.close();
						System.out.println("Deletou");
						progressBar.setValue(4);
					}
				}
				
				//CLIEN					
				if (cboxCLIEN.isSelected()) {

					try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
						stmt.executeUpdate("DELETE FROM CLIEN");
						stmt.close();
						System.out.println("Deletou");
						progressBar.setValue(5);
					}
				}
				
				if (cboxENDER.isSelected()) {
				try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
					stmt.executeUpdate("DELETE FROM CLXED");
					stmt.close();
					System.out.println("Deletou");
					progressBar.setValue(7);
				}
				
				try (Statement stmt = Conexao.getSqlConnection().createStatement()) {
					stmt.executeUpdate("DELETE FROM ENDER");
					stmt.close();
					System.out.println("Deletou");
					progressBar.setValue(8);
				}
				}
				// JOptionPane.showMessageDialog(null,
				// "Dados importados com sucesso.");
				JOptionPane.showMessageDialog(getContentPane(),
						"Limpeza de dados realizada com sucesso",
						"Informação", JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(getContentPane(),
						"Limpeza de dados cancelada", "Informação",
						JOptionPane.INFORMATION_MESSAGE);
			}
				return null;
			}

			@Override
			protected void done() {
				try {
					progressBar.setValue(0);
					btn_limpa_dados.setEnabled(true);
					btn_processa.setEnabled(true);
					getContentPane().setCursor(Cursor.getDefaultCursor());

					// Descobre como está o processo. É responsável por lançar
					// as exceptions
					get();
					
//					JOptionPane.showMessageDialog(getContentPane(),
//							"Limpeza de dados realizada com sucesso", "Info",
//							JOptionPane.INFORMATION_MESSAGE);
				} catch (ExecutionException e) {
					final String msg = String.format("Erro ao limpar dados: %s", e.getCause().toString());
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							JOptionPane.showMessageDialog(getContentPane(),
									"Erro ao limpar: " + msg, "Erro",
									JOptionPane.ERROR_MESSAGE);
						}
					});
				} catch (InterruptedException e) {
					System.out.println("Processo de exportação foi interrompido");
				}
			}
		}

		btn_limpa_dados = new JButton("Limpa Dados");
		btn_limpa_dados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				Conexao.FB_BANCO = txtFBBanco.getText();
				Conexao.SQL_BANCO = txtVmdBanco.getText();
				Conexao.SQL_SERVIDOR = txtVmdServidor.getText();
				new LimpaDadosWorker().execute();
			}
		});
		panel.add(btn_limpa_dados);
		panel.add(btn_processa);

		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[][][][][][][grow,fill]", "[][][][][][][][]"));
		
				cboxFABRI = new JCheckBox("1 - FABRI");
				cboxFABRI.setSelected(true);
				panel_1.add(cboxFABRI, "cell 0 0");
								
								cboxFORNE = new JCheckBox("4-FORNE");
								cboxFORNE.setSelected(true);
								panel_1.add(cboxFORNE, "cell 1 0");
						
								cboxGRPRC = new JCheckBox("2 -GRPRC");
								cboxGRPRC.setSelected(true);
								panel_1.add(cboxGRPRC, "cell 0 1");
				
				cboxCLIEN = new JCheckBox("5-CLIEN");
				cboxCLIEN.setSelected(true);
				panel_1.add(cboxCLIEN, "cell 1 1");
		
				cboxPRODU = new JCheckBox("3 - PRODU");
				cboxPRODU.setSelected(true);
				panel_1.add(cboxPRODU, "cell 0 2");
		
		cboxENDER = new JCheckBox("6-ENDER");
		cboxENDER.setSelected(true);
		panel_1.add(cboxENDER, "cell 1 2");

		progressBar = new JProgressBar();
		progressBar.setMaximum(20);
		panel_1.add(progressBar, "cell 0 6 7 1,growx");

		progressBar2 = new JProgressBar();
		panel_1.add(progressBar2, "cell 0 7 7 1,growx");

	}

//	private int prox(String chave, String tabela) {
//		try (Statement s = Conexao.getSqlConnectionAux().createStatement();
//				ResultSet rs = s.executeQuery("(Select Isnull(MAX(" + chave	+ "), 0) + 1 From " + tabela + ")")) {
//			if (rs.next())
//				return rs.getInt(1);
//			return 0;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}

	private int contaRegistros(String tabela) throws SQLException {
		String sql = "SELECT count(*) qtde FROM " + tabela;
			try (PreparedStatement ps = Conexao.getFbConnectionAux().prepareStatement(sql);
					ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
			}
			return 0;
		}
	}
	
	private boolean existeNCM(String cod_ncm) throws SQLException {
		String sql = "SELECT CAST(CASE WHEN EXISTS(SELECT * FROM TBNCM where Cod_Ncm = " +cod_ncm+") THEN 1 ELSE 0 END AS BIT)";
		try (PreparedStatement ps = Conexao.getSqlConnectionAux().prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getBoolean(1);
				} else
					return false;
		}
	}
	
	private boolean existeCod_EndFon(String Cod_EndFon) throws SQLException {
		String sql = "SELECT CAST(CASE WHEN EXISTS(SELECT * FROM ENDER where Cod_EndFon = '"+Cod_EndFon+"') THEN 1 ELSE 0 END AS BIT)";
		try (PreparedStatement ps = Conexao.getSqlConnectionAux().prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getBoolean(1);
				} else
					return false;
		}
	}
	
	private void cadastraNCM(String cod_ncm) throws SQLException {
		String fbNCM = "select * from tbncm where codncm = '" +cod_ncm+"'";
		String vNCM = "Insert Into TBNCM (Cod_Ncm, Des_Ncm) Values (?,?)";
		try (PreparedStatement ps = Conexao.getSqlConnectionAux().prepareStatement(vNCM);
			 PreparedStatement pf = Conexao.getFbConnectionAux().prepareStatement(fbNCM)) {
			
			ResultSet rs = pf.executeQuery();

			while (rs.next()) {
				// grava no varejo
				ps.setInt(1, rs.getInt("CODNCM"));
			    ps.setString(2, rs.getString("DESCRICAO"));
				
				ps.executeUpdate();

			}
			System.out.println("CADASTROU NCM");
			pf.close();
			ps.close();
	}
	}
	
	private void cadastraCamposQueFaltamPRODU(String cod_ean) throws SQLException {
		String vProduVmdBase = "select * from PRODU where Cod_Ean = '" +cod_ean+"'";
		String vProduVmd = "Update Produ set Cod_Classi=?, Cod_Seccao=?, Cod_ClaTri=?, Ctr_Preco=?, Ctr_Lista=?, Tip_Por344=?, Ctr_Venda=?, Cod_SubBas=? where Cod_Ean = '"+cod_ean+"'";
		try (PreparedStatement v1 = Conexao.getSqlConnectionConsulta().prepareStatement(vProduVmdBase);
			 PreparedStatement v2 = Conexao.getSqlConnectionAux().prepareStatement(vProduVmd)) {
			
			ResultSet rs = v1.executeQuery();

			while (rs.next()) {
				// grava no varejo
				v2.setString(1, rs.getString("Cod_Classi"));
			    v2.setInt(2, rs.getInt("Cod_Seccao"));
			    v2.setString(3, rs.getString("Cod_ClaTri"));
			    v2.setString(4, rs.getString("Ctr_Preco"));
			    v2.setString(5, rs.getString("Ctr_Lista"));
			    v2.setString(6, rs.getString("Tip_Por344"));
			    v2.setString(7, rs.getString("Ctr_Venda"));
			    if(rs.getInt("Cod_SubBas") != 0){
			    v2.setInt(8, rs.getInt("Cod_SubBas"));
			    }else
			    v2.setString(8, null);
			    
				v2.executeUpdate();

			}
			v1.close();
			v2.close();
	}
	}
	// private int contaRegistros2(String tabela, String where, Connection c)
	// throws SQLException {
	// String sql = "SELECT count(*) qtde FROM "+tabela+" "+where;
	// try (PreparedStatement ps = c.prepareStatement(sql)) {
	// ResultSet rs = ps.executeQuery();
	// if (rs.next()) {
	// return rs.getInt(1);
	// }
	// return 0;
	// }
	// }
	// public JCheckBox getCboxFORNE() {
	// return cboxFORNE;
	// }
	public JCheckBox getCboxPRXLJ() {
		return cboxCLIEN;
	}
	
	public JCheckBox getCboxENDER() {
		return cboxENDER;
	}
}
