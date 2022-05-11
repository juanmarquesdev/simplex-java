
import java.util.Scanner;

public class Simplex {

	public static Scanner sc = new Scanner(System.in);
	public static int linha;
	public static int coluna;
	
	
	public static void main(String[] args) {
		
		int qntVarDecisao;
		int qntRestricao;
		double[][] tabela;
		
		System.out.println("Quantas variaveis de decisão tem o problema? ");
		qntVarDecisao = Integer.parseInt(sc.nextLine());
		
		System.out.println("Quantas restrições? ");
		qntRestricao = Integer.parseInt(sc.nextLine());
		
		linha = qntRestricao + 1;
		coluna = linha + qntVarDecisao + 1;
		
		tabela = new double[linha][coluna];
		
		gerarTabela(tabela);
		funcaoObjetivo(tabela, qntVarDecisao);
		gerarRestricoes(tabela, qntRestricao, qntVarDecisao);
		printTabela(tabela, linha, coluna, qntVarDecisao, qntRestricao);
		printResultados(tabela, linha, coluna, qntVarDecisao, qntRestricao);
		
		System.out.println();
		
		while(colunaPivot(tabela, coluna) != 0) {
			novaLinhaPivot(tabela, linhaPivot(tabela, linha, coluna, colunaPivot(tabela, coluna)), colunaPivot(tabela, coluna));
			novasLinhas(tabela, colunaPivot(tabela, coluna));
			printTabela(tabela, linha, coluna, qntVarDecisao, qntRestricao);
			printResultados(tabela, linha, coluna, qntVarDecisao, qntRestricao);
			System.out.println();
		}
		
		
	}
	
	public static void printResultados(double[][] tabela, int linha, int coluna, int qntVarDecisao, int qntRestricao) {
		
		int index = 0;
		int qntZero = 0;
		int qntUm = 0;
		
		System.out.printf("Z = %.2f%n", tabela[0][coluna-1]);
		
		for (int c = 1; c < coluna-1; c++) {
			for (int l = 0; l < linha; l++) {
				if (tabela[l][c] == 0) {
					qntZero++;
					
				} else if(tabela[l][c] == 1) {
					qntUm++;
					index = l;
				}
			}
			if(qntUm == 1 && qntZero == qntRestricao) {
				if(c <= qntVarDecisao) {
					System.out.printf("X%d = %.2f%n", c, tabela[index][coluna-1]);
					
				} else if( c > qntVarDecisao && c < coluna -1) {
					System.out.printf("F%d = %.2f%n", c - qntVarDecisao, tabela[index][coluna-1]);
				}
			}
			
			index = 0;
			qntZero = 0;
			qntUm = 0;
			
		}
		
		
	}
	
	public static double[][] novasLinhas(double[][] tabela, int colunaPivot) {
		
		int linhaPivot = linhaPivot(tabela, linha, coluna, colunaPivot(tabela, coluna));
		double elementoDaLinha;
		double[] novaLinhaPivotCalculo = new double[coluna];
		
		for (int i = 0; i < tabela.length; i++) {
			if(i != linhaPivot) {
				elementoDaLinha = tabela[i][colunaPivot]; 
				for(int n = 0; n < novaLinhaPivotCalculo.length; n++ ) {
					novaLinhaPivotCalculo[n] = tabela[linhaPivot][n] * elementoDaLinha * (-1);
					tabela[i][n] = tabela[i][n] + novaLinhaPivotCalculo[n];
				}
				
			}
		}
		
		
		return tabela;
	}
	
	public static double[][] novaLinhaPivot(double[][] tabela, int linhaPivot, int colunaPivot) {
		
		double elementoPivot = tabela[linhaPivot][colunaPivot];
		
		for(int i = 0; i < tabela[linhaPivot].length ; i++) {
			tabela[linhaPivot][i] = tabela[linhaPivot][i] / elementoPivot;
		}
		
		return tabela;
	}	
	
	public static double[][] gerarTabela(double[][] tabela) {
		
		for(int i = 0; i < tabela.length; i++) {
			for(int n = 0; n < tabela[0].length; n++) {
				tabela[i][n] = 0;
			}
		}
		
		return tabela;
	}
	
	public static void printTabela(double[][] tabela, int linha, int coluna, int qntVarDecisao, int qntRestricao) {
		System.out.printf("\tZ");
		
		for(int f = 1; f <= qntVarDecisao; f++) {
			System.out.printf("\tX%d", f);
		}
		
		for(int g = 1; g <= qntRestricao; g++) {
			System.out.printf("\tF%d", g);
		}
		System.out.printf("\tB");
		System.out.println();
		
		for(int i = 0; i < linha; i++) {
			System.out.printf("L%d", i + 1);
			for(int c = 0; c < coluna; c++) {
				System.out.printf("\t%.1f", tabela[i][c]);
			}
			System.out.println();
		}
	}
	
	public static double[][] funcaoObjetivo(double[][] tabela, int qntVarDecisao) {
		
		int key = 0;
		
		System.out.println("A função é para: ");
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
				System.out.println("Opção inválida! Digite novamente.");
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
		
		
		
		System.out.print("Função objetivo: Z = ");
		
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
					
					System.out.println("O resultado é: ");
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
							System.out.println("Opção inválida! Digite novamente.");
						}
					}
					
					
				}
			}
		}
		
		System.out.println("Restrições: ");
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
	
	public static int colunaPivot(double[][] tabela, int coluna) {
		
		double value = 0;
		int colunaPivot = 0;
		
		for(int i = 0; i < coluna; i++) {
			if(tabela[0][i] < value) {
				value = tabela[0][i];
				colunaPivot = i;
			}
		}

		return colunaPivot;
	} 

	public static int linhaPivot(double[][] tabela, int linha, int coluna, int colunaPivot) {
		int linhaPivot = 0;
		
		double resultado = 999999999;
		
		for(int i = 1; i < linha; i++) {
			if(tabela[1][coluna -1] != 0 && tabela[1][colunaPivot] != 0) {
				if(tabela[i][coluna -1] / tabela[i][colunaPivot] < resultado) {
					resultado = tabela[i][coluna -1] / tabela[i][colunaPivot];
					linhaPivot = i;
				}
			}
			
		}
		
		
		return linhaPivot;
	}

}
