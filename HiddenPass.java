package hiddenPass;

import java.io.*;
import java.lang.Math;

public class HiddenPass {

	public static void main(String[] args) {
		
		try{
			FileReader fr = new FileReader("d://teste.txt"); /**Localização do arquivo*/
			BufferedReader bf = new BufferedReader(fr);
			
			String senha = ""; /**String de saída que exibe a senha*/
			String linha = bf.readLine(); /**String de leitura do arquivo*/
			
			char num[] = new char[3];
			int arrA[] = new int[6]; /**Array para armazenar os resultados dos 6 bits lidos para o valor a(i-esimo caractere) da 6-tupla --- Posteriormente sera convertido o valor em Decimal*/
			int arrB[] = new int[6]; /**Array para armazenar os resultados dos 6 bits lidos para o valor b(a + 3 % 6)-esimo caractere da 6-tupla --- Posteriormente sera convertido o valor em Decimal*/
			int nCases, n6t, n;
			
			n = linha.length(); /**Comprimento da String*/
			linha.getChars(0, n, num, 0); /**O metodo getChars pega cada char contidos na String e o armazena em um array de char*/
			nCases = charToInt(num); /**Método usado para converter uma sequencia numerica de char em seu inteiro respectivo*/
			System.out.println("numero de cases: " + nCases);
			linha = null;
			
			if(nCases >= 100){ /**O numero de casos testes não pode ser maior ou igual a 100*/
				System.out.println("Valor inválido!");
				bf.close();
				fr.close();
				return;
			}
			
			for(int i = 0; i < nCases; i++){
				
				int nTupla = 0; /**n-esima 6-tupla para ser lida*/
				
				linha = bf.readLine();
				n = linha.length();
				linha.getChars(0, n, num, 0);
				n6t = charToInt(num); /**n6t (number of 6-tuple) ---> numero total de 6-tuplas (sequencia de 6 caracteres)*/
				System.out.println("numero de 6-tupla: " + n6t);				
				
				for(int j = 0; j < n6t; j++){ /**Leitura das sequencias de 6 caracteres*/
					
					int a = 0, b; /**a ---> a medida que vai lendo a sequencia, vai-se incrementado o 'a' que corresponde ao i-esimo caractere
					 				b ---> corresponde ao ((a + 3) % 6)-esimo caractere da sequencia*/
					
					for(int k = 0; k < 6; k++){ /**Leitura dos 6 caracteres da 6-tupla*/
						
						int c = bf.read(); /**Leitura de cada caractere*/
						int aux = c; /**variavel auxiliar para nao perder o valor lido*/
						
						c >>>= a; /**Utilizado para se obter o bit correspondente em relação ao valor a*/
						c &= 1; /**Considera-se apenas esse bit*/
						arrA[k] = c; /**Armazena no array para determinar o valor de a*/
						
						b = (a + 3) % 6;
						c = aux;
						c >>>= b;
						c &= 1;
						arrB[k] = c;
						
						a++;
					}
					
					bf.read(); /**Por definiçao as 6-tuplas sao separadas por " " (espaco), logo ele será ignorado*/
					bf.mark(64 + (n6t * 7)); /**Marca a posição de leitura na Stream, para posteriormente ser chamado o metodo reset() que retornará desse ponto demarcado*/
					bf.readLine();/**Lê o restante da linha para, em seguida, começar a leitura da segunda String*/
					
					nTupla++; /**Incrementa a nTupla, pois foi lido uma das 6-tuplas*/
					
					int numA = 0, numB = 0;
					a = binToDec(arrA); /**Transforma o valor armazenado no array (na base binária) em um decimal correspondente*/
	
					for(int p = 0; p <= a; p++){ /**Percorre a segunda String até a posicao a (começa a contagem a partir do 0)*/
						numA = bf.read();
					}

					senha += (char) numA; /**Adiciona o caractere a senha*/
	
					b = binToDec(arrB); /**Mesmo processo descrito anteriormente*/
					System.out.println("a = " + a + "\nb = " + b + "\n");
					bf.reset();
					
					bf.readLine();
					
					for(int p = 0; p <= b; p++){
						numB = bf.read();
					}
					
					senha += (char) numB;
					
					int z = n6t - nTupla; /**If para determinar se retorna ao local marcado no BufferedReader ou conclui a linha da segunda String determinando o fim desse caso teste*/
					if(z != 0)
						bf.reset();
					else
						bf.readLine();
				}
				
				bf.readLine(); /**Os casos testes são separados por uma linha em branco*/
				senha += "\n";
			}
			
			System.out.println("senha:\n" + senha); /**Exibe a senha*/
			
			bf.close();
			fr.close();
			
		}catch(IOException ioe){
			ioe.printStackTrace();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public static int asciiToInt(char n){ /**Converte caracteres numericos em seus respectivos valores inteiros*/
		switch(n){
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		default:
			return -1;
		}
	}
	
	public static int binToDec(int []bin){ /**Converte binário para decimal*/
		int dec = 0;
		int i;
		
		for(i = 0; i < bin.length; i++){
			dec += Math.pow(2, i) * bin[i];
		}
		
		return dec;
	}
	
	public static int charToInt(char n[]){ /**Converte char para inteiro*/
		int aux = 0, num = 0;
		
		for(int i = 0; i < n.length; i++){
			
			if(i == 0){
				aux = asciiToInt(n[i]);
				if(aux != -1)
					num += aux;
				continue;
			}
			
			if(i == 1){
				aux = asciiToInt(n[i]);
				if(aux != -1)
					num = (num * 10) + aux;
				else
					break;
			}
			
			if(i == 2)
				aux = asciiToInt(n[i]);
				if(aux != -1)
					num = (num * 100) + aux;
		}
		
		if(num > 100){
			return 0;
		}else{
			return num;
		}
	}

}
