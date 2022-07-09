import java.util.*;
import java.awt.*;

public class Hachage{
	static ArrayList<Integer> messageAscii = new ArrayList<Integer>();
	static int [] empreinte = new int[10];
	static int [][] matriceWork = new int [10][10];
	static int [][] matriceSave = new int [10][10];
	static int cpt=0, i=0, j=0;

	public static void init(int [] empreinte, ArrayList<Integer> message){
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez le message que vous voulez encoder:");
		String str = sc.nextLine();
		for(int e=0; e<10; e++){ //initialise toutes les valeurs de l'empreinte à 0
			empreinte[e]=0;
		}
		for (char c : str.toCharArray()) {
  			message.add((int)c); //convertie les charactères de l'entrée en code ascii
		}
	}

	public static void padding(ArrayList<Integer> message){
		if (message.size() % 100 != 0){ //si le message n'est pas un multiple de 100
			message.add(32); //ajoute 32 à la fin de celui-ci
			while (message.size() % 100 != 0){ //et jusqu'à obtenir un multiple de 100
				message.add(0); //ajoute des 0
			}
		}
	}

	public static void arrangementMatriciel(ArrayList<Integer> message, int [][] matriceW, int [][] matriceS){
		for (i=0; i<10; i++){ //passage du message dans une matrice
			for(j=0; j<10; j++){
				matriceW[i][j]=message.get(cpt); //met les charactères dans la matrice de travaille
				matriceS[i][j]=message.get(cpt); //met les charactères dans la matrice de sauvegarde
				cpt++;
			}
		}
	}

	public static void calculEmpreinte(int [][] matriceW, int [][] matriceS, int [] empreinte){
		//CALCUL DE L'EMPREINTE POUR LE BLOC EN COURS - ETAPE 1
		sommeColonne(matriceW, empreinte);
		//CALCUL DE L'EMPREINTE POUR LE BLOC EN COURS - ETAPE 2
		for (i=0; i<10; i++){ //passage du message dans une matrice
			j=0;
			while (j<10){
				if(j+i<10){
					matriceW[i][j%10] = matriceW[i][(j+i)%10]; //prend la valeur avec un pas de i fois à gauche
				}
				else{
					matriceW[i][j%10] = matriceS[i][(j+i)%10]; //prend la valeur avec un pas de i fois à gauche du tableau inchangé
				}
				j++;
			}
		}
		//CALCUL DE L'EMPREINTE POUR LE BLOC EN COURS - ETAPE 3
		sommeColonne(matriceW, empreinte);
	}

	public static void sommeColonne(int [][] matriceW, int [] empreinte){
		for(i=0; i<10; i++){ //additione toutes les valeurs d'une colonne ainsi que la valeur de l'empreinte courante
			empreinte[i] = (empreinte[i]+matriceW[0][i]+matriceW[1][i]+matriceW[2][i]+matriceW[3][i]+matriceW[4][i]+matriceW[5][i]+matriceW[6][i]+matriceW[7][i]+matriceW[8][i]+matriceW[9][i]) % 256;
		}
	}

	public static void affichageMatrice(int [][] matrice){
		int k=0, l=0, cmp=0;
		System.out.println("Matrice:");
		for(k=0; k<10; k++){
			for(l=0; l<10; l++){
				System.out.print("|" + matrice[k][l]);
				cmp++;
				if (cmp%10 == 0) {
					System.out.println("\n-------------------------------------");
				}
			}
		}
	}

	public static void affichageEmpreinte(int [] empreinte){
		System.out.print("empreinte = ");
		for(i=0; i<10; i++){
			System.out.print(" | " + empreinte[i]);
		}
		System.out.println(" |");
	}

	public static void main(String[] args) {
		init(empreinte, messageAscii);
		padding(messageAscii);
		while(cpt<messageAscii.size()){
			arrangementMatriciel(messageAscii, matriceWork, matriceSave);
			affichageMatrice(matriceWork); //enlever le commentaire pour voir la matrice avant le decallage circulaire
			calculEmpreinte(matriceWork, matriceSave, empreinte);
			affichageMatrice(matriceWork); //enlever le commentaire pour voir la matrice après le decallage circulaire
		}
		affichageEmpreinte(empreinte);
	}
}