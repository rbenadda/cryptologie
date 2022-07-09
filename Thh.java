import java.util.*;

public class Thh{
	
	private ArrayList<Character> blocs; //liste qui va contenir les caractères ascii
	private ArrayList<Integer> keys; // liste qui va contenir les empreintes
	private int taille_max = 1000; //taille maximal du message encrypter
	private int taille_bloc = 100; // taille de la matrice [10][10]
	private int length_mes; // longueur du message entrée par l'utilisateur
	private int taille_list = 10; // taille d'une ligne de matrice
	private int modulo = 256; // valeur sur lequel on veut travailler
	private String mes; // message de l'utilisateur 

	Thh(){

		Scanner scanner = new Scanner(System.in); //initialisation d'un scanner
		//----------affichage----------//
		System.out.println(" 		|--------------------------------------|");
		System.out.println("		|Veuillez saisir un message à encrypter|");
		System.out.println("		|--------------------------------------|");
		System.out.println(); //saut de ligne
		System.out.println(); //saut de ligne
		System.out.print(":");
		//-----------------------------//
		mes = scanner.next(); //récupération de l'entrée saisie par l'utilisateur
		
		if (mes.length() % 100 != 0){ // si la taille du message ne peut pas rentrer dans un/des bloc entier
			mes = mes.concat(" "); // On ajoute ' ' à la fin du message (32 en ascii)
		}
		blocs = new ArrayList<Character>(); //initialisation de la liste de caractères
		keys = new ArrayList<Integer>(); //initialisation de la liste d'empreinte
		etapeAB(); //appelle de fonction
		for(int i=0;i<blocs.size();i=i+taille_bloc){  //tanqu'il reste des blocs
		System.out.println("|------------------------------------------------------------------------------|");
		System.out.println("|------------------------------- Block : " + i/100 + " ------------------------------------|");
		System.out.println("|------------------------------------------------------------------------------|");
			etapeC(i);  //appelle de fonction
			etapeD(i);  //appelle de fonction
			etapeE(i);  //appelle de fonction
		}
	}


	private void etapeAB(){
		char c = '\0'; //initialisation d'un caractère nul '0' en ascii
		int cpt = 0; // initialisation d'un compteur à 0
		for(int i=0;i<taille_max;i++){ //pour i allant de 0 à 1000
			if ( cpt < mes.length()){ // si le compteur est inférrieur à la taille du message
				blocs.add(mes.charAt(cpt)); // On ajoute le caractère à l'index du compteur rentré par l'utilisateur
			}
			cpt++; // Incrémente le compteur
		}
		while(blocs.size() % 100 != 0){ // si le message ne rente pas dans un bloc
			blocs.add(c); // On le bourre avec le caractère nul
		}
		affEtapeAB(); // appelle de l'affichage
	}


	private void affEtapeAB(){
		System.out.println("		|---------------Etape-AB---------------|");
		for(int i=0;i<blocs.size();i++){
			if(i % 100 ==0){ //affichage des blocs
				System.out.println(); //saut de ligne
			}
			if(i % 10 == 0){ //affichage des ligne du bloc
				System.out.println(); //saut de ligne
			}
			System.out.print(" | "+(int)blocs.get(i) );//affichage de la ligne du bloc
		}
		System.out.println(); //saut de ligne
	}



	private void etapeC(int i){
		int decallage = 0; // initialisation d'un entier qui nous sert à choisir la colonne
		while(decallage < 10){ // tanque le decallage est infférieur à 10
			int somme = 0; // initialisation d'une somme
			for(int j=0;j<taille_bloc;j=j+taille_list){ // pour j allant de 0 à la taille d'un bloc (100) en incrémentant de 10
				somme = somme + (int)blocs.get(i+j+decallage); // on somme la colonne courante
			}
			somme = somme%modulo; // On applique le modulo
			if(keys.size()< taille_list){ // si on a pas toutes les cles
				keys.add(somme); // on ajoute le résultat que l'on vient d'obtenir
			}
			else{ //sinon
				//int index = i/taille_bloc; //on récupère l'index de la clé correspondant au calcul de la colonne en question
				int temp = keys.get(decallage); // On récupère l'ancienne valeur de la clef
				keys.set(decallage,somme + temp); // On applique la nouvelle valeur
			}
			decallage++; // Incrémente le décallage
		}
		affEtapeC(); //appelle de l'affichage
	}


	private void affEtapeC(){
		System.out.println(); //saut de ligne
		System.out.println(); //saut de ligne
		System.out.println("		|---------------Etape-C----------------|");
		System.out.println(); //saut de ligne
		System.out.println("empreinte : " + keys);

	}


	private void etapeD(int i){

        int decallage = 0; // initialisation d'un entier qui nous sert à choisir la colonne
            for(int k=0;k<taille_bloc;k=k+taille_list){// pour j allant de 0 à la taille d'un bloc (100) en incrémentant de 10
                for(int l=0;l<decallage;l++){ //pour j allant de 0 au décallage
                    Character temp = blocs.get(i+k+taille_list-1); //stock la valeur déplacée
                    blocs.add(i+k,temp);// on l'ajoute à droite de la dernière valeur de la ligne
                    blocs.remove(i+k+taille_list); //on l'a supprime de son ancienne place
                }
                decallage++; //incrémente le comteur
            }     
        affEtapeD(i); //appelle de l'affichage
    }	

    private void affEtapeD(int index){
		System.out.println();//saut de ligne
		System.out.println("		|---------------Etape-D----------------|");
		for(int i=index;i<index+100;i++){ //parcours de tous les blocs
			if(i % 10 == 0){ //affichage des ligne du bloc
				System.out.println(); //saut de ligne
			}
			System.out.print(" | "+(int)blocs.get(i) ); //affichage de la ligne du bloc
		}
	}

	private void etapeE(int i){ //même fonctionnement que l'étape D

        int decallage = 0; // initialisation d'un entier qui nous sert à choisir la colonne
            while(decallage < 10){ // tanqu'on a pas atteint la fin des colonnes
                int somme = 0; // initialisation d'un entier
                for(int j=0;j<taille_bloc;j=j+taille_list){  // pour j allant de 0 à la taille d'un bloc (100) en incrémentant de 10
                    somme = somme + (int)blocs.get(i+j+decallage) ; // on somme la colonne courante
                }
               // int index = i/taille_bloc; //on récupère l'index de la clé correspondant au calcul de la colonne en question
                int temp = keys.get(decallage); // On récupère l'ancienne valeur de la clef
                int new_key = (somme + temp)%modulo; // on calcul la nouvelle clef
                keys.set(decallage,new_key); // remplace l'ancienne valeur par celle calculée
                decallage++; // incrémentation de la colonne
            }
        affEtapeE();//appelle de l'affichage
    }


    private void affEtapeE(){
		System.out.println(); //saut de ligne
		System.out.println(); //saut de ligne
		System.out.println("		|---------------Etape-E----------------|");
		System.out.println(); //saut de ligne
		System.out.println("empreinte : " + keys);
		System.out.println(); //saut de ligne
	}


	public static void main(String[] args)
	{
		Thh t = new Thh(); //appelle du constructeur 
	}

}