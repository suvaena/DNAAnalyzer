//Suvaena Laventhiran
//Assignment 8 Q3
//March 26, 2019
//Program reads a file and outputs info about the dna
//Input: file name to read data from and file name to output data to
//Output: outputs DNA data to text file

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class DNA_4 {

	public static final int MIN_NUM_OF_CODONS = 5;
	public static final double PERCENTAGE_OF_MASS = 30;
	public static final int UNIQUE_NUCLEOTIDES = 4;
	public static final int NUCLEOTIDES_PER_CODON = 3;

	public static void main(String[] args) throws FileNotFoundException {
		readFile();
	}

	public static void readFile() throws FileNotFoundException {

		// Get input file name from user
		System.out.print(
				"This program reports information about DNA nucleotide sequences that may encode proteins.\nInput file name? ");
		Scanner in = new Scanner(System.in);
		String inputName = in.next();

		File toRead = new File("src/" + inputName);
		Scanner input = new Scanner(toRead);

		// Get output file name from user
		System.out.print("Output file name? ");
		String outputName = in.next();
		PrintStream ps = new PrintStream(new File("src/" + outputName));

		String nextToken;
		String nucleotide;
		int[] array = new int[UNIQUE_NUCLEOTIDES];

		while (input.hasNextLine()) {

			// Read Region Name
			nextToken = input.nextLine();
			ps.print("Region Name: " + nextToken + "\n");

			// Read Nucleotide name
			nucleotide = input.nextLine();

			nucleotideBreakdown(nucleotide, array, ps);
		}
	}

	public static void nucleotideBreakdown(String nucleotide, int[] array, PrintStream ps)
			throws FileNotFoundException {
		ps.print("Nucleotides: " + nucleotide.toUpperCase() + "\n");

		double totalMass = 0;
		// break up nucleotide into array - sort by letter type
		for (int i = 0; i < nucleotide.length(); i++) {
			if ((nucleotide.charAt(i) == 'A') || (nucleotide.charAt(i) == 'a')) {
				array[0]++;
				totalMass += 135.128;
			} else if ((nucleotide.charAt(i) == 'C') || (nucleotide.charAt(i) == 'c')) {
				array[1]++;
				totalMass += 111.103;
			} else if ((nucleotide.charAt(i) == 'G') || (nucleotide.charAt(i) == 'g')) {
				array[2]++;
				totalMass += 151.128;
			} else if ((nucleotide.charAt(i) == 'T') || (nucleotide.charAt(i) == 't')) {
				array[3]++;
				totalMass += 125.107;
			} else {
				totalMass += 100.000;
			}
		}
		massPercentOfNucleotide(nucleotide, totalMass, array, ps);
	}

	public static void massPercentOfNucleotide(String nucleotide, double totalMass, int[] array, PrintStream ps) {
		ps.print("Nuc. Counts: " + Arrays.toString(array) + "\n");

		double[] massArrays = new double[4];
		// find percent masses of each letter in nucleotide
		massArrays[0] = ((array[0] * 135.128) / totalMass) * 100;
		massArrays[1] = ((array[1] * 111.1038) / totalMass) * 100;
		massArrays[2] = ((array[2] * 151.128) / totalMass) * 100;
		massArrays[3] = ((array[3] * 125.107) / totalMass) * 100;

		ps.print("Total Mass%: [");
		for (int i = 0; i < massArrays.length; i++) {
			ps.print(Math.round(massArrays[i] * 10) / 10.0);
			if (i != massArrays.length - 1) {
				ps.print(", ");
			}
		}
		double roundedTotal = Math.round(totalMass * 10) / 10.0;
		ps.print("] of " + roundedTotal + "\n");

		String codon[] = new String[(nucleotide.length()) / 3];
		codon(codon, nucleotide, roundedTotal, massArrays, ps);

		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
	}

	public static void codon(String[] codon, String nucleotide, double totalMass, double[] massArrays, PrintStream ps) {
		String nucleotideOriginal = nucleotide.toUpperCase();
		// break down codon names
		for (int j = 0; j < codon.length; j++) {
			String codonName = "";
			for (int i = 0; i < NUCLEOTIDES_PER_CODON; i++) {
				if (nucleotide.charAt(i) == '-') {
					nucleotide.charAt(i + 1);
					codonName = codonName + nucleotide.charAt(i + 1);
				} else {
					codonName = codonName + nucleotide.charAt(i);
				}
			}
			// take substring from for loop and place into array
			codon[j] = codonName.toUpperCase();
			nucleotide = nucleotide.substring(NUCLEOTIDES_PER_CODON, nucleotide.length());
		}

		ps.print("Codons list: " + Arrays.toString(codon) + "\n");

		checkIfProtein(nucleotideOriginal, codon, totalMass, massArrays, ps);
	}

	public static void checkIfProtein(String nucleotide, String[] codon, double totalMass, double[] massArrays,
			PrintStream ps) {
		// run through if statements to check if the nucleotide is a protein by checking for various conditions
		if (nucleotide.startsWith("ATG") || (nucleotide.startsWith("atg"))) {
			if ((nucleotide.endsWith("TAA")) || (nucleotide.endsWith("taa")) || (nucleotide.endsWith("TAG"))
					|| (nucleotide.endsWith("tag")) || (nucleotide.endsWith("TGA")) || (nucleotide.endsWith("tga"))) {
				if (codon.length >= MIN_NUM_OF_CODONS) {

					double fraction = (massArrays[1] + massArrays[2]);

					if (fraction >= ((PERCENTAGE_OF_MASS))) {
						ps.print("Is protein?: YES\n\n");
					} else {
						ps.print("Is protein?: NO\n\n");
					}
				} else {
					ps.print("Is protein?: NO\n\n");
				}
			} else {
				ps.print("Is protein?: NO\n\n");
			}
		} else {
			ps.print("Is protein?: NO\n\n");
		}
	}}