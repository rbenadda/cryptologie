import java.util.*;

public class Doute{
	
	private ArrayList<ArrayList<Character>> blocs;
	private ArrayList<ArrayList<Integer>> keys;
	private int taille_bloc = 25;
	private int taille_list = 5;
	private int length_mes;
	private int temp_length;
	private int modulo = 64;
	private String mes;

	Doute(){

		// Etape 1 --------- Padding de M ----------------------------//
		Scanner scanner = new Scanner( System.in );
		System.out.println("--------------------------------------");
		System.out.println("Veuillez saisir un message à encrypter");
		System.out.println("--------------------------------------");
		mes = scanner.next();
		length_mes = mes.length();
		mes = mes.concat(" ");
		temp_length = mes.length();
		blocs = new ArrayList<ArrayList<Character>>();
		keys = new ArrayList<ArrayList<Integer>>();
		etapeAB();
		affEtapeAB();
		etapeC();
		affEtapeC();
		etapeD();
		affEtapeD();
		etapeE();
		affEtapeE();
	}


	private void etapeAB(){
		char c = '\0';
		int cpt = 0;
		for(int i=0;i<taille_list;i++){
			ArrayList<Character> temp_char = new ArrayList<Character>();
			for(int j=0;j<taille_bloc;j++){
				if ( cpt < temp_length){
					temp_char.add(mes.charAt(cpt));
				}
				else{
					temp_char.add(c);
				}
				cpt++;
			}
			blocs.add(temp_char);
		}
	}

	private void affEtapeAB(){
		System.out.println("----Etape--AB----");
		for(int i=0;i<blocs.size();i++){
			for(int j=0;j<taille_bloc;j++)
				System.out.println((int)blocs.get(i).get(j));

		}
	}



	private void etapeC(){

		System.out.println("----Etape-C----");
		int decallage = 0;
		for(int i=0;i<taille_list;i++){
			ArrayList<Integer> temp_key = new ArrayList<Integer>();
			
			for (int k=0;k<taille_list;k++){
				int somme = 0;
				for(int j=0;j<taille_bloc;j=j+taille_list){
					System.out.println((int)blocs.get(i).get(j+decallage));
					somme = somme + (int)blocs.get(i).get(j+decallage) ; 
				}
				somme = somme%modulo;
				temp_key.add(somme);
				decallage++;
			}
			keys.add(temp_key);
			decallage = 0;
		}

	}

	private void affEtapeC(){
		System.out.println("----Etape--C----");
		for(int i=0;i<blocs.size();i++){

			System.out.println(keys.get(i));

		}
	}


	private void etapeD(){

		int decallage = 0;
		for(int i=0;i<taille_list;i++){
			for(int k=0;k<taille_bloc;k=k+taille_list){
				for(int l=0;l<decallage;l++){
					Character temp = blocs.get(i).get(k);
					blocs.get(i).add(k+5,temp);
					blocs.get(i).remove(k);
				}
				decallage++;
			}	

		}
	}	
	private void affEtapeD(){
		System.out.println("----Etape--D----");
		for(int i=0;i<blocs.size();i++){
			System.out.println("----Block-:" + i  +"----");
			for(int j=0;j<taille_bloc;j++){
				System.out.println(blocs.get(i).get(j));
			}

		}
	}
	private void etapeE(){
		
		int decallage = 0;
		for(int i=0;i<taille_list;i++){
			
			for (int k=0;k<taille_list;k++){
				int somme = 0;
				for(int j=0;j<taille_bloc;j=j+taille_list){
					somme = somme + (int)blocs.get(i).get(j+decallage) ; 
				}
				int new_key = (somme + keys.get(i).get(k))%modulo;
				keys.get(i).set(k,new_key);
				decallage++;
			}
			decallage = 0;
		}


	}	

	private void affEtapeE(){
		System.out.println("----Etape--E----");
		for(int i=0;i<blocs.size();i++){

			System.out.println(keys.get(i));

		}
	}


	public static void main(String[] args)
	{
		Thh t = new Thh();
	}


/*for(int j=0;j<taille_list;j=j+5){
				somme = somme + (int)blocs.get(i).get(j+decallage);
			}
			decallage++;*/
		}