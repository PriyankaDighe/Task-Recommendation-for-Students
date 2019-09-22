package com.tss.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class LSHMinHash<T> {

	private int hashes[];
	private int numOfHashes;
	private int numOfSets;
	private List<T> setA;
	private List<T> setB;
	private Map<T, boolean[]> matrix;
	private int[][] minHashes;

	public LSHMinHash(List<T> setA, List<T> setB) {
		this.numOfHashes = 100;// (int)(1 / (e * e));
		// System.out.println("Size of setA and Set B ::  " + setA.size()
		// +":"+setB.size());
		this.numOfSets = 2;
		this.setA = setA;
		this.setB = setB;
		matrix = buildSetMatrix(setA, setB);
		minHashes = initMinHashes(numOfSets, numOfHashes);
		hashes = computeHashes(numOfHashes);
	}

	private Map<T, boolean[]> buildSetMatrix(List<T> setA, List<T> setB) {

		Map<T, boolean[]> matrix = new HashMap<T, boolean[]>();

		for (T element : setA) {
			matrix.put(element, new boolean[] { true, false });
//			 System.out.println("SetA ELement ::"+element);
		}
		for (T element : setB) {
//			 System.out.println("SetB ELement ::"+element);
			if (matrix.containsKey(element)) {
//				 System.out.println("SetB TRUE ELement ::"+element);
				matrix.put(element, new boolean[] { true, true });
			} else if (!matrix.containsKey(element)) {
//				 System.out.println("SetB FALSE ELement ::"+element);
				matrix.put(element, new boolean[] { false, true });
			}
		}
//		 System.out.println("MAtrix ::+ "+matrix);
		return matrix;
	}

	private int[][] initMinHashes(int numOfSets, int numOfHashes) {
		int[][] minHashes = new int[numOfSets][numOfHashes];

		for (int i = 0; i < numOfSets; i++) {
			for (int j = 0; j < numOfHashes; j++) {
				minHashes[i][j] = Integer.MAX_VALUE;
			}
		}
		return minHashes;
	}

	private int[] computeHashes(int numOfHashes) {
		int[] hashes = new int[numOfHashes];
		Random r = new Random(31);

		for (int i = 0; i < numOfHashes; i++) {
			int a = (int) r.nextInt();
			int b = (int) r.nextInt();
			int c = (int) r.nextInt();
			hashes[i] = (int) ((a * (a * b * c >> 4) + a * a * b * c + c));// &
																			// 0xFFFFFFFF);
		}
		return hashes;
	}

	private void computeMinHashForSet(List<T> set, int setIndex) {
		int hashIndex = 0;

		for (T element : matrix.keySet()) {
			// System.out.println("Element::"+element);
			for (int i = 0; i < numOfHashes; i++) {
				if (set.contains(element)) {
					// System.out.println("Element Found true::"+element);
					int hashValue = hashes[hashIndex];
					if (hashValue < minHashes[setIndex][hashIndex]) {
						minHashes[setIndex][hashIndex] = hashValue;
					}
				}
			}
			hashIndex++;
		}
	}

	private double computeMinHash(int[][] minHashes, int numOfHashes) {
		int identicalMinHashes = 0;
		for (int i = 0; i < numOfHashes; i++) {
			if (minHashes[0][i] == minHashes[1][i]) {
				identicalMinHashes++;
			}
		}
		return (double) (1.0 * identicalMinHashes) / numOfHashes;
	}

	public double findSimilarities() {
		computeMinHashForSet(setA, 0);
		computeMinHashForSet(setB, 1);
		// return computeMinHash(minHashes, numOfHashes);
		return computeMinHashWithLinkedHashSets(minHashes, numOfHashes);
	}

	private double computeMinHashWithLinkedHashSets(int[][] minHashes,int numOfHashes) {
		double score = 0;
		int similarity = 0;
		Set<T> unionSet = new LinkedHashSet<T>();
		unionSet.addAll(setA);
		unionSet.addAll(setB);
		List<T> listA = new ArrayList<T>(setA);
		List<T> listB = new ArrayList<T>(setB);
		
		for(int j= 0;j<listA.size();j++){
			// Compare GRE Range
//			if(j==0){
//				String elementA = (String) listA.get(j);
//				String elementB = (String) listB.get(j);
//				String[] greRange = elementB.split("-");
//				int greMin = Integer.parseInt(greRange[0]);
//				int greMax = Integer.parseInt(greRange[1]);
//				int greStud = Integer.parseInt(elementA);
//				if((greStud >=greMin) && (greStud<=greMax)){
//					similarity++;
//				}
//			}
//			// ielts
//			if(j==1){
//				String elementA = (String) listA.get(j);
//				String elementB = (String) listB.get(j);
//				Float studIelts = Float.parseFloat(elementA);
//				Float univIelts = Float.parseFloat(elementB);
//				if(studIelts>univIelts){
//					similarity++;
//				}
//			}
//			// tofel
//			if(j==2){
//				String elementA = (String) listA.get(j);
//				String elementB = (String) listB.get(j);
//				Float studtofel = Float.parseFloat(elementA);
//				Float univtofel = Float.parseFloat(elementB);
//				if(studtofel>univtofel){
//					similarity++;
//				}
//			}
//			// budget
//			if(j==4){
//				String elementA = (String) listA.get(j);
//				String elementB = (String) listB.get(j);
//				Float studBudget = Float.parseFloat(elementA);
//				Float univBudget = Float.parseFloat(elementB);
//				if(studBudget>univBudget){
//					similarity++;
//				}
//			}
			String elementA = (String) listA.get(j);
			String elementB = (String) listB.get(j);
			if(elementA.equals(elementB)){
				similarity++;
			}
		}
		
		/*for (int i = 4; i < listA.size(); i++) {
			String elementA = (String) listA.get(i);
			if (listB.contains(elementA)) {
				int k = listB.indexOf(elementA);
				similarity++;
				if(i< listA.size()-1){
				String tempEl = (String) listA.get(++i);
			//	System.out.println("List B"+listB.size()); 
			//	System.out.println("K "+k);
				if (k < listB.size()-1) {
					if (tempEl.equals(listB.get(++k))) {
						similarity++;
					}
				}
				}
			}
		}*/
                //1.988 * 
		score = (double) (similarity) / unionSet.size();
//		System.out.println("Union Size::"+ unionSet.size());
		return score;
	}

}
