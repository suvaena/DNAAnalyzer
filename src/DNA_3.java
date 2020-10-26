import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class DNA_3 {

	public static final int MIN_NUM_OF_CODONS = 5;
	public static final double PERCENTAGE_OF_MASS = 30;
	public static final int UNIQUE_NUCLEOTIDES = 4;
	public static final int NUCLEOTIDES_PER_CODON = 3;

	public static void main(String[] args) throws FileNotFoundException {
		readFile();
	}

	public static void readFile() throws FileNotFoundException {
		System.out.print(
				"This program reports information about DNA nucleotide sequences that may encode proteins.\nInput file name? ");
		Scanner in = new Scanner(System.in);
		String inputName = in.next();
		System.out.print("Output file name? ");
		String outputName = in.next();

		File toRead = new File("src/" + inputName);
		Scanner input = new Scanner(toRead);
		String nextToken;
		String nucleotide;
		int[] array = new int[UNIQUE_NUCLEOTIDES];
		System.out.println();

		while (input.hasNextLine()) {
			nextToken = input.nextLine();

		//	String ttoken = nextToken;
		//	PrintStream ps = new PrintStream(new File("src/output.txt"));
		//	ps.print("Region Name: " + ttoken);

			System.out.println("Region Name: " + nextToken);
			nextToken = input.nextLine();
			nucleotide = nextToken;
			String nucleotides = nucleotide;

			readNucleotide(nucleotides, array);

			String codon[] = new String[(nucleotide.length()) / 3];
			String codonName = "";
			System.out.println();
			codon(codon, codonName, nucleotides);
		}
	}

	public static void readNucleotide(String nucleotides, int[] array) throws FileNotFoundException {
		double totalMass = 0;

		System.out.println("Nucleotides: " + nucleotides.toUpperCase());
	//	PrintStream ps = new PrintStream(new File("src/output.txt"));
	//	ps.print("\nNucleotides: " + nucleotides.toUpperCase());

		for (int i = 0; i < nucleotides.length(); i++) {
			if ((nucleotides.charAt(i) == 'A') || (nucleotides.charAt(i) == 'a')) {
				array[0]++;
				totalMass += 135.128;
			} else if ((nucleotides.charAt(i) == 'C') || (nucleotides.charAt(i) == 'c')) {
				array[1]++;
				totalMass += 111.103;
			} else if ((nucleotides.charAt(i) == 'G') || (nucleotides.charAt(i) == 'g')) {
				array[2]++;
				totalMass += 151.128;
			} else if ((nucleotides.charAt(i) == 'T') || (nucleotides.charAt(i) == 't')) {
				array[3]++;
				totalMass += 125.107;
			} else {
				totalMass += 100.000;
			}
		}
		massPercentOfNucleotide(totalMass, array);
	}

	public static void massPercentOfNucleotide(double totalMass, int[] array) {
		System.out.println("Nuc. Counts: " + Arrays.toString(array));

		double[] massArrays = new double[4];
		massArrays[0] = ((array[0] * 135.128) / totalMass) * 100;
		massArrays[1] = ((array[1] * 111.1038) / totalMass) * 100;
		massArrays[2] = ((array[2] * 151.128) / totalMass) * 100;
		massArrays[3] = ((array[0] * 125.107) / totalMass) * 100;

		System.out.print("Total Mass%: [");
		for (int i = 0; i < massArrays.length - 1; i++) {
			System.out.print(Math.round(massArrays[i] * 10) / 10.0 + ", ");
		}
		System.out.print(Math.round(massArrays[massArrays.length - 1] * 10) / 10.0 + "] of "
				+ Math.round(totalMass * 10) / 10.0);

		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
	}
	
	public static double [] array (double[]array)
	{
		return array;
	}

	public static void codon(String[] codon, String codonName, String nucleotides) {
		for (int j = 0; j < codon.length; j++) {
			for (int i = 0; i < NUCLEOTIDES_PER_CODON; i++) {
				if (nucleotides.charAt(i) == '-') {
					nucleotides.charAt(i + 1);
					codonName = codonName + nucleotides.charAt(i + 1);
				} else {
					codonName = codonName + nucleotides.charAt(i);
				}
			}
			codon[j] = codonName.toUpperCase();
			nucleotides = nucleotides.substring(NUCLEOTIDES_PER_CODON, nucleotides.length());
			codonName = "";
		}
		System.out.println("Codons list: " + Arrays.toString(codon) + "\n");
		
		//checkIfProtein (nucleotides, codon, totalMass, massPercents);
	}

	public static void checkIfProtein (String nucleotide, String [] codon, double totalMass, double[]massPercents)
	{
		if (nucleotide.startsWith("ATG") || (nucleotide.startsWith("atg")))
		{
			if ((nucleotide.endsWith("TAA")) || (nucleotide.endsWith("taa")) || (nucleotide.endsWith("TAG")) || (nucleotide.endsWith("tag")) || (nucleotide.endsWith("TGA")) || (nucleotide.endsWith("tga")))
			{
				if (codon.length>= MIN_NUM_OF_CODONS-1)
				{
					double fraction = (massPercents[1] + massPercents[2])/totalMass;
					if (fraction >= ((PERCENTAGE_OF_MASS)/10))
					{
						System.out.println("Is protein?: YES");
					}
				}
			}
		}
		else
		{
			System.out.println("Is protein?: NO");
		}
	}
}
