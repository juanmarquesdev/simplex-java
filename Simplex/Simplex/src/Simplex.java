import java.util.Scanner;

public class Simplex {

	public static Scanner sc = new Scanner(System.in);
	public static int linha;
	public static int coluna;
	
	
	public static void main(String[] args) {
		
		int qntVarDecisao;
		int qntRestricao;
		double[][] tabela;
		
		System.out.println("Quantas variaveis de decis�o tem o problema? ");
		qntVarDecisao = Integer.parseInt(sc.nextLine());
		
		System.out.println("Quantas restri��es? ");
		qntRestricao = Integer.parseInt(sc.nextLine());
		
		linha = qntRestricao + 1;
		coluna = linha + qntVarDecisao + 1;
		
		tabela = new double[linha][coluna];
		
		gerarTabela(tabela);
		funcaoObjetivo(tabela, qntVarDecisao);
		gerarRestricoes(tabela, qntRestricao, qntVarDecisao);
		
		
		for(int i = 0; i < tabela.length; i++) {
			for(int n = 0; n < tabela[0].length; n++) {
				System.out.printf("%.2f ", tabela[i][n]);
			}
			System.out.println();
		}
		
	}
	
	public static double[][] gerarTabela(double[][] tabela) {
		
		for(int i = 0; i < tabela.length; i++) {
			for(int n = 0; n < tabela[0].length; n++) {
				tabela[i][n] = 0;
			}
		}
		
		return tabela;
	}
	
	public static double[][] funcaoObjetivo(double[][] tabela, int qntVarDecisao) {
		
		int key = 0;
		
		System.out.println("A fun��o � para: ");
		System.out.println(" Maximizar (Digite 1)");
		System.out.println(" Minimizar (Digite 2)");
		
		while(key != 1 && key != 2) {
			key = Integer.parseInt(sc.nextLine());
			switch (key) {
			case 1:
				break;
			case 2:
				break;
			default:
				System.out.println("Op��o inv�lida! Digite novamente.");
			}
		}
		
		
		
		for(int i = 0; i < tabela[0].length; i++) {
			if(i == 0) {
				tabela[0][i] = 1;
			} else if (i <= qntVarDecisao) {
				System.out.printf("Qual o valor da variavel X%d na funcao objetiva?%n", i);
				tabela[0][i] = (-1) * Double.parseDouble(sc.nextLine());
			} else {
				tabela[0][i] = 0;
			}
		}
		
		if(key == 2) {
			for (int m = 1; m < tabela[0].length; m++) {
				if (tabela[0][m] != 0) {
					tabela[0][m] = tabela[0][m] * (-1);
				}
			}
		}
		
		System.out.print("Fun��o objetivo: Z = ");
		
		for (int k = 1; k <= qntVarDecisao; k++) {
			if(k < qntVarDecisao) {
				System.out.printf("%.0fX%d + ", tabela[0][k]*(-1), k);
			}else {
				System.out.printf("%.0fX%d%n", tabela[0][k]*(-1), k);
			}
		}
				
		return tabela;
	}
	
	public static double[][] gerarRestricoes(double[][] tabela, int qntRestricao, int qntVarDecisao) {
		
		int key = 0;
		int keyArray[];
		keyArray = new int[qntRestricao+1];
		
		
		for(int r = 1; r <= qntRestricao; r++) {
			for(int i = 1; i < tabela[r].length; i++) {
				
				if (i <= qntVarDecisao) {
					System.out.printf("Qual o valor da variavel X%d na restricao %d?%n", i, r);
					tabela[r][i] = Double.parseDouble(sc.nextLine());
				} else if (i == (tabela[r].length -1)) {
					System.out.printf("Qual o valor do resultado na restricao %d?%n", r);
					tabela[r][i] = Double.parseDouble(sc.nextLine());
					if(key == 1) {
						for (int m = 0; m < tabela[r].length; m++) {
							if (tabela[r][m] != 0) {
								tabela[r][m] = tabela[r][m] * (-1);
							}
						}
						
					}
				} else if (i == (qntVarDecisao + r)) {
					
					System.out.println("O resultado �: ");
					System.out.println(" >= maior igual (Digite 1)");
					System.out.println(" <= menor igual (Digite 2)");
					System.out.println(" = igual (Digite 3)");
					
					key = 0;
					keyArray[0] = 0;
					
					while(key != 1 && key != 2 && key != 3 ) {
						
						key = Integer.parseInt(sc.nextLine());
						
						switch (key) {
						case 1: 
							tabela[r][i] = -1;
							keyArray[r] = 2;
							break;
								
						case 2:
							tabela[r][i] = 1;
							keyArray[r] = 2;
							break;
							
						case 3:
							tabela[r][i] = 0;
							keyArray[r] = 3;
							break;
							
						default:
							System.out.println("Op��o inv�lida! Digite novamente.");
						}
					}
					
					
				}
			}
		}
		
		System.out.println("Restri��es: ");
		for(int k = 1; k <= qntRestricao; k++) {
			for(int a = 1; a < tabela[k].length; a++) {
				if (a < qntVarDecisao) {
					System.out.printf("%.0fX%d + ", tabela[k][a], a);
				}else if(a == qntVarDecisao) {
					System.out.printf("%.0fX%d ", tabela[k][a], a);
				}else if(a == tabela[k].length-1) {
					switch (keyArray[k]) {
					case 1:
						System.out.printf(">= %.0f%n", tabela[k][a]);
						break;
					case 2:
						System.out.printf("<= %.0f%n", tabela[k][a]);
						break;
					case 3: 
						System.out.printf("= %.0f%n", tabela[k][a]);
						break;
					}
				}
			}
			
		}
		
		return tabela;
	}
	
	
}
