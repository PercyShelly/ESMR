import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class PaperAuthorEmbedding{
	static String linuxpath = "";//"//home//ubuntu//Desktop//recommendation_system//java//program//xcjprogram//";

	public static void  main(String args[]){
		int embeddingLength=25;
		int negativeRatio=5;
		double learningRateInit=1.0;
		double embeddingInit=0.1;


		Map<Set<String>, Double> paperCitationMap= new HashMap<Set<String>, Double>();
		Map<Set<String>, Double> paperWordMap= new HashMap<Set<String>, Double>();
		Map<Set<String>, Double> paperTopicMap= new HashMap<Set<String>, Double>();
		Map<Set<String>, Double> wordTopicMap= new HashMap<Set<String>, Double>();
		Map<Set<String>, Double> coauthorMap = new HashMap<Set<String>,Double>();
		Map<Set<String>, Double> paperAuthorMap= new HashMap<Set<String>, Double>();

		ArrayList<String> paperList= new ArrayList<String>();
		ArrayList<String> authorList=new ArrayList<String>();
		ArrayList<String> wordList= new ArrayList<String>();
		ArrayList<String> topicList=new ArrayList<String>();

		Map<String,double[]> paperEmbedding;
		Map<String,double[]> paperEmbedding_context;
		Map<String,double[]> authorEmbedding;
		Map<String,double[]> authorEmbedding_context;
		Map<String,double[]> wordEmbedding;
		Map<String,double[]> topicEmbedding;


		System.out.println("******Author Network Embedding******");

		// coauthorMap=readData("D:/data/New/DataProcessing/a_a_weight_last.txt");
		// authorList=readList("D:/data/New/DataProcessing/authorlist.txt","A");
		coauthorMap=readData(linuxpath+"data/a-a(remaindouble).txt");
		authorList=readList(linuxpath+"data/a-p(remain).txt");
		paperCitationMap=readData(linuxpath+"data/p-p(remain).txt");
		paperWordMap=readData(linuxpath+"data/p-w(remain).txt");
		paperTopicMap=readData(linuxpath+"data/p-t(remain).txt");
		//wordTopicMap=readData("D:/hjn/weused/wt-weight.txt");
		paperList=readList(linuxpath+"data/paperlist.txt");
		wordList=readList(linuxpath+"data/wlist.txt");
		//topicList=readList("D:/hjn/aan/processdata/topiclist.txt");
		for(int i=0;i<30;i++){
			topicList.add("t"+i);
		}
//		coauthorMap=readData("D:/hjn/reprocessed/Dataset/a-a(double).txt");
//		authorList=readList("D:/hjn/reprocessed/Dataset/authlist.txt");
//		
//		paperCitationMap=readData("D:/hjn/reprocessed/Dataset/p-p(contain_inoutdegree).txt");
//		paperWordMap=readData("D:/hjn/reprocessed/Dataset/pw-weight.txt");
//		paperTopicMap=readData("D:/hjn/reprocessed/Dataset/pt-weight.txt");
//		//wordTopicMap=readData("D:/hjn/reprocessed/Dataset/wt-weight.txt");
//		paperList=readList("D:/hjn/reprocessed/Dataset/paperlist(remain).txt");
//		wordList=readList("D:/hjn/reprocessed/Dataset/wlist.txt");
//		topicList=readList("D:/hjn/reprocessed/Dataset/topiclist.txt");
		System.out.println(paperList.size());
		System.out.println(wordList.size());
		System.out.println(topicList.size());

		//authorEmbedding= authorNetworkEmbedding(coauthorMap, authorList, embeddingLength, embeddingInit, learningRateInit, negativeRatio);

		ArrayList<Map<String,double[]>> mapList = authorNetworkEmbeddingList(coauthorMap,authorList,embeddingLength,embeddingInit,learningRateInit,negativeRatio);
		authorEmbedding=mapList.get(0);
		authorEmbedding_context= mapList.get(1);

		System.out.println("******Paper Network Embedding******");
		// paperCitationMap=readData("D:/data/New/DataProcessing/paper-paper-weight(G).txt");
		// paperWordMap=readData("D:/data/New/DataProcessing/pw_test.txt");
		// paperTopicMap=readData("D:/data/New/DataProcessing/paper-topic-weight.txt");
		// wordTopicMap=readData("D:/data/New/DataProcessing/wt_test.txt");
		// paperList=readList("D:/data/New/DataProcessing/paperlist.txt","p");
		// wordList=preadList("D:/data/New/DataProcessing/wordlist.txt");
		// for(int i=0;i<20;i++){
		// 	topicList.add("T"+i);
		// }
		

		// Map<String,double[]> authorEmbedding = initEmbedding(embeddingLength,embeddingInit,authorList);
		// //System.out.println(authorList.size());
		// Map<String,double[]> authorEmbedding_context = initEmbedding(embeddingLength,embeddingInit,authorList);
		// Map<String,double[]> paperEmbedding=initEmbedding(embeddingLength,embeddingInit,paperList);
		// Map<String,double[]> paperEmbedding_context= initEmbedding(embeddingLength,embeddingInit,paperList);
		// Map<String,double[]> wordEmbedding= initEmbedding(embeddingLength,embeddingInit,wordList);
		// Map<String,double[]> topicEmbedding= initEmbedding(embeddingLength,embeddingInit,topicList);

		// paperEmbedding= paperNetworkEmbedding(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,paperList,wordList,topicList,
		// 	embeddingLength,embeddingInit,learningRateInit, negativeRatio);
		ArrayList<Map<String,double[]>> mapListpaper=new ArrayList<Map<String,double[]>>();
		mapListpaper =paperNetworkEmbeddingList(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap, paperList, wordList,
				topicList,embeddingLength,embeddingInit,learningRateInit,negativeRatio);
		paperEmbedding= mapListpaper.get(0);
		paperEmbedding_context = mapListpaper.get(1);
		wordEmbedding = mapListpaper.get(2);
		topicEmbedding = mapListpaper.get(3);

		//paperAuthorMap=readData("D:/hjn/reprocessed/Dataset/a-p-weight.txt");
		paperAuthorMap=readData(linuxpath+"data/a-p(remain).txt");
		//paperAuthorMap=readData("D:/data/New/DataProcessing/author-paper-weight.txt");
		System.out.println("******Global Network Embedding******");
		globalNetworkEmbedding(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingLength,embeddingInit,learningRateInit,negativeRatio);


	}
	public static Map<Set<String>,Double> readData(String filePath) {
		Map<Set<String>,Double> map=new HashMap<Set<String>,Double>();
		File file=new File(filePath);
		try{
			BufferedReader br= new BufferedReader(new FileReader(file));
			String line=null;
			while((line=br.readLine())!=null){
				String[] array=line.split(",");
				Set<String> edge= new LinkedHashSet<String>();
				if(!array[0].equals(array[1])&&array.length>=3){
					edge.add(array[0]);
					edge.add(array[1]);
					//System.out.println(array[0]+","+array[1]);
					double weight=Double.valueOf(array[2]).doubleValue();
					map.put(edge,weight);
				}
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return map;
	}
	public static ArrayList<String> readList(String filePath){
		ArrayList<String> array=new ArrayList<String>();
		File file= new File(filePath);
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line=br.readLine();
			while(line!=null){
				String[] str=line.split(",");
				array.add(str[0]);
				line=br.readLine();
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return array;
	}
	public static ArrayList<String> preadList(String filePath){
		ArrayList<String> array=new ArrayList<String>();
		File file= new File(filePath);
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			String line=br.readLine();
			while(line!=null){
				//String[] str=line.split("_");
				array.add(line);
				line=br.readLine();
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return array;
	}

	public static void globalNetworkEmbedding(Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double> paperWordMap,Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double>wordTopicMap,
		Map<Set<String>,Double> coauthorMap,Map<Set<String>,Double> paperAuthorMap,ArrayList<String> paperList,ArrayList<String> wordList,ArrayList<String> topicList,ArrayList<String> authorList,
		Map<String,double[]> paperEmbedding,Map<String,double[]> paperEmbedding_context,Map<String,double[]> wordEmbedding,Map<String,double[]>topicEmbedding,Map<String,double[]>authorEmbedding,
		Map<String,double[]> authorEmbedding_context,int embeddingLength,double embeddingInit,double learningRateInit,int negativeRatio){

		//Random rand=new Random(System.currentTimeMillis());
		double[][] embeddingMatrix=new double[embeddingLength][embeddingLength];
		for(int i=0;i<embeddingMatrix.length;i++){
			for(int j=0;j<embeddingMatrix[i].length;j++){
				embeddingMatrix[i][j]=0.0;
			}
		}
		double lastLoss= getGlobalNetworkLoss(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,negativeRatio);
		System.out.println("last Loss="+lastLoss);
		boolean paperConverge=false;
		boolean authorConverge=false;
		boolean matrixConverge=false;
		long startTime=System.currentTimeMillis();
		double globalLoss=lastLoss;
		Map<String,Integer> converge=new HashMap<String,Integer>();
		int all=1;
		while(true){
			int mc=1;
			while(!matrixConverge){
				System.out.println("Embedding Matrix Learning");
				double currentLoss= doubleNetworkMatrixEmbeddingLearning(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,learningRateInit,negativeRatio);
				System.out.println("current Loss="+currentLoss);
				System.out.println(Integer.toString(mc)+","+Integer.toString(all));
				mc++;
				if(Math.abs(currentLoss- lastLoss)/lastLoss<=0.01||mc>3){
					matrixConverge=true;
				}
				if(currentLoss<lastLoss){
					lastLoss=currentLoss;
				}
			}
			matrixConverge=false;
			if(Math.abs(globalLoss - lastLoss)/globalLoss <=0.01||all>5){
				if(!converge.containsKey("Matrix")){
					converge.put("Matrix",1);
				}else{
					converge.put("Matrix",2);
				}
				if(overallConverge(converge)){
					break;
				}
			}else{
					converge.remove("Matrix");
			}
			globalLoss=lastLoss;
			System.out.println("last Loss= "+globalLoss);

			int ac=1;
			while(!authorConverge){
				System.out.println("Author Embedding Learning");
				double currentLoss=doubleNetworkAuthorEmbeddingLearning(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,learningRateInit,negativeRatio);
				System.out.println("current Loss="+currentLoss);
				System.out.println(Integer.toString(ac)+","+Integer.toString(all));
				ac++;
				if(Math.abs(currentLoss- lastLoss)/lastLoss<=0.01||ac>8){
					authorConverge=true;
				}
				if(currentLoss<lastLoss){
					lastLoss=currentLoss;
				}
			}
			authorConverge=false;
			if(Math.abs(globalLoss - lastLoss)/globalLoss <=0.01||all>5){
				if(!converge.containsKey("Author")){
					converge.put("Author",1);
				}else{
					converge.put("Author",2);
				}
				if(overallConverge(converge)){
					break;
				}
			}else{
					converge.remove("Author");
			}
			globalLoss=lastLoss;
			System.out.println("last Loss="+ globalLoss);

			int pc=1;
			while(!paperConverge){
				System.out.println("Paper Embedding Learing");
				double currentLoss= doubleNetworkPaperEmbeddingLearning(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,learningRateInit,negativeRatio);
				System.out.println("current Loss="+currentLoss);
				pc++;
				if(Math.abs(currentLoss- lastLoss)/lastLoss<=0.01||pc>8){
					paperConverge=true;
				}
				if(currentLoss<lastLoss){
					lastLoss=currentLoss;
				}
				System.out.println(Integer.toString(pc)+","+Integer.toString(all));
			}
			paperConverge=false;
			if(Math.abs(globalLoss - lastLoss)/globalLoss <=0.01||all>5){
				if(!converge.containsKey("Paper")){
					converge.put("Paper",1);
				}else{
					converge.put("Paper",2);
				}
				if(overallConverge(converge)){
					break;
				}
			}else{
					converge.remove("Paper");
			}
			globalLoss=lastLoss;
			System.out.println("last Loss="+globalLoss);
			all++;

		}
		double sparsityNum = 0.0;
		for (int i = 0; i < embeddingMatrix.length; i++) {
			for (int j = 0; j < embeddingMatrix[i].length; j++) {
				if (embeddingMatrix[i][j] == 0.0) {
					sparsityNum += 1.0;
				}
			}
		}
		System.out.println("sparsity ratio:" + sparsityNum + "/" + embeddingLength * embeddingLength + "="
				+ sparsityNum / (double) (Math.pow(embeddingLength, 2)));
		long endTime = System.currentTimeMillis();
		System.out.println("time difference=" + (endTime - startTime) / 1000);

		String globalAuthorEmbeddingfile=linuxpath+"author(NECoRank-T).txt";
		File fileGlobalEmbedding= new File(globalAuthorEmbeddingfile);
		FileWriter fwGlobalEmbedding;
		try{
			fwGlobalEmbedding=new FileWriter(fileGlobalEmbedding);
			BufferedWriter bwGlobalEmbedding=new BufferedWriter(fwGlobalEmbedding);
			for(String author:authorEmbedding.keySet()){
				bwGlobalEmbedding.write(author+",");
				double[] embedding = authorEmbedding.get(author);
				for(int i=0;i<embedding.length;i++){
					bwGlobalEmbedding.write(String.valueOf(embedding[i])+",");
				}
				bwGlobalEmbedding.newLine();
			}
			bwGlobalEmbedding.flush();
			bwGlobalEmbedding.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		String globalPaperEmbeddingfile=linuxpath+"paper(NECoRank-T).txt";
		File fileGlobalPaperEmbedding= new File(globalPaperEmbeddingfile);
		FileWriter fwGlobalPaperEmbedding;
		try{
			fwGlobalPaperEmbedding=new FileWriter(fileGlobalPaperEmbedding);
			BufferedWriter bwGlobalEmbedding=new BufferedWriter(fwGlobalPaperEmbedding);
			for(String paper:paperEmbedding.keySet()){
				bwGlobalEmbedding.write(paper+",");
				double[] embedding = paperEmbedding.get(paper);
				for(int i=0;i<embedding.length;i++){
					bwGlobalEmbedding.write(String.valueOf(embedding[i])+",");
				}
				bwGlobalEmbedding.newLine();
			}
			bwGlobalEmbedding.flush();
			bwGlobalEmbedding.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static Map<String,double[]> paperNetworkEmbedding(Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double> paperWordMap,
		Map<Set<String>,Double> paperTopicMap,Map<Set<String>,Double> wordTopicMap,ArrayList<String> paperList,ArrayList<String> wordList,
		ArrayList<String> topicList, int embeddingLength, double embeddingInit, double learningRateInit,int negativeRatio){

		Map<String,double[]> paperEmbedding=initEmbedding(embeddingLength,embeddingInit,paperList);
		Map<String,double[]> paperEmbedding_context= initEmbedding(embeddingLength,embeddingInit,paperList);
		Map<String,double[]> wordEmbedding= initEmbedding(embeddingLength,embeddingInit,wordList);
		Map<String,double[]> topicEmbedding= initEmbedding(embeddingLength,embeddingInit,topicList);

		double lastLoss= getPaperNetworkLoss(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,paperList,wordList,topicList,paperEmbedding,paperEmbedding_context,wordEmbedding,topicEmbedding,negativeRatio);
		System.out.println("last loss= "+ lastLoss);
		boolean converge=false;
		long startTime=System.currentTimeMillis();
		int convergetime=1;
		while(!converge){
			double currentLoss= paperNetworkEmbeddingLearning(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,paperList,wordList,topicList,paperEmbedding,paperEmbedding_context,wordEmbedding,topicEmbedding,
				learningRateInit,negativeRatio);
			System.out.println("current loss=" + currentLoss);
			convergetime++;
			if(Math.abs(currentLoss- lastLoss)/lastLoss<=0.01||convergetime>20){
				converge= true;
			}else{
				lastLoss= currentLoss;
			}
		}
		long endTime= System.currentTimeMillis();
		System.out.println("time difference =" +(endTime - startTime)/1000);
		String paperEmbeddingFile =linuxpath+"single_paper(NECoRank-T).txt";
		File paperEmbeddingfile= new File(paperEmbeddingFile);
		FileWriter fwPaperEmbedding;
		try{
			fwPaperEmbedding= new FileWriter(paperEmbeddingfile.getAbsoluteFile());
			BufferedWriter bwPaperEmbedding = new BufferedWriter(fwPaperEmbedding);
			for(String sequence: paperEmbedding.keySet()){
				bwPaperEmbedding.write(sequence+",");
				double[] embedding = paperEmbedding.get(sequence);
				for(int i= 0; i<embedding.length;i++){
					bwPaperEmbedding.write(String.valueOf(embedding[i]+","));
				}
				bwPaperEmbedding.newLine();
			}
			bwPaperEmbedding.flush();
			bwPaperEmbedding.close();
		}catch(IOException e){
			e.printStackTrace();
		}


		return paperEmbedding;
	}

	public static Map<String,double[]> authorNetworkEmbedding(Map<Set<String>,Double> coauthorMap, ArrayList<String> authorList,int embeddingLength, double embeddingInit, double learningRateInit, int negativeRatio){

		Map<String,double[]> authorEmbedding = initEmbedding(embeddingLength,embeddingInit,authorList);
		Map<String,double[]> authorEmbedding_context = initEmbedding(embeddingLength,embeddingInit,authorList);

		double lastLoss= getAuthorNetworkLoss(coauthorMap,authorList,authorEmbedding,authorEmbedding_context,negativeRatio);
		System.out.println("last loss=" + lastLoss);
		boolean converge=false;
		long startTime =System.currentTimeMillis();
		int convergetime=1;
		while(!converge){
			double currentLoss= authorNetworkEmbeddingLearning(lastLoss,coauthorMap,authorList,authorEmbedding,authorEmbedding_context,
				learningRateInit,negativeRatio);
			System.out.println("current loss=" + currentLoss);
			convergetime++;
			if(Math.abs(currentLoss- lastLoss)/lastLoss<=0.01||convergetime>20){
				converge= true;
			}else{
				lastLoss= currentLoss;
			}
		}
		long endTime= System.currentTimeMillis();
		System.out.println("time difference =" +(endTime - startTime)/1000);
		String authorEmbeddingFile =linuxpath+"single_author(NECoRank-T).txt";
		File authorEmbeddingfile= new File(authorEmbeddingFile);
		FileWriter fwAuthorEmbedding;
		try{
			fwAuthorEmbedding= new FileWriter(authorEmbeddingfile.getAbsoluteFile());
			BufferedWriter bwAuthorEmbedding = new BufferedWriter(fwAuthorEmbedding);
			for(String sequence: authorEmbedding.keySet()){
				bwAuthorEmbedding.write(sequence+",");
				double[] embedding = authorEmbedding.get(sequence);
				for(int i= 0; i<embedding.length;i++){
					bwAuthorEmbedding.write(String.valueOf(embedding[i]+","));
				}
				bwAuthorEmbedding.newLine();
			}
			bwAuthorEmbedding.flush();
			bwAuthorEmbedding.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		return authorEmbedding;
	}

	public static double getGlobalNetworkLoss(Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double> paperWordMap,Map<Set<String>,Double> paperTopicMap,Map<Set<String>,Double> wordTopicMap,
		Map<Set<String>,Double> coauthorMap,Map<Set<String>,Double> paperAuthorMap,ArrayList<String> paperList,ArrayList<String> wordList,ArrayList<String> topicList,ArrayList<String> authorList,
		Map<String,double[]> paperEmbedding, Map<String,double[]> paperEmbedding_context,Map<String,double[]> wordEmbedding,Map<String,double[]> topicEmbedding,Map<String,double[]> authorEmbedding,
		Map<String,double[]> authorEmbedding_context,double[][] embeddingMatrix, int negativeRatio){

		double authorloss=0.0;
		//author Network Loss
		Random rand1 = new Random(System.currentTimeMillis());
		Random rand2 = new Random(System.currentTimeMillis()+10000);

		for(Set<String> link : coauthorMap.keySet()){
			
			ArrayList<String> array=new ArrayList<String>();
			for(String str:link){
				array.add(str);
			}

			// if(authorEmbedding_context.get(array.get(1))!=null){

			// 	authorloss += - coauthorMap.get(link)*Math.log(getSigmoidValue(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));

			// }
			authorloss += - coauthorMap.get(link)*Math.log(getSigmoidValue(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));
		}
		System.out.println("author link loss ");
		for(int i=0;i<coauthorMap.keySet().size()*negativeRatio;i++){
			int nonLinkedIndex1 = rand1.nextInt(authorList.size());
			int nonLinkedIndex2 = rand2.nextInt(authorList.size());
			if(nonLinkedIndex1 != nonLinkedIndex2)
			{
				Set<String> tempLink= new LinkedHashSet<String>();
				tempLink.add(authorList.get(nonLinkedIndex1));
				tempLink.add(authorList.get(nonLinkedIndex2));
				ArrayList<String> array= new ArrayList<String>();
				array.add(authorList.get(nonLinkedIndex1));
				array.add(authorList.get(nonLinkedIndex2));
				if(!coauthorMap.containsKey(tempLink)&&authorEmbedding_context.containsKey(array.get(1))&&authorEmbedding.containsKey(array.get(0))){
					// System.out.println(array.get(0));
					// System.out.println(array.get(1));
					// System.out.println(authorEmbedding.get(array.get(0)));
					// System.out.println(authorEmbedding_context.get(array.get(1)));
					authorloss += -Math.log(1.0-getSigmoidValue(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));
					//System.out.println(getSigmoidValue(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));
				}else{i--;}
			}//else{i--;}
		}
		System.out.println("author nonlink loss ");
		//inter Loss
		double interloss=0.0;
		for(Set<String> link: paperAuthorMap.keySet()){
			ArrayList<String> array=new ArrayList<String>();
			for(String vertex:link){
				array.add(vertex);
			}
			//System.out.println(array.size());
			if(authorEmbedding.containsKey(array.get(0))&&paperEmbedding.containsKey(array.get(1))){
				interloss+= - paperAuthorMap.get(link)*Math.log(getInterLogisticValue(authorEmbedding.get(array.get(0)),embeddingMatrix,paperEmbedding.get(array.get(1))));
				//System.out.println(Double.toString(getVectorMatrixProduct(authorEmbedding.get(array.get(0)),embeddingMatrix,paperEmbedding.get(array.get(1)))));
				//System.out.println(Double.toString(interloss));
							
			}
		}
		System.out.println("inter link loss ");
		for(int i=0;i<paperAuthorMap.keySet().size()*negativeRatio;i++){
			int nonLinkedIndex1=rand1.nextInt(authorList.size());
			int nonLinkedIndex2=rand2.nextInt(paperList.size());
			ArrayList<String> tempLink= new ArrayList<String>();
			tempLink.add(authorList.get(nonLinkedIndex1));
			tempLink.add(paperList.get(nonLinkedIndex2));
			Set<String> nonLink=new LinkedHashSet<String>();
			nonLink.add(authorList.get(nonLinkedIndex1));
			nonLink.add(paperList.get(nonLinkedIndex2));
			if(!paperAuthorMap.containsKey(nonLink)){
				interloss += -Math.log(1.0 - getInterLogisticValue(authorEmbedding.get(tempLink.get(0)),
						embeddingMatrix, paperEmbedding.get(tempLink.get(1))));
			}//else{i--;}
		}
		System.out.println("inter nonlink loss ");
		//regular Matrix....



		//paper Network Loss
		double paperloss=0.0;
		for(Set<String> sequence:paperCitationMap.keySet()){
			ArrayList<String> array= new ArrayList<String>();
			for(String s:sequence){
				array.add(s);
			}
			paperloss+= -paperCitationMap.get(sequence)*Math.log(getSigmoidValue(paperEmbedding_context.get(array.get(1)),paperEmbedding.get(array.get(0))));
		}
		System.out.println("finish1-global");
		for(int i=0;i<paperCitationMap.keySet().size()*negativeRatio;i++){
			int nonLinkedIndex1= rand1.nextInt(paperList.size());
			int nonLinkedIndex2= rand2.nextInt(paperList.size());
			//System.out.println(i);
			if(nonLinkedIndex1!=nonLinkedIndex2){
				Set<String> nonlink= new LinkedHashSet<String> ();
				nonlink.add(paperList.get(nonLinkedIndex1));
				nonlink.add(paperList.get(nonLinkedIndex2));
				ArrayList<String> nonLink=new ArrayList<String>();
				nonLink.add(paperList.get(nonLinkedIndex1));
				nonLink.add(paperList.get(nonLinkedIndex2));
				if(!paperCitationMap.containsKey(nonlink)){
					paperloss+= -Math.log(1.0-getSigmoidValue(paperEmbedding_context.get(nonLink.get(1)),paperEmbedding.get(nonLink.get(0))));
				}else{i--;}
			}else{i--;}
		}
		System.out.println("finish2-global");

//		for(Set<String> pwLink: paperWordMap.keySet()){
//			ArrayList<String> array= new ArrayList<String>();
//			for(String s:pwLink){
//				array.add(s);
//			}
//			
//			paperloss+= -paperWordMap.get(pwLink)*Math.log(getSigmoidValue(wordEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//		}
//		System.out.println("finish3-global");
//		for(int i=0;i<paperWordMap.keySet().size()*negativeRatio;i++){
//			int nonLinkedIndex1=rand1.nextInt(paperList.size());
//			int nonLinkedIndex2=rand2.nextInt(wordList.size());
//			Set<String> nonlink= new LinkedHashSet<String> ();
//			nonlink.add(paperList.get(nonLinkedIndex1));
//			nonlink.add(wordList.get(nonLinkedIndex2));
//			ArrayList<String> nonLink=new ArrayList<String>();
//			nonLink.add(paperList.get(nonLinkedIndex1));
//			nonLink.add(wordList.get(nonLinkedIndex2));
//			if(!paperWordMap.containsKey(nonlink)){
//				paperloss+= -Math.log(1.0-getSigmoidValue(wordEmbedding.get(nonLink.get(1)),paperEmbedding.get(nonLink.get(0))));
//			}else{i--;}
//		}
//		System.out.println("finish4-global");
//
//		for(Set<String> ptLink:paperTopicMap.keySet()){
//			ArrayList<String> array= new ArrayList<String>();
//			for(String s:ptLink){
//				array.add(s);
//			}
//			paperloss+= -paperTopicMap.get(ptLink)*Math.log(getSigmoidValue(topicEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//		}
//		System.out.println("finish5-global");
//		for(int i=0;i<paperTopicMap.keySet().size()*negativeRatio;i++){
//			int nonLinkedIndex1= rand1.nextInt(paperList.size());
//			int nonLinkedIndex2= rand2.nextInt(topicList.size());
//			Set<String> nonlink= new LinkedHashSet<String> ();
//			nonlink.add(paperList.get(nonLinkedIndex1));
//			nonlink.add(topicList.get(nonLinkedIndex2));
//			ArrayList<String> nonLink=new ArrayList<String>();
//			nonLink.add(paperList.get(nonLinkedIndex1));
//			nonLink.add(topicList.get(nonLinkedIndex2));
//			if(!paperTopicMap.containsKey(nonlink)){
//				paperloss+= -Math.log(1.0-getSigmoidValue(topicEmbedding.get(nonLink.get(1)),paperEmbedding.get(nonLink.get(0))));
//			}else{i--;}
//		}
//		System.out.println("finish6-global");

//		for(Set<String> wtLink:wordTopicMap.keySet()){
//			ArrayList<String> array= new ArrayList<String>();
//			for(String s:wtLink){
//				array.add(s);
//			}
//			// System.out.println(array.get(0));
//			// System.out.println(array.get(1)+"_");
//			paperloss+= -wordTopicMap.get(wtLink)*Math.log(getSigmoidValue(topicEmbedding.get(array.get(1)),wordEmbedding.get(array.get(0))));
//		}
//		System.out.println("finish7-global");
//		for(int i=0;i<wordTopicMap.keySet().size()*negativeRatio;i++){
//			int nonLinkedIndex1= rand1.nextInt(wordList.size());
//			int nonLinkedIndex2= rand2.nextInt(topicList.size());
//			Set<String> nonlink= new LinkedHashSet<String> ();
//			nonlink.add(wordList.get(nonLinkedIndex1));
//			nonlink.add(topicList.get(nonLinkedIndex2));
//			ArrayList<String> nonLink=new ArrayList<String>();
//			nonLink.add(wordList.get(nonLinkedIndex1));
//			nonLink.add(topicList.get(nonLinkedIndex2));
//			if(!wordTopicMap.containsKey(nonlink)){
//
//				paperloss+= -Math.log(1.0-getSigmoidValue(topicEmbedding.get(nonLink.get(1)),wordEmbedding.get(nonLink.get(0))));
//			}else{i--;} 
//		}
//		System.out.println("finish8-global");

		double loss=authorloss+interloss+paperloss;
		return loss;
	}

	public static double doubleNetworkMatrixEmbeddingLearning(double lastLoss,Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double>paperWordMap,Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double>wordTopicMap,
		Map<Set<String>,Double>coauthorMap,Map<Set<String>,Double> paperAuthorMap,ArrayList<String>paperList,ArrayList<String>wordList,ArrayList<String>topicList,ArrayList<String>authorList,Map<String,double[]>paperEmbedding,
			Map<String,double[]>paperEmbedding_context,Map<String,double[]>wordEmbedding,Map<String,double[]>topicEmbedding,Map<String,double[]>authorEmbedding,Map<String,double[]>authorEmbedding_context,double[][] embeddingMatrix,
			double learningRateInit,int negativeRatio){
		double[][] gradient = new double[embeddingMatrix.length][embeddingMatrix[0].length];
		for (int i = 0; i < embeddingMatrix.length; i++) {
			for (int j = 0; j < embeddingMatrix[i].length; j++) {
				gradient[i][j] = 0.0;
			}
		}
		System.out.println("matrix gradient init");
		for(Set<String> seq:paperAuthorMap.keySet()){
			ArrayList<String> array=new ArrayList<String>();
			for(String sequence:seq){
				array.add(sequence);
			}
			double weight=paperAuthorMap.get(seq);
			double exponential=0.0;
			if(authorEmbedding.containsKey(array.get(0))&&paperEmbedding.containsKey(array.get(1))){
				exponential=Math.pow(Math.E,- getVectorMatrixProduct(authorEmbedding.get(array.get(0)),embeddingMatrix,paperEmbedding.get(array.get(1))));
				double result=-getVectorMatrixProduct(authorEmbedding.get(array.get(0)),embeddingMatrix,paperEmbedding.get(array.get(1)));
				//System.out.println(result+ " "+exponential);
				double[][] derivative= getLinkedEmbeddingMatrixPartialDerivative (weight,exponential,authorEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1)));
				//System.out.println("Matrix Partial Derivative finished");
				for(int i=0;i<embeddingMatrix.length;i++){
					for(int j=0;j<embeddingMatrix[i].length;j++){
						gradient[i][j]+=derivative[i][j];
					}
				}
			}
		}
		System.out.println("matrix link loss ");
		Random rand1 = new Random(System.currentTimeMillis());
		Random rand2 = new Random(System.currentTimeMillis() + 10000);
		for(int t=0;t<paperAuthorMap.keySet().size()*negativeRatio;t++){
			int nonLinkedIndex1 = rand1.nextInt(authorList.size());
			int nonLinkedIndex2 = rand2.nextInt(paperList.size());
			ArrayList<String> tempLink = new ArrayList<String>();
			tempLink.add(authorList.get(nonLinkedIndex1));
			tempLink.add(paperList.get(nonLinkedIndex2));
			Set<String> nonLink=new LinkedHashSet<String>();
			nonLink.add(authorList.get(nonLinkedIndex1));
			nonLink.add(paperList.get(nonLinkedIndex2));
			if(!paperAuthorMap.containsKey(nonLink)){
				double exponential=Math.pow(Math.E,- getVectorMatrixProduct(authorEmbedding.get(tempLink.get(0)),embeddingMatrix,paperEmbedding.get(tempLink.get(1))));
				double[][] derivative= getNonlinkedEmbeddingMatrixPartialDerivative(exponential,authorEmbedding.get(tempLink.get(0)),paperEmbedding.get(tempLink.get(1)));
				for (int i = 0; i < embeddingMatrix.length; i++) {
					for (int j = 0; j < embeddingMatrix[i].length; j++) {
						gradient[i][j] += derivative[i][j];
					}
				}
			}else{t--;}
		}
		System.out.println("matrix nonlink loss ");
		//regular matrix

		double updatedLoss = backtrackingLineSearchForEmbeddingMatrix(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,gradient,learningRateInit,negativeRatio);
		return updatedLoss;
	}

	public static double doubleNetworkAuthorEmbeddingLearning(double lastLoss,Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double>paperWordMap,Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double>wordTopicMap,
		Map<Set<String>,Double>coauthorMap,Map<Set<String>,Double> paperAuthorMap,ArrayList<String>paperList,ArrayList<String>wordList,ArrayList<String>topicList,ArrayList<String>authorList,Map<String,double[]>paperEmbedding,
			Map<String,double[]>paperEmbedding_context,Map<String,double[]>wordEmbedding,Map<String,double[]>topicEmbedding,Map<String,double[]>authorEmbedding,Map<String,double[]>authorEmbedding_context,double[][] embeddingMatrix,
			double learningRateInit,int negativeRatio){

		//coauthor gradient
		Map<String,double[]> authorgradient=new HashMap<String,double[]>();
		Map<String,double[]> contextAuthorgradient=new HashMap<String,double[]>();
		for(Set<String> coauthor:coauthorMap.keySet()){
			ArrayList<String> array=new ArrayList<String>();
			for(String author:coauthor){
				array.add(author);
			}
			double weight= coauthorMap.get(coauthor);
			double linkedExponential=Math.pow(Math.E,-getVectorInnerProduct(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));
			double[] linkedGradient_V=getNetworkLinkedPartialDerivative(weight,linkedExponential,authorEmbedding_context.get(array.get(1)));
			double[] linkedGradient_C=getNetworkLinkedPartialDerivative(weight,linkedExponential,authorEmbedding.get(array.get(0)));
			if(authorgradient.containsKey(array.get(0))){
				for (int i = 0; i < authorgradient.get(array.get(0)).length; i++) {
					authorgradient.get(array.get(0))[i] += linkedGradient_V[i];
				}
			}else{
				double[] tempGradient = new double[linkedGradient_V.length];
				for (int i = 0; i < tempGradient.length; i++) {
					tempGradient[i] = linkedGradient_V[i];
				}
				authorgradient.put(array.get(0), tempGradient);
			}
			if(contextAuthorgradient.containsKey(array.get(1))){
				for(int i=0;i<contextAuthorgradient.get(array.get(1)).length;i++){
					contextAuthorgradient.get(array.get(1))[i]+=linkedGradient_C[i];
				}
			}else{
				double[] tempGradient = new double[linkedGradient_C.length];
				for (int i = 0; i < tempGradient.length; i++) {
					tempGradient[i] = linkedGradient_C[i];
				}
				contextAuthorgradient.put(array.get(1), tempGradient);
			}
		}
		Random rand1 = new Random(System.currentTimeMillis());
		Random rand2 = new Random(System.currentTimeMillis() + 10000);
		for(int t=0;t<coauthorMap.keySet().size()*negativeRatio;t++){
			int nonLinkedIndex1=rand1.nextInt(authorList.size());
			int nonLinkedIndex2=rand2.nextInt(authorList.size());
			if(nonLinkedIndex1!=nonLinkedIndex2){
				ArrayList<String> nonlink=new ArrayList<String>();
				nonlink.add(authorList.get(nonLinkedIndex1));
				nonlink.add(authorList.get(nonLinkedIndex2));
				Set<String> nonLink=new LinkedHashSet<String>();
				nonLink.add(authorList.get(nonLinkedIndex1));
				nonLink.add(authorList.get(nonLinkedIndex2));
				if(!coauthorMap.containsKey(nonLink)){
					double weight=1.0;
					//double vertex_weight= authorVertexMap.get(authorList.get(nonLinkedIndex1));
					double nonlinkedExponential=Math.pow(Math.E,-getVectorInnerProduct(authorEmbedding_context.get(nonlink.get(1)),authorEmbedding.get(nonlink.get(0))));
					double[] nonlinkedGradient_V=getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,authorEmbedding_context.get(nonlink.get(1)));
					double[] nonlinkedGradient_C=getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,authorEmbedding.get(nonlink.get(0)));
					if(authorgradient.containsKey(nonlink.get(0))){
						for(int i=0;i<authorgradient.get(nonlink.get(0)).length;i++){
							authorgradient.get(nonlink.get(0))[i]+=nonlinkedGradient_V[i];
						}
					}else{
						double[] tempGradient = new double[nonlinkedGradient_V.length];
						for (int i = 0; i < tempGradient.length; i++) {
							tempGradient[i] = nonlinkedGradient_V[i];
						}
						authorgradient.put(nonlink.get(0), tempGradient);
					}
					if(contextAuthorgradient.containsKey(nonlink.get(1))){
						for(int i=0;i<contextAuthorgradient.get(nonlink.get(1)).length;i++){
							contextAuthorgradient.get(nonlink.get(1))[i]+=nonlinkedGradient_C[i];
						}
					}else{
						double[] tempGradient = new double[nonlinkedGradient_C.length];
						for (int i = 0; i < tempGradient.length; i++) {
							tempGradient[i] = nonlinkedGradient_C[i];
						}
						contextAuthorgradient.put(nonlink.get(1), tempGradient);
					}
				}else{t--;}
			}else{t--;}	
		}
		//regular...

		//inter gradient
		for(Set<String> link:paperAuthorMap.keySet()){
			ArrayList<String> array=new ArrayList<String>();
			for(String vertex:link){
				array.add(vertex);
				//System.out.println(vertex);
			}
			double weight=paperAuthorMap.get(link);
			double linkedExponential=Math.pow(Math.E,-getVectorMatrixProduct(authorEmbedding.get(array.get(0)),embeddingMatrix,paperEmbedding.get(array.get(1))));
			double[] authorDerivative=getLinkedAuthorDerivative(weight,linkedExponential,embeddingMatrix,paperEmbedding.get(array.get(1)));
			if(authorgradient.containsKey(array.get(0))){
				for(int i=0;i<authorgradient.get(array.get(0)).length;i++){
					authorgradient.get(array.get(0))[i]+=authorDerivative[i];
				}
			}else{
				double[] tempGradient=new double[authorDerivative.length];
				for (int i = 0; i < tempGradient.length; i++) {
						tempGradient[i] = authorDerivative[i];
					}
					authorgradient.put(array.get(0), tempGradient);
			}
		}

		Random rand3 = new Random(System.currentTimeMillis());
		Random rand4 = new Random(System.currentTimeMillis() + 10000);
		for(int t=0;t<paperAuthorMap.keySet().size()*negativeRatio;t++){
			ArrayList<String> nonlink=new ArrayList<String>();
			int nonLinkedIndex1=rand3.nextInt(authorList.size());
			int nonLinkedIndex2=rand4.nextInt(paperList.size());
			nonlink.add(authorList.get(nonLinkedIndex1));
			nonlink.add(paperList.get(nonLinkedIndex2));
			Set<String> nonLink=new LinkedHashSet<String>();
			nonLink.add(authorList.get(nonLinkedIndex1));
			nonLink.add(paperList.get(nonLinkedIndex2));
			if(!paperAuthorMap.containsKey(nonLink)){
				double weight=1.0;
				double linkedExponential=Math.pow(Math.E,-getVectorMatrixProduct(authorEmbedding.get(nonlink.get(0)),embeddingMatrix,paperEmbedding.get(nonlink.get(1))));
				double[] authorDerivative=getNonLinkedAuthorDerivative(weight,linkedExponential,embeddingMatrix,paperEmbedding.get(nonlink.get(1)));
				if(authorgradient.containsKey(nonlink.get(0))){
					for(int i=0;i<authorgradient.get(nonlink.get(0)).length;i++){
						authorgradient.get(nonlink.get(0))[i]+=authorDerivative[i];
					}
				}else{
					double[] tempGradient=new double[authorDerivative.length];
					for (int i = 0; i < tempGradient.length; i++) {
						tempGradient[i] = authorDerivative[i];
					}
					authorgradient.put(nonlink.get(0), tempGradient);
				}
			}else{t--;}
			  
		}
		double updatedLoss=backtrackingLineSearchForAuthorEmbedding(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,authorgradient,contextAuthorgradient,learningRateInit,negativeRatio);
		return updatedLoss;
	}
	public static double doubleNetworkPaperEmbeddingLearning(double lastLoss,Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double>paperWordMap,Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double>wordTopicMap,
		Map<Set<String>,Double>coauthorMap,Map<Set<String>,Double> paperAuthorMap,ArrayList<String>paperList,ArrayList<String>wordList,ArrayList<String>topicList,ArrayList<String>authorList,Map<String,double[]>paperEmbedding,
			Map<String,double[]>paperEmbedding_context,Map<String,double[]>wordEmbedding,Map<String,double[]>topicEmbedding,Map<String,double[]>authorEmbedding,Map<String,double[]>authorEmbedding_context,double[][] embeddingMatrix,
			double learningRateInit,int negativeRatio){

		//paper Network gradient
		Map<String,double[]> papergradient = new HashMap<String,double[]>();
		Map<String,double[]> contextPapergradient = new HashMap<String,double[]>();
		Map<String,double[]> wordgradient = new HashMap<String,double[]>();
		Map<String,double[]> topicgradient = new HashMap<String,double[]>();
		
		for(Set<String> sequence:paperCitationMap.keySet()){
			double weight = paperCitationMap.get(sequence);
			ArrayList<String> array = new ArrayList<String>();
			for(String str: sequence){
				array.add(str);
			}
			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(paperEmbedding_context.get(array.get(1)),paperEmbedding.get(array.get(0))));
			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(paperEmbedding_context.get(array.get(0)),paperEmbedding.get(array.get(1))));
			
			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,paperEmbedding_context.get(array.get(1)));
			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,paperEmbedding.get(array.get(0)));
		
			if(papergradient.containsKey(array.get(0))){
				for(int i=0;i<papergradient.get(array.get(0)).length;i++){
					papergradient.get(array.get(0))[i] += linkedGradient_V[i];
				}
			}else{
				double[] tempgradient = new double[linkedGradient_V.length];
				for(int i=0;i<tempgradient.length;i++){
					tempgradient[i]= linkedGradient_V[i];
				}
				papergradient.put(array.get(0),tempgradient);
			}

			if(contextPapergradient.containsKey(array.get(1))){
				for(int i=0;i<contextPapergradient.get(array.get(1)).length;i++){
					contextPapergradient.get(array.get(1))[i] += linkedGradient_C[i];
				}
			}else{
				double[] tempgradient= new double[linkedGradient_C.length];
				for(int i=0;i<tempgradient.length;i++){
					tempgradient[i]=linkedGradient_C[i];
				}
				contextPapergradient.put(array.get(1),tempgradient);
			}
		}

		Random rand1 = new Random(System.currentTimeMillis());
		Random rand2 = new Random(System.currentTimeMillis()+10000);
		for(int t=0;t<paperCitationMap.keySet().size()*negativeRatio;t++){
			int nonLinkedIndex1 = rand1.nextInt(paperList.size());
			int nonLinkedIndex2 = rand2.nextInt(paperList.size());
			if(nonLinkedIndex1!= nonLinkedIndex2){
				double weight=1.0;
				Set<String> tempLink= new LinkedHashSet<String>();
				tempLink.add(paperList.get(nonLinkedIndex1));
				tempLink.add(paperList.get(nonLinkedIndex2));
				ArrayList<String> array = new ArrayList<String>();
				array.add(paperList.get(nonLinkedIndex1));
				array.add(paperList.get(nonLinkedIndex2));
				if(!paperCitationMap.containsKey(tempLink)){
					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(paperEmbedding_context.get(array.get(1)),paperEmbedding.get(array.get(0))));
					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(paperEmbedding_context.get(array.get(0)),paperEmbedding.get(array.get(1))));
					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,paperEmbedding_context.get(array.get(1)));
					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,paperEmbedding.get(array.get(0)));

					if(papergradient.containsKey(array.get(0))){
						for(int i=0;i<papergradient.get(array.get(0)).length;i++){
							papergradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
						}
					}else{
						double[] tempGradient= new double[nonlinkedGradient0.length];
						for(int i=0;i<tempGradient.length;i++){
							tempGradient[i]=nonlinkedGradient0[i];
						}
						papergradient.put(array.get(0),tempGradient);
					}
					if(contextPapergradient.containsKey(array.get(1))){
						for(int i=0;i<contextPapergradient.get(array.get(1)).length;i++){
							contextPapergradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
						}
					}else{
						double[] tempGradient= new double[nonlinkedGradient1.length];
						for(int i=0;i<tempGradient.length;i++){
							tempGradient[i]=nonlinkedGradient1[i];
						}
						contextPapergradient.put(array.get(1),tempGradient);
					}
  
				}else{t--;}
			}else{t--;}
		}

//		for(Set<String> sequence:paperWordMap.keySet()){
//			double weight = paperWordMap.get(sequence);
//			ArrayList<String> array = new ArrayList<String>();
//			for(String str: sequence){
//				array.add(str);
//			}
//			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(wordEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(wordEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1))));
//			
//			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,wordEmbedding.get(array.get(1)));
//			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,paperEmbedding.get(array.get(0)));
//		
//			if(papergradient.containsKey(array.get(0))){
//				for(int i=0;i<papergradient.get(array.get(0)).length;i++){
//					papergradient.get(array.get(0))[i] += linkedGradient_V[i];
//				}
//			}else{
//				double[] tempgradient = new double[linkedGradient_V.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]= linkedGradient_V[i];
//				}
//				papergradient.put(array.get(0),tempgradient);
//			}
//
//			if(wordgradient.containsKey(array.get(1))){
//				for(int i=0;i<wordgradient.get(array.get(1)).length;i++){
//					wordgradient.get(array.get(1))[i] += linkedGradient_C[i];
//				}
//			}else{
//				double[] tempgradient= new double[linkedGradient_C.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]=linkedGradient_C[i];
//				}
//				wordgradient.put(array.get(1),tempgradient);
//			}
//		}
//
//		for(int t=0;t<paperWordMap.keySet().size()*negativeRatio;t++){
//			int nonLinkedIndex1 = rand1.nextInt(paperList.size());
//			int nonLinkedIndex2 = rand2.nextInt(wordList.size());
//			if(nonLinkedIndex1!= nonLinkedIndex2){
//				double weight=1.0;
//				Set<String> tempLink= new LinkedHashSet<String>();
//				tempLink.add(paperList.get(nonLinkedIndex1));
//				tempLink.add(wordList.get(nonLinkedIndex2));
//				ArrayList<String> array = new ArrayList<String>();
//				array.add(paperList.get(nonLinkedIndex1));
//				array.add(wordList.get(nonLinkedIndex2));
//				if(!paperWordMap.containsKey(tempLink)){
//					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(wordEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(wordEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1))));
//					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,wordEmbedding.get(array.get(1)));
//					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,paperEmbedding.get(array.get(0)));
//
//					if(papergradient.containsKey(array.get(0))){
//						for(int i=0;i<papergradient.get(array.get(0)).length;i++){
//							papergradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient0.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient0[i];
//						}
//						papergradient.put(array.get(0),tempGradient);
//					}
//					if(wordgradient.containsKey(array.get(1))){
//						for(int i=0;i<wordgradient.get(array.get(1)).length;i++){
//							wordgradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient1.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient1[i];
//						}
//						wordgradient.put(array.get(1),tempGradient);
//					}
//  
//				}else{t--;}
//			}else{t--;}
//		}
//
//		for(Set<String> sequence:paperTopicMap.keySet()){
//			double weight = paperTopicMap.get(sequence);
//			ArrayList<String> array = new ArrayList<String>();
//			for(String str: sequence){
//				array.add(str);
//			}
//			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1))));
//			
//			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,topicEmbedding.get(array.get(1)));
//			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,paperEmbedding.get(array.get(0)));
//		
//			if(papergradient.containsKey(array.get(0))){
//				for(int i=0;i<papergradient.get(array.get(0)).length;i++){
//					papergradient.get(array.get(0))[i] += linkedGradient_V[i];
//				}
//			}else{
//				double[] tempgradient = new double[linkedGradient_V.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]= linkedGradient_V[i];
//				}
//				papergradient.put(array.get(0),tempgradient);
//			}
//
//			if(topicgradient.containsKey(array.get(1))){
//				for(int i=0;i<topicgradient.get(array.get(1)).length;i++){
//					topicgradient.get(array.get(1))[i] += linkedGradient_C[i];
//				}
//			}else{
//				double[] tempgradient= new double[linkedGradient_C.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]=linkedGradient_C[i];
//				}
//				topicgradient.put(array.get(1),tempgradient);
//			}
//		}
//
//		for(int t=0;t<paperTopicMap.keySet().size()*negativeRatio;t++){
//			int nonLinkedIndex1 = rand1.nextInt(paperList.size());
//			int nonLinkedIndex2 = rand2.nextInt(topicList.size());
//			if(nonLinkedIndex1!= nonLinkedIndex2){
//				double weight=1.0;
//				Set<String> tempLink= new LinkedHashSet<String>();
//				tempLink.add(paperList.get(nonLinkedIndex1));
//				tempLink.add(topicList.get(nonLinkedIndex2));
//				ArrayList<String> array = new ArrayList<String>();
//				array.add(paperList.get(nonLinkedIndex1));
//				array.add(topicList.get(nonLinkedIndex2));
//				if(!paperTopicMap.containsKey(tempLink)){
//					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1))));
//					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,topicEmbedding.get(array.get(1)));
//					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,paperEmbedding.get(array.get(0)));
//
//					if(papergradient.containsKey(array.get(0))){
//						for(int i=0;i<papergradient.get(array.get(0)).length;i++){
//							papergradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient0.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient0[i];
//						}
//						papergradient.put(array.get(0),tempGradient);
//					}
//					if(topicgradient.containsKey(array.get(1))){
//						for(int i=0;i<topicgradient.get(array.get(1)).length;i++){
//							topicgradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient1.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient1[i];
//						}
//						topicgradient.put(array.get(1),tempGradient);
//					}
//  
//				}else{t--;}
//			}else{t--;}
//		}

//		for(Set<String> sequence:wordTopicMap.keySet()){
//			double weight = wordTopicMap.get(sequence);
//			ArrayList<String> array = new ArrayList<String>();
//			for(String str: sequence){
//				array.add(str);
//			}
//			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(1)),wordEmbedding.get(array.get(0))));
//			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(0)),wordEmbedding.get(array.get(1))));
//			
//			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,topicEmbedding.get(array.get(1)));
//			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,wordEmbedding.get(array.get(0)));
//		
//			if(wordgradient.containsKey(array.get(0))){
//				for(int i=0;i<wordgradient.get(array.get(0)).length;i++){
//					wordgradient.get(array.get(0))[i] += linkedGradient_V[i];
//				}
//			}else{
//				double[] tempgradient = new double[linkedGradient_V.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]= linkedGradient_V[i];
//				}
//				wordgradient.put(array.get(0),tempgradient);
//			}
//
//			if(topicgradient.containsKey(array.get(1))){
//				for(int i=0;i<topicgradient.get(array.get(1)).length;i++){
//					topicgradient.get(array.get(1))[i] += linkedGradient_C[i];
//				}
//			}else{
//				double[] tempgradient= new double[linkedGradient_C.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]=linkedGradient_C[i];
//				}
//				topicgradient.put(array.get(1),tempgradient);
//			}
//		}
//
//		for(int t=0;t<wordTopicMap.keySet().size()*negativeRatio;t++){
//			int nonLinkedIndex1 = rand1.nextInt(wordList.size());
//			int nonLinkedIndex2 = rand2.nextInt(topicList.size());
//			if(nonLinkedIndex1!= nonLinkedIndex2){
//				double weight=1.0;
//				Set<String> tempLink= new LinkedHashSet<String>();
//				tempLink.add(wordList.get(nonLinkedIndex1));
//				tempLink.add(topicList.get(nonLinkedIndex2));
//				ArrayList<String> array = new ArrayList<String>();
//				array.add(wordList.get(nonLinkedIndex1));
//				array.add(topicList.get(nonLinkedIndex2));
//				if(!wordTopicMap.containsKey(tempLink)){
//					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(1)),wordEmbedding.get(array.get(0))));
//					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(0)),wordEmbedding.get(array.get(1))));
//					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,topicEmbedding.get(array.get(1)));
//					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,wordEmbedding.get(array.get(0)));
//
//					if(wordgradient.containsKey(array.get(0))){
//						for(int i=0;i<wordgradient.get(array.get(0)).length;i++){
//							wordgradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient0.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient0[i];
//						}
//						wordgradient.put(array.get(0),tempGradient);
//					}
//					if(topicgradient.containsKey(array.get(1))){
//						for(int i=0;i<topicgradient.get(array.get(1)).length;i++){
//							topicgradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient1.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient1[i];
//						}
//						topicgradient.put(array.get(1),tempGradient);
//					}
//  
//				}else{t--;}
//			}else{t--;}
//		}

		//inter gradient
		for(Set<String> link:paperAuthorMap.keySet()){
			ArrayList<String> array=new ArrayList<String>();
			for(String vertex:link){
				array.add(vertex);
			}
			double weight=paperAuthorMap.get(link);
			double linkedExponential=Math.pow(Math.E,-getVectorMatrixProduct(authorEmbedding.get(array.get(0)),embeddingMatrix,paperEmbedding.get(array.get(1))));
			double[] paperDerivative=getLinkedPaperDerivative(weight,linkedExponential,embeddingMatrix,authorEmbedding.get(array.get(0)));
			if(papergradient.containsKey(array.get(1))){
				for(int i=0;i<papergradient.get(array.get(1)).length;i++){
					papergradient.get(array.get(1))[i]+=paperDerivative[i];
				}
			}else{
				double[] tempGradient=new double[paperDerivative.length];
				for (int i = 0; i < tempGradient.length; i++) {
						tempGradient[i] = paperDerivative[i];
					}
					papergradient.put(array.get(1), tempGradient);
			}
		}

		Random rand3 = new Random(System.currentTimeMillis());
		Random rand4 = new Random(System.currentTimeMillis() + 10000);
		for(int t=0;t<paperAuthorMap.keySet().size()*negativeRatio;t++){
			
			ArrayList<String> nonlink=new ArrayList<String>();
			int nonLinkedIndex1=rand3.nextInt(authorList.size());
			int nonLinkedIndex2=rand4.nextInt(paperList.size());
			nonlink.add(authorList.get(nonLinkedIndex1));
			nonlink.add(paperList.get(nonLinkedIndex2));
			Set<String> nonLink=new LinkedHashSet<String>();
			nonLink.add(authorList.get(nonLinkedIndex1));
			nonLink.add(paperList.get(nonLinkedIndex2));
			if(!paperAuthorMap.containsKey(nonLink)){
				double weight=1.0;
				double linkedExponential=Math.pow(Math.E,-getVectorMatrixProduct(authorEmbedding.get(nonlink.get(0)),embeddingMatrix,paperEmbedding.get(nonlink.get(1))));
				double[] paperDerivative=getNonLinkedPaperDerivative(weight,linkedExponential,embeddingMatrix,authorEmbedding.get(nonlink.get(0)));
				if(papergradient.containsKey(nonlink.get(1))){
					for(int i=0;i<papergradient.get(nonlink.get(1)).length;i++){
						papergradient.get(nonlink.get(1))[i]+=paperDerivative[i];
					}
				}else{
					double[] tempGradient=new double[paperDerivative.length];
					for (int i = 0; i < tempGradient.length; i++) {
						tempGradient[i] = paperDerivative[i];
					}
					papergradient.put(nonlink.get(1), tempGradient);
				}
			}else{t--;}
			  
		}

		double updatedLoss=backtrackingLineSearchForPaperEmbedding(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,papergradient,contextPapergradient,wordgradient,topicgradient,learningRateInit,negativeRatio);
		return updatedLoss;
	}

	public static double backtrackingLineSearchForEmbeddingMatrix(double lastLoss,Map<Set<String>,Double>paperCitationMap,Map<Set<String>,Double>paperWordMap,Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double>wordTopicMap,
		Map<Set<String>,Double>coauthorMap,Map<Set<String>,Double>paperAuthorMap,ArrayList<String>paperList,ArrayList<String>wordList,ArrayList<String>topicList,ArrayList<String>authorList,Map<String,double[]>paperEmbedding,
			Map<String,double[]>paperEmbedding_context,Map<String,double[]>wordEmbedding,Map<String,double[]>topicEmbedding,Map<String,double[]>authorEmbedding,Map<String,double[]>authorEmbedding_context,double[][] embeddingMatrix,
			double[][] gradient,double learningRateInit,int negativeRatio){
		double[][] stayvalue=new double[embeddingMatrix.length][embeddingMatrix.length];
		for(int i = 0; i < embeddingMatrix.length; i++){
			for (int j = 0; j < embeddingMatrix[i].length; j++){
				stayvalue[i][j]=embeddingMatrix[i][j];
			}
		}

		double normalization = 0.0;
		for (int i = 0; i < gradient.length; i++) {
			for (int j = 0; j < gradient[i].length; j++) {
				normalization += Math.pow(gradient[i][j], 2);
			}
		}
		normalization = Math.sqrt(normalization);

		for (int i = 0; i < gradient.length; i++) {
			for (int j = 0; j < gradient[i].length; j++) {
				gradient[i][j] /= normalization;
			}
		}
		double updatedPoint = 0.0;
		boolean found = false;
		int trackingTimes = 0;
		while (!found) {
			double[][] updatedEmbeddingMatrix = new double[embeddingMatrix.length][embeddingMatrix[0].length];
			for (int i = 0; i < gradient.length; i++) {
				for (int j = 0; j < gradient[i].length; j++) {
					double tempValue = embeddingMatrix[i][j] - learningRateInit * gradient[i][j];
					if (tempValue * embeddingMatrix[i][j] > 0.0) {
						updatedEmbeddingMatrix[i][j] = tempValue;
					} else if (tempValue * embeddingMatrix[i][j] < 0.0) {
						updatedEmbeddingMatrix[i][j] = 0.0;
					} else {
						if (tempValue == 0.0) {
							updatedEmbeddingMatrix[i][j] = 0.0;
						} else {
							updatedEmbeddingMatrix[i][j] = tempValue;
						}
					}
				}
			}
			System.out.println("matrix gradient ");
			updatedPoint=getGlobalNetworkLoss(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,negativeRatio);
			if (updatedPoint <= lastLoss) {
				found = true;
				for (int i = 0; i < gradient.length; i++) {
					for (int j = 0; j < gradient[i].length; j++) {
						double tempValue = embeddingMatrix[i][j] - learningRateInit * gradient[i][j];
						if (tempValue * embeddingMatrix[i][j] > 0.0) {
							embeddingMatrix[i][j] = tempValue;
						} else if (tempValue * embeddingMatrix[i][j] < 0.0) {
							embeddingMatrix[i][j] = 0.0;
						} else {
							if (tempValue == 0.0) {
								embeddingMatrix[i][j] = 0.0;
							} else {
								embeddingMatrix[i][j] = tempValue;
							}
						}
					}
				}
			} else {
				learningRateInit /= 2.0;
				if (trackingTimes <= 3) {
					trackingTimes++;
				} else {
					if(updatedPoint>lastLoss){
						for(int i = 0; i < embeddingMatrix.length; i++){
							for (int j = 0; j < embeddingMatrix[i].length; j++){
								embeddingMatrix[i][j]=stayvalue[i][j];
							}
						}
						updatedPoint=lastLoss;
					}
					found = true;
				}
			}
		}
		return updatedPoint;

	}

	public static double backtrackingLineSearchForAuthorEmbedding(double lastLoss,Map<Set<String>,Double>paperCitationMap,Map<Set<String>,Double>paperWordMap,Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double>wordTopicMap,
		Map<Set<String>,Double>coauthorMap,Map<Set<String>,Double>paperAuthorMap,ArrayList<String>paperList,ArrayList<String>wordList,ArrayList<String>topicList,ArrayList<String>authorList,Map<String,double[]>paperEmbedding,
			Map<String,double[]>paperEmbedding_context,Map<String,double[]>wordEmbedding,Map<String,double[]>topicEmbedding,Map<String,double[]>authorEmbedding,Map<String,double[]>authorEmbedding_context,double[][] embeddingMatrix,
			Map<String, double[]> authorgradient,Map<String, double[]> contextAuthorgradient,double learningRateInit,int negativeRatio){

		Map<String, double[]> ast=new HashMap<String, double[]>();
		Map<String, double[]> acst=new HashMap<String, double[]>();	
		for(String sequence:authorEmbedding.keySet()){
			double[] tempgradient = new double[authorEmbedding.get(sequence).length];
			for(int i=0;i<tempgradient.length;i++){
				tempgradient[i]= authorEmbedding.get(sequence)[i];
			}
			ast.put(sequence,tempgradient);
		}
		for(String sequence : authorEmbedding_context.keySet()){
			double[] tempgradient = new double[authorEmbedding_context.get(sequence).length];
			for(int i=0;i<tempgradient.length;i++){
				tempgradient[i]= authorEmbedding_context.get(sequence)[i];
			}
			acst.put(sequence,tempgradient);
		}	

		for (String sequence : authorgradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < authorgradient.get(sequence).length; i++) {
				normalization += Math.pow(authorgradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < authorgradient.get(sequence).length; i++) {
				authorgradient.get(sequence)[i] /= normalization;
			}
		}
		for (String sequence : contextAuthorgradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < contextAuthorgradient.get(sequence).length; i++) {
				normalization += Math.pow(contextAuthorgradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < contextAuthorgradient.get(sequence).length; i++) {
				contextAuthorgradient.get(sequence)[i] /= normalization;
			}
		}

		double updatePoint =0.0;
		boolean found= false;
		int trackingTimes=0;
		while(!found){
			 Map<String, double[]> updatedAuthorEmbeddingMap = new HashMap<String, double[]>();
			 Map<String, double[]> updatedContextAuthorEmbeddingMap = new HashMap<String, double[]>();
			for(String sequence:authorgradient.keySet()){
				double[] updatedEmbedding = new double[authorgradient.get(sequence).length];
				for(int i=0;i<authorgradient.get(sequence).length;i++){
					updatedEmbedding[i] =authorEmbedding.get(sequence)[i]-learningRateInit*authorgradient.get(sequence)[i];
				}
				updatedAuthorEmbeddingMap.put(sequence,updatedEmbedding);
			}
			for(String seq : contextAuthorgradient.keySet()){
				double[] updatedContextEmbedding = new double[contextAuthorgradient.get(seq).length];
				for(int i=0;i<contextAuthorgradient.get(seq).length;i++){
					updatedContextEmbedding[i]=authorEmbedding_context.get(seq)[i]- learningRateInit*contextAuthorgradient.get(seq)[i];
				}
				updatedContextAuthorEmbeddingMap.put(seq,updatedContextEmbedding);
			}
			// updatePoint = getGlobalNetworkLoss(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			// paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,negativeRatio);
			updatePoint = getGlobalNetworkLoss(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,updatedAuthorEmbeddingMap,updatedContextAuthorEmbeddingMap,embeddingMatrix,negativeRatio);
			if(updatePoint<= lastLoss){
				found=true;
				for(String sequence:authorgradient.keySet()){
					for(int i=0;i<authorgradient.get(sequence).length;i++){
						authorEmbedding.get(sequence)[i]-=learningRateInit*authorgradient.get(sequence)[i];
					}
				}
				for(String seq : contextAuthorgradient.keySet()){
					for(int i=0;i<contextAuthorgradient.get(seq).length;i++){
						authorEmbedding_context.get(seq)[i]-= learningRateInit*contextAuthorgradient.get(seq)[i];
					}
				}
			}else{
				
				learningRateInit/=2.0;
				if (trackingTimes <= 3) {
					trackingTimes++;
				} else {
					if(updatePoint>lastLoss){
						for(String sequence:authorEmbedding.keySet()){
							for(int i=0;i<authorEmbedding.get(sequence).length;i++){
								authorEmbedding.get(sequence)[i]=ast.get(sequence)[i];
							}
						}
						for(String seq : authorEmbedding_context.keySet()){
							for(int i=0;i<authorEmbedding_context.get(seq).length;i++){
								authorEmbedding_context.get(seq)[i]=acst.get(seq)[i];
							}
						}
					}
					found = true;
					updatePoint=lastLoss;
				}
			}

		}
		return updatePoint;
	}

	public static double backtrackingLineSearchForPaperEmbedding (double lastLoss,Map<Set<String>,Double>paperCitationMap,Map<Set<String>,Double>paperWordMap,Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double>wordTopicMap,
		Map<Set<String>,Double>coauthorMap,Map<Set<String>,Double>paperAuthorMap,ArrayList<String>paperList,ArrayList<String>wordList,ArrayList<String>topicList,ArrayList<String>authorList,Map<String,double[]>paperEmbedding,
			Map<String,double[]>paperEmbedding_context,Map<String,double[]>wordEmbedding,Map<String,double[]>topicEmbedding,Map<String,double[]>authorEmbedding,Map<String,double[]>authorEmbedding_context,double[][] embeddingMatrix,
			Map<String, double[]>papergradient,Map<String, double[]>contextPapergradient,Map<String, double[]>wordgradient,Map<String, double[]>topicgradient,double learningRateInit,int negativeRatio){

		Map<String, double[]> pst=new HashMap<String, double[]>();
		Map<String, double[]> pcst=new HashMap<String, double[]>();
		Map<String, double[]> wst=new HashMap<String, double[]>();
		Map<String, double[]> tcst=new HashMap<String, double[]>();
		for(String sequence:paperEmbedding.keySet()){
			double[] tempgradient = new double[paperEmbedding.get(sequence).length];
			for(int i=0;i<tempgradient.length;i++){
				tempgradient[i]= paperEmbedding.get(sequence)[i];
			}
			pst.put(sequence,tempgradient);
		}
		for(String sequence : paperEmbedding_context.keySet()){
			double[] tempgradient = new double[paperEmbedding_context.get(sequence).length];
			for(int i=0;i<tempgradient.length;i++){
				tempgradient[i]= paperEmbedding_context.get(sequence)[i];
			}
			pcst.put(sequence,tempgradient);
		}
		for(String sequence : wordEmbedding.keySet()){
			double[] tempgradient = new double[wordEmbedding.get(sequence).length];
			for(int i=0;i<tempgradient.length;i++){
				tempgradient[i]= wordEmbedding.get(sequence)[i];
			}
			wst.put(sequence,tempgradient);
		}
		for(String sequence : topicEmbedding.keySet()){
			double[] tempgradient = new double[topicEmbedding.get(sequence).length];
			for(int i=0;i<tempgradient.length;i++){
				tempgradient[i]= topicEmbedding.get(sequence)[i];
			}
			tcst.put(sequence,tempgradient);
		}

		for (String sequence : papergradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < papergradient.get(sequence).length; i++) {
				normalization += Math.pow(papergradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < papergradient.get(sequence).length; i++) {
				papergradient.get(sequence)[i] /= normalization;
			}
		}
		for (String sequence : contextPapergradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < contextPapergradient.get(sequence).length; i++) {
				normalization += Math.pow(contextPapergradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < contextPapergradient.get(sequence).length; i++) {
				contextPapergradient.get(sequence)[i] /= normalization;
			}
		}
		for (String sequence : wordgradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < wordgradient.get(sequence).length; i++) {
				normalization += Math.pow(wordgradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < wordgradient.get(sequence).length; i++) {
				wordgradient.get(sequence)[i] /= normalization;
			}
		}
		for (String sequence : topicgradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < topicgradient.get(sequence).length; i++) {
				normalization += Math.pow(topicgradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < topicgradient.get(sequence).length; i++) {
				topicgradient.get(sequence)[i] /= normalization;
			}
		}
		double updatePoint=0.0;
		boolean found=false;
		int trackingTimes=0;
		while(!found){
			// Map<String, double[]> updatedPaperEmbeddingMap = new HashMap<String, double[]>();
			// Map<String, double[]> updatedContextPaperEmbeddingMap = new HashMap<String, double[]>();
			// Map<String, double[]> updatedWordEmbeddingMap = new HashMap<String, double[]>();
			// Map<String, double[]> updatedtopicEmbeddingMap = new HashMap<String, double[]>();
			for(String sequence: papergradient.keySet()){
				for(int i=0;i<papergradient.get(sequence).length;i++){
					paperEmbedding.get(sequence)[i]-=learningRateInit*papergradient.get(sequence)[i];
				}
			}
			for(String sequence: contextPapergradient.keySet()){
				for(int i=0;i<contextPapergradient.get(sequence).length;i++){
					paperEmbedding_context.get(sequence)[i]-=learningRateInit*contextPapergradient.get(sequence)[i];
				}
			}
			for(String sequence: wordgradient.keySet()){
				for(int i=0;i<wordgradient.get(sequence).length;i++){
					wordEmbedding.get(sequence)[i]-=learningRateInit*wordgradient.get(sequence)[i];
				}
			}
			for(String sequence: topicgradient.keySet()){
				for(int i=0;i<topicgradient.get(sequence).length;i++){
					topicEmbedding.get(sequence)[i]-=learningRateInit*topicgradient.get(sequence)[i];
				}
			}
			updatePoint = getGlobalNetworkLoss(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,coauthorMap,paperAuthorMap,paperList,wordList,topicList,authorList,paperEmbedding,
			paperEmbedding_context,wordEmbedding,topicEmbedding,authorEmbedding,authorEmbedding_context,embeddingMatrix,negativeRatio);
			if(updatePoint<lastLoss){
				found=true;
				for(String sequence:papergradient.keySet()){
					for(int i=0;i<papergradient.get(sequence).length;i++){
						paperEmbedding.get(sequence)[i]-=learningRateInit*papergradient.get(sequence)[i];
					}
				}
				for(String seq : contextPapergradient.keySet()){
					for(int i=0;i<contextPapergradient.get(seq).length;i++){
						paperEmbedding_context.get(seq)[i]-= learningRateInit*contextPapergradient.get(seq)[i];
					}
				}
				for(String seq : wordgradient.keySet()){
					for(int i=0;i<wordgradient.get(seq).length;i++){
						wordEmbedding.get(seq)[i]-= learningRateInit*wordgradient.get(seq)[i];
					}
				}
				for(String seq : topicgradient.keySet()){
					for(int i=0;i<topicgradient.get(seq).length;i++){
						topicEmbedding.get(seq)[i]-= learningRateInit*topicgradient.get(seq)[i];
					}
				}
			}else{
				
				learningRateInit/=2.0;
				if (trackingTimes <= 3) {
					trackingTimes++;
				} else {
					if(updatePoint>lastLoss){
						for(String sequence:paperEmbedding.keySet()){
							for(int i=0;i<paperEmbedding.get(sequence).length;i++){
								paperEmbedding.get(sequence)[i]=pst.get(sequence)[i];
							}
						}
						for(String seq : paperEmbedding_context.keySet()){
							for(int i=0;i<paperEmbedding_context.get(seq).length;i++){
								paperEmbedding_context.get(seq)[i]=pcst.get(seq)[i];
							}
						}
						for(String seq : wordEmbedding.keySet()){
							for(int i=0;i<wordEmbedding.get(seq).length;i++){
								wordEmbedding.get(seq)[i]=wst.get(seq)[i];
							}
						}
						for(String seq : topicEmbedding.keySet()){
							for(int i=0;i<topicEmbedding.get(seq).length;i++){
								topicEmbedding.get(seq)[i]=tcst.get(seq)[i];
							}
						}
					}
					found = true;
					updatePoint=lastLoss;
				}
			}
		}
		return updatePoint;
	}

	public static double getPaperNetworkLoss(Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double> paperWordMap,Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double> wordTopicMap,
		ArrayList<String> paperList,ArrayList<String> wordList,ArrayList<String> topicList,Map<String,double[]> paperEmbedding,Map<String,double[]>paperEmbedding_context,Map<String,double[]> wordEmbedding,
		Map<String,double[]> topicEmbedding,int negativeRatio){

		
		double loss=0.0;
		Random rand1=new Random(System.currentTimeMillis());
		Random rand2=new Random(System.currentTimeMillis()+10000);
		for(Set<String> sequence:paperCitationMap.keySet()){
			ArrayList<String> array= new ArrayList<String>();
			for(String s:sequence){
				//System.out.println(s);
				array.add(s);
			}
			//System.out.println(paperEmbedding_context.keySet().size());
			//System.out.println(array.get(0));
			loss+= -paperCitationMap.get(sequence)*Math.log(getSigmoidValue(paperEmbedding_context.get(array.get(1)),paperEmbedding.get(array.get(0))));
		}
		System.out.println("finish1");
		for(int i=0;i<paperCitationMap.keySet().size()*negativeRatio;i++){
			int nonLinkedIndex1= rand1.nextInt(paperList.size());
			int nonLinkedIndex2= rand2.nextInt(paperList.size());
			//System.out.println(i);
			if(nonLinkedIndex1!=nonLinkedIndex2){
				Set<String> nonlink= new LinkedHashSet<String> ();
				nonlink.add(paperList.get(nonLinkedIndex1));
				nonlink.add(paperList.get(nonLinkedIndex2));
				ArrayList<String> nonLink=new ArrayList<String>();
				nonLink.add(paperList.get(nonLinkedIndex1));
				nonLink.add(paperList.get(nonLinkedIndex2));
				if(!paperCitationMap.containsKey(nonlink)){
					loss+= -Math.log(1.0-getSigmoidValue(paperEmbedding_context.get(nonLink.get(1)),paperEmbedding.get(nonLink.get(0))));
				}else{i--;}
			}else{i--;}
		}
		System.out.println("finish2");

//		for(Set<String> pwLink: paperWordMap.keySet()){
//			ArrayList<String> array= new ArrayList<String>();
//			for(String s:pwLink){
//				array.add(s);
//				//System.out.println(s);
//			}
//			
//			loss+= -paperWordMap.get(pwLink)*Math.log(getSigmoidValue(wordEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//		}
//		System.out.println("finish3");
//		for(int i=0;i<paperWordMap.keySet().size()*negativeRatio;i++){
//			int nonLinkedIndex1=rand1.nextInt(paperList.size());
//			int nonLinkedIndex2=rand2.nextInt(wordList.size());
//			Set<String> nonlink= new LinkedHashSet<String> ();
//			nonlink.add(paperList.get(nonLinkedIndex1));
//			nonlink.add(wordList.get(nonLinkedIndex2));
//			ArrayList<String> nonLink=new ArrayList<String>();
//			nonLink.add(paperList.get(nonLinkedIndex1));
//			nonLink.add(wordList.get(nonLinkedIndex2));
//			if(!paperWordMap.containsKey(nonlink)){
//				loss+= -Math.log(1.0-getSigmoidValue(wordEmbedding.get(nonLink.get(1)),paperEmbedding.get(nonLink.get(0))));
//			}else{i--;}
//		}
//		System.out.println("finish4");
//
//		for(Set<String> ptLink:paperTopicMap.keySet()){
//			ArrayList<String> array= new ArrayList<String>();
//			for(String s:ptLink){
//				array.add(s);
//			}
//			loss+= -paperTopicMap.get(ptLink)*Math.log(getSigmoidValue(topicEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//		}
//		System.out.println("finish5");
//		for(int i=0;i<paperTopicMap.keySet().size()*negativeRatio;i++){
//			int nonLinkedIndex1= rand1.nextInt(paperList.size());
//			int nonLinkedIndex2= rand2.nextInt(topicList.size());
//			Set<String> nonlink= new LinkedHashSet<String> ();
//			nonlink.add(paperList.get(nonLinkedIndex1));
//			nonlink.add(topicList.get(nonLinkedIndex2));
//			ArrayList<String> nonLink=new ArrayList<String>();
//			nonLink.add(paperList.get(nonLinkedIndex1));
//			nonLink.add(topicList.get(nonLinkedIndex2));
//			if(!paperTopicMap.containsKey(nonlink)){
//				loss+= -Math.log(1.0-getSigmoidValue(topicEmbedding.get(nonLink.get(1)),paperEmbedding.get(nonLink.get(0))));
//			}else{i--;}
//		}
		System.out.println("finish6");

//		for(Set<String> wtLink:wordTopicMap.keySet()){
//			ArrayList<String> array= new ArrayList<String>();
//			for(String s:wtLink){
//				array.add(s);
//				//System.out.println(s);
//			}
//			// System.out.println(array.get(0));
//			// System.out.println(array.get(1)+"_");
//			loss+= -wordTopicMap.get(wtLink)*Math.log(getSigmoidValue(topicEmbedding.get(array.get(1)),wordEmbedding.get(array.get(0))));
//		}
//		System.out.println("finish7");
//		for(int i=0;i<wordTopicMap.keySet().size()*negativeRatio;i++){
//			int nonLinkedIndex1= rand1.nextInt(wordList.size());
//			int nonLinkedIndex2= rand2.nextInt(topicList.size());
//			Set<String> nonlink= new LinkedHashSet<String> ();
//			nonlink.add(wordList.get(nonLinkedIndex1));
//			nonlink.add(topicList.get(nonLinkedIndex2));
//			ArrayList<String> nonLink=new ArrayList<String>();
//			nonLink.add(wordList.get(nonLinkedIndex1));
//			nonLink.add(topicList.get(nonLinkedIndex2));
//			if(!wordTopicMap.containsKey(nonlink)){
//
//				loss+= -Math.log(1.0-getSigmoidValue(topicEmbedding.get(nonLink.get(1)),wordEmbedding.get(nonLink.get(0))));
//			}else{i--;} 
//		}
//		System.out.println("finish8");
		return loss;
	}

	public static double paperNetworkEmbeddingLearning(double lastLoss,Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double> paperWordMap,Map<Set<String>,Double> paperTopicMap,Map<Set<String>,Double>wordTopicMap,
		ArrayList<String> paperList,ArrayList<String> wordList,ArrayList<String>topicList,Map<String,double[]> paperEmbedding,Map<String,double[]> paperEmbedding_context,Map<String,double[]> wordEmbedding,
		Map<String,double[]> topicEmbedding,double learningRateInit,int negativeRatio){
		
		Map<String,double[]> papergradient = new HashMap<String,double[]>();
		Map<String,double[]> contextPapergradient = new HashMap<String,double[]>();
		Map<String,double[]> wordgradient = new HashMap<String,double[]>();
		Map<String,double[]> topicgradient = new HashMap<String,double[]>();
		
		for(Set<String> sequence:paperCitationMap.keySet()){
			double weight = paperCitationMap.get(sequence);
			ArrayList<String> array = new ArrayList<String>();
			for(String str: sequence){
				array.add(str);
			}
			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(paperEmbedding_context.get(array.get(1)),paperEmbedding.get(array.get(0))));
			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(paperEmbedding_context.get(array.get(0)),paperEmbedding.get(array.get(1))));
			
			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,paperEmbedding_context.get(array.get(1)));
			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,paperEmbedding.get(array.get(0)));
		
			if(papergradient.containsKey(array.get(0))){
				for(int i=0;i<papergradient.get(array.get(0)).length;i++){
					papergradient.get(array.get(0))[i] += linkedGradient_V[i];
				}
			}else{
				double[] tempgradient = new double[linkedGradient_V.length];
				for(int i=0;i<tempgradient.length;i++){
					tempgradient[i]= linkedGradient_V[i];
				}
				papergradient.put(array.get(0),tempgradient);
			}

			if(contextPapergradient.containsKey(array.get(1))){
				for(int i=0;i<contextPapergradient.get(array.get(1)).length;i++){
					contextPapergradient.get(array.get(1))[i] += linkedGradient_C[i];
				}
			}else{
				double[] tempgradient= new double[linkedGradient_C.length];
				for(int i=0;i<tempgradient.length;i++){
					tempgradient[i]=linkedGradient_C[i];
				}
				contextPapergradient.put(array.get(1),tempgradient);
			}
		}

		Random rand1 = new Random(System.currentTimeMillis());
		Random rand2 = new Random(System.currentTimeMillis()+10000);
		for(int t=0;t<paperCitationMap.keySet().size()*negativeRatio;t++){
			int nonLinkedIndex1 = rand1.nextInt(paperList.size());
			int nonLinkedIndex2 = rand2.nextInt(paperList.size());
			if(nonLinkedIndex1!= nonLinkedIndex2){
				double weight=1.0;
				Set<String> tempLink= new LinkedHashSet<String>();
				tempLink.add(paperList.get(nonLinkedIndex1));
				tempLink.add(paperList.get(nonLinkedIndex2));
				ArrayList<String> array = new ArrayList<String>();
				array.add(paperList.get(nonLinkedIndex1));
				array.add(paperList.get(nonLinkedIndex2));
				if(!paperCitationMap.containsKey(tempLink)){
					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(paperEmbedding_context.get(array.get(1)),paperEmbedding.get(array.get(0))));
					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(paperEmbedding_context.get(array.get(0)),paperEmbedding.get(array.get(1))));
					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,paperEmbedding_context.get(array.get(1)));
					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,paperEmbedding.get(array.get(0)));

					if(papergradient.containsKey(array.get(0))){
						for(int i=0;i<papergradient.get(array.get(0)).length;i++){
							papergradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
						}
					}else{
						double[] tempGradient= new double[nonlinkedGradient0.length];
						for(int i=0;i<tempGradient.length;i++){
							tempGradient[i]=nonlinkedGradient0[i];
						}
						papergradient.put(array.get(0),tempGradient);
					}
					if(contextPapergradient.containsKey(array.get(1))){
						for(int i=0;i<contextPapergradient.get(array.get(1)).length;i++){
							contextPapergradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
						}
					}else{
						double[] tempGradient= new double[nonlinkedGradient1.length];
						for(int i=0;i<tempGradient.length;i++){
							tempGradient[i]=nonlinkedGradient1[i];
						}
						contextPapergradient.put(array.get(1),tempGradient);
					}
  
				}else{t--;}
			}else{t--;}
		}

//		for(Set<String> sequence:paperWordMap.keySet()){
//			double weight = paperWordMap.get(sequence);
//			ArrayList<String> array = new ArrayList<String>();
//			for(String str: sequence){
//				array.add(str);
//			}
//			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(wordEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(wordEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1))));
//			
//			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,wordEmbedding.get(array.get(1)));
//			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,paperEmbedding.get(array.get(0)));
//		
//			if(papergradient.containsKey(array.get(0))){
//				for(int i=0;i<papergradient.get(array.get(0)).length;i++){
//					papergradient.get(array.get(0))[i] += linkedGradient_V[i];
//				}
//			}else{
//				double[] tempgradient = new double[linkedGradient_V.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]= linkedGradient_V[i];
//				}
//				papergradient.put(array.get(0),tempgradient);
//			}
//
//			if(wordgradient.containsKey(array.get(1))){
//				for(int i=0;i<wordgradient.get(array.get(1)).length;i++){
//					wordgradient.get(array.get(1))[i] += linkedGradient_C[i];
//				}
//			}else{
//				double[] tempgradient= new double[linkedGradient_C.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]=linkedGradient_C[i];
//				}
//				wordgradient.put(array.get(1),tempgradient);
//			}
//		}
//
//		for(int t=0;t<paperWordMap.keySet().size()*negativeRatio;t++){
//			int nonLinkedIndex1 = rand1.nextInt(paperList.size());
//			int nonLinkedIndex2 = rand2.nextInt(wordList.size());
//			if(nonLinkedIndex1!= nonLinkedIndex2){
//				double weight=1.0;
//				Set<String> tempLink= new LinkedHashSet<String>();
//				tempLink.add(paperList.get(nonLinkedIndex1));
//				tempLink.add(wordList.get(nonLinkedIndex2));
//				ArrayList<String> array = new ArrayList<String>();
//				array.add(paperList.get(nonLinkedIndex1));
//				array.add(wordList.get(nonLinkedIndex2));
//				if(!paperWordMap.containsKey(tempLink)){
//					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(wordEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(wordEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1))));
//					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,wordEmbedding.get(array.get(1)));
//					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,paperEmbedding.get(array.get(0)));
//
//					if(papergradient.containsKey(array.get(0))){
//						for(int i=0;i<papergradient.get(array.get(0)).length;i++){
//							papergradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient0.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient0[i];
//						}
//						papergradient.put(array.get(0),tempGradient);
//					}
//					if(wordgradient.containsKey(array.get(1))){
//						for(int i=0;i<wordgradient.get(array.get(1)).length;i++){
//							wordgradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient1.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient1[i];
//						}
//						wordgradient.put(array.get(1),tempGradient);
//					}
//  
//				}else{t--;}
//			}else{t--;}
//		}
//
//		for(Set<String> sequence:paperTopicMap.keySet()){
//			double weight = paperTopicMap.get(sequence);
//			ArrayList<String> array = new ArrayList<String>();
//			for(String str: sequence){
//				array.add(str);
//			}
//			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1))));
//			
//			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,topicEmbedding.get(array.get(1)));
//			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,paperEmbedding.get(array.get(0)));
//		
//			if(papergradient.containsKey(array.get(0))){
//				for(int i=0;i<papergradient.get(array.get(0)).length;i++){
//					papergradient.get(array.get(0))[i] += linkedGradient_V[i];
//				}
//			}else{
//				double[] tempgradient = new double[linkedGradient_V.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]= linkedGradient_V[i];
//				}
//				papergradient.put(array.get(0),tempgradient);
//			}
//
//			if(topicgradient.containsKey(array.get(1))){
//				for(int i=0;i<topicgradient.get(array.get(1)).length;i++){
//					topicgradient.get(array.get(1))[i] += linkedGradient_C[i];
//				}
//			}else{
//				double[] tempgradient= new double[linkedGradient_C.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]=linkedGradient_C[i];
//				}
//				topicgradient.put(array.get(1),tempgradient);
//			}
//		}
//
//		for(int t=0;t<paperTopicMap.keySet().size()*negativeRatio;t++){
//			int nonLinkedIndex1 = rand1.nextInt(paperList.size());
//			int nonLinkedIndex2 = rand2.nextInt(topicList.size());
//			if(nonLinkedIndex1!= nonLinkedIndex2){
//				double weight=1.0;
//				Set<String> tempLink= new LinkedHashSet<String>();
//				tempLink.add(paperList.get(nonLinkedIndex1));
//				tempLink.add(topicList.get(nonLinkedIndex2));
//				ArrayList<String> array = new ArrayList<String>();
//				array.add(paperList.get(nonLinkedIndex1));
//				array.add(topicList.get(nonLinkedIndex2));
//				if(!paperTopicMap.containsKey(tempLink)){
//					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(1)),paperEmbedding.get(array.get(0))));
//					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(0)),paperEmbedding.get(array.get(1))));
//					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,topicEmbedding.get(array.get(1)));
//					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,paperEmbedding.get(array.get(0)));
//
//					if(papergradient.containsKey(array.get(0))){
//						for(int i=0;i<papergradient.get(array.get(0)).length;i++){
//							papergradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient0.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient0[i];
//						}
//						papergradient.put(array.get(0),tempGradient);
//					}
//					if(topicgradient.containsKey(array.get(1))){
//						for(int i=0;i<topicgradient.get(array.get(1)).length;i++){
//							topicgradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient1.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient1[i];
//						}
//						topicgradient.put(array.get(1),tempGradient);
//					}
//  
//				}else{t--;}
//			}else{t--;}
//		}

//		for(Set<String> sequence:wordTopicMap.keySet()){
//			double weight = wordTopicMap.get(sequence);
//			ArrayList<String> array = new ArrayList<String>();
//			for(String str: sequence){
//				array.add(str);
//			}
//			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(1)),wordEmbedding.get(array.get(0))));
//			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(0)),wordEmbedding.get(array.get(1))));
//			
//			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,topicEmbedding.get(array.get(1)));
//			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,wordEmbedding.get(array.get(0)));
//		
//			if(wordgradient.containsKey(array.get(0))){
//				for(int i=0;i<wordgradient.get(array.get(0)).length;i++){
//					wordgradient.get(array.get(0))[i] += linkedGradient_V[i];
//				}
//			}else{
//				double[] tempgradient = new double[linkedGradient_V.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]= linkedGradient_V[i];
//				}
//				wordgradient.put(array.get(0),tempgradient);
//			}
//
//			if(topicgradient.containsKey(array.get(1))){
//				for(int i=0;i<topicgradient.get(array.get(1)).length;i++){
//					topicgradient.get(array.get(1))[i] += linkedGradient_C[i];
//				}
//			}else{
//				double[] tempgradient= new double[linkedGradient_C.length];
//				for(int i=0;i<tempgradient.length;i++){
//					tempgradient[i]=linkedGradient_C[i];
//				}
//				topicgradient.put(array.get(1),tempgradient);
//			}
//		}
//
//		for(int t=0;t<wordTopicMap.keySet().size()*negativeRatio;t++){
//			int nonLinkedIndex1 = rand1.nextInt(wordList.size());
//			int nonLinkedIndex2 = rand2.nextInt(topicList.size());
//			if(nonLinkedIndex1!= nonLinkedIndex2){
//				double weight=1.0;
//				Set<String> tempLink= new LinkedHashSet<String>();
//				tempLink.add(wordList.get(nonLinkedIndex1));
//				tempLink.add(topicList.get(nonLinkedIndex2));
//				ArrayList<String> array = new ArrayList<String>();
//				array.add(wordList.get(nonLinkedIndex1));
//				array.add(topicList.get(nonLinkedIndex2));
//				if(!wordTopicMap.containsKey(tempLink)){
//					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(1)),wordEmbedding.get(array.get(0))));
//					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(topicEmbedding.get(array.get(0)),wordEmbedding.get(array.get(1))));
//					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,topicEmbedding.get(array.get(1)));
//					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,wordEmbedding.get(array.get(0)));
//
//					if(wordgradient.containsKey(array.get(0))){
//						for(int i=0;i<wordgradient.get(array.get(0)).length;i++){
//							wordgradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient0.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient0[i];
//						}
//						wordgradient.put(array.get(0),tempGradient);
//					}
//					if(topicgradient.containsKey(array.get(1))){
//						for(int i=0;i<topicgradient.get(array.get(1)).length;i++){
//							topicgradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
//						}
//					}else{
//						double[] tempGradient= new double[nonlinkedGradient1.length];
//						for(int i=0;i<tempGradient.length;i++){
//							tempGradient[i]=nonlinkedGradient1[i];
//						}
//						topicgradient.put(array.get(1),tempGradient);
//					}
//  
//				}else{t--;}
//			}else{t--;}
//		}
		double updateLoss=paperBackTrackingLineSearch(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,paperList,wordList,topicList,paperEmbedding,paperEmbedding_context,wordEmbedding,topicEmbedding,
				papergradient,contextPapergradient,wordgradient,topicgradient,learningRateInit,negativeRatio);
		return updateLoss;

	}

	public static Map<String,double[]> initEmbedding(int embeddingLength,double embeddingInit,ArrayList<String> trainingList){
		Random rand = new Random(System.currentTimeMillis());
		Map<String, double[]> embeddingMap= new LinkedHashMap<String,double[]>();
		for (int i=0;i<trainingList.size();i++){
			double[] tempEmbedding = new double[embeddingLength];
			for(int t=0;t<embeddingLength;t++){
				tempEmbedding[t]= rand.nextDouble()*embeddingInit;
				//System.out.println(tempEmbedding[t]);
			}
			//System.out.println(trainingList.size());
			embeddingMap.put(trainingList.get(i),tempEmbedding);
		}
		return embeddingMap;
	}

	public static double authorNetworkEmbeddingLearning(double lastLoss, Map<Set<String>,Double> coauthorMap,ArrayList<String> authorList,
		Map<String, double[]> authorEmbedding,Map<String, double[]> authorEmbedding_context,double learningRateInit,int negativeRatio){

		Map<String,double[]> authorgradient = new HashMap<String,double[]>();
		Map<String,double[]> contextAuthorgradient = new HashMap<String,double[]>();
		for(Set<String> sequence:coauthorMap.keySet()){
			double weight = coauthorMap.get(sequence);
			ArrayList<String> array = new ArrayList<String>();
			for(String str: sequence){
				array.add(str);
			}
			double linkedExponential= Math.pow(Math.E,-getVectorInnerProduct(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));
			//double linkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(authorEmbedding_context.get(array.get(0)),authorEmbedding.get(array.get(1))));
			
			double[] linkedGradient_V= getNetworkLinkedPartialDerivative(weight,linkedExponential,authorEmbedding_context.get(array.get(1)));
			double[] linkedGradient_C= getNetworkLinkedPartialDerivative(weight,linkedExponential,authorEmbedding.get(array.get(0)));
		
			if(authorgradient.containsKey(array.get(0))){
				for(int i=0;i<authorgradient.get(array.get(0)).length;i++){
					authorgradient.get(array.get(0))[i] += linkedGradient_V[i];
				}
			}else{
				double[] tempgradient = new double[linkedGradient_V.length];
				for(int i=0;i<tempgradient.length;i++){
					tempgradient[i]= linkedGradient_V[i];
				}
				authorgradient.put(array.get(0),tempgradient);
			}

			if(contextAuthorgradient.containsKey(array.get(1))){
				for(int i=0;i<contextAuthorgradient.get(array.get(1)).length;i++){
					contextAuthorgradient.get(array.get(1))[i] += linkedGradient_C[i];
				}
			}else{
				double[] tempgradient= new double[linkedGradient_C.length];
				for(int i=0;i<tempgradient.length;i++){
					tempgradient[i]=linkedGradient_C[i];
				}
				contextAuthorgradient.put(array.get(1),tempgradient);
			}
		}

		Random rand1 = new Random(System.currentTimeMillis());
		Random rand2 = new Random(System.currentTimeMillis()+10000);
		for(int t=0;t<coauthorMap.keySet().size()*negativeRatio;t++){
			int nonLinkedIndex1 = rand1.nextInt(authorList.size());
			int nonLinkedIndex2 = rand2.nextInt(authorList.size());
			if(nonLinkedIndex1!= nonLinkedIndex2){
				double weight=1.0;
				Set<String> tempLink= new LinkedHashSet<String>();
				tempLink.add(authorList.get(nonLinkedIndex1));
				tempLink.add(authorList.get(nonLinkedIndex2));
				ArrayList<String> array = new ArrayList<String>();
				array.add(authorList.get(nonLinkedIndex1));
				array.add(authorList.get(nonLinkedIndex2));
				if(!coauthorMap.containsKey(tempLink)){
					double nonlinkedExponential= Math.pow(Math.E,-getVectorInnerProduct(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));
					//double nonlinkedExponential1= Math.pow(Math.E,-getVectorInnerProduct(authorEmbedding_context.get(array.get(0)),authorEmbedding.get(array.get(1))));
					double[] nonlinkedGradient0= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,authorEmbedding_context.get(array.get(1)));
					double[] nonlinkedGradient1= getNetworknonLinkedPartialDerivative(weight,nonlinkedExponential,authorEmbedding.get(array.get(0)));

					if(authorgradient.containsKey(array.get(0))){
						for(int i=0;i<authorgradient.get(array.get(0)).length;i++){
							authorgradient.get(array.get(0))[i]+= nonlinkedGradient0[i];
						}
					}else{
						double[] tempGradient= new double[nonlinkedGradient0.length];
						for(int i=0;i<tempGradient.length;i++){
							tempGradient[i]=nonlinkedGradient0[i];
						}
						authorgradient.put(array.get(0),tempGradient);
					}
					if(contextAuthorgradient.containsKey(array.get(1))){
						for(int i=0;i<contextAuthorgradient.get(array.get(1)).length;i++){
							contextAuthorgradient.get(array.get(1))[i]+= nonlinkedGradient1[i];
						}
					}else{
						double[] tempGradient= new double[nonlinkedGradient1.length];
						for(int i=0;i<tempGradient.length;i++){
							tempGradient[i]=nonlinkedGradient1[i];
						}
						contextAuthorgradient.put(array.get(1),tempGradient);
					}
  
				}else{t--;}
			}else{t--;}
		}
		double updateLoss=authorBacktrackingLineSearch(lastLoss, coauthorMap, authorList, authorEmbedding,authorEmbedding_context,
				authorgradient,contextAuthorgradient, learningRateInit, negativeRatio);

		return updateLoss;
	}

	public static double getAuthorNetworkLoss(Map<Set<String>,Double> coauthorMap,ArrayList<String> authorList,Map<String, double[]>authorEmbedding,
		Map<String, double[]> authorEmbedding_context,int negativeRatio){
		double loss= 0.0;
		Random rand1 = new Random(System.currentTimeMillis());
		Random rand2 = new Random(System.currentTimeMillis()+10000);

		for(Set<String> link : coauthorMap.keySet()){
			
			ArrayList<String> array=new ArrayList<String>();
			for(String str:link){
				array.add(str);
			}
			//System.out.println(authorEmbedding.keySet().size());
			//System.out.println(authorEmbedding_context.keySet().size());
			loss += - coauthorMap.get(link)*Math.log(getSigmoidValue(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));
		}
		for(int i=0;i<coauthorMap.keySet().size()*negativeRatio;i++){
			int nonLinkedIndex1 = rand1.nextInt(authorList.size());
			int nonLinkedIndex2 = rand2.nextInt(authorList.size());
			if(nonLinkedIndex1 != nonLinkedIndex2)
			{
				Set<String> tempLink= new LinkedHashSet<String>();
				tempLink.add(authorList.get(nonLinkedIndex1));
				tempLink.add(authorList.get(nonLinkedIndex2));
				ArrayList<String> array= new ArrayList<String>();
				array.add(authorList.get(nonLinkedIndex1));
				array.add(authorList.get(nonLinkedIndex2));
				if(!coauthorMap.containsKey(tempLink)){
					loss += -Math.log(1.0-getSigmoidValue(authorEmbedding_context.get(array.get(1)),authorEmbedding.get(array.get(0))));
				}else{i--;}
			}else{i--;}
		}
		//regular 
		return loss;
	}

	public static double getSigmoidValue(double[] vector1, double[] vector2){
		double value = 1.0 / (1.0+ Math.pow(Math.E, - getVectorInnerProduct(vector1,vector2)));
		return value;
	}
	public static double getVectorInnerProduct(double[] vector1, double[] vector2){
		double sum =0.0;
		for(int i=0;i<vector1.length;i++){
			sum +=vector1[i]*vector2[i];
		}
		return sum;
	}
	public static double[] getNetworkLinkedPartialDerivative(double weight, double exponential, double[] vector){
		double[] derivative = new double[vector.length];

		for (int i = 0; i < derivative.length; i++) {
			derivative[i] = -weight * exponential / (1.0 + exponential) * vector[i];
		}

		return derivative;
	}
	public static double[] getNetworknonLinkedPartialDerivative(double weight,double exponential,double[] vector){
		double[] derivative = new double[vector.length];

		for (int i = 0; i < derivative.length; i++) {
			derivative[i] = weight / (1.0 + exponential) * vector[i];
		}

		return derivative;
	}

	public static double paperBackTrackingLineSearch(double lastLoss,Map<Set<String>,Double>paperCitationMap,Map<Set<String>,Double>paperWordMap,
		Map<Set<String>,Double>paperTopicMap,Map<Set<String>,Double> wordTopicMap,ArrayList<String>paperList,ArrayList<String>wordList,
		ArrayList<String>topicList,Map<String,double[]>paperEmbedding,Map<String,double[]>paperEmbedding_context,Map<String,double[]>wordEmbedding,
		Map<String,double[]>topicEmbedding, Map<String,double[]> papergradient,Map<String,double[]>contextPapergradient,Map<String,double[]>wordgradient,
		Map<String,double[]> topicgradient,double learningRateInit,int negativeRatio){

		for (String sequence : papergradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < papergradient.get(sequence).length; i++) {
				normalization += Math.pow(papergradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < papergradient.get(sequence).length; i++) {
				papergradient.get(sequence)[i] /= normalization;
			}
		}
		for (String sequence : contextPapergradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < contextPapergradient.get(sequence).length; i++) {
				normalization += Math.pow(contextPapergradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < contextPapergradient.get(sequence).length; i++) {
				contextPapergradient.get(sequence)[i] /= normalization;
			}
		}
		for (String sequence : wordgradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < wordgradient.get(sequence).length; i++) {
				normalization += Math.pow(wordgradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < wordgradient.get(sequence).length; i++) {
				wordgradient.get(sequence)[i] /= normalization;
			}
		}
		for (String sequence : topicgradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < topicgradient.get(sequence).length; i++) {
				normalization += Math.pow(topicgradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < topicgradient.get(sequence).length; i++) {
				topicgradient.get(sequence)[i] /= normalization;
			}
		}
		double updatePoint=0.0;
		boolean found=false;
		int trackingTimes=0;
		while(!found){
			for(String sequence: papergradient.keySet()){
				for(int i=0;i<papergradient.get(sequence).length;i++){
					paperEmbedding.get(sequence)[i]-=learningRateInit*papergradient.get(sequence)[i];
				}
			}
			for(String sequence: contextPapergradient.keySet()){
				for(int i=0;i<contextPapergradient.get(sequence).length;i++){
					paperEmbedding_context.get(sequence)[i]-=learningRateInit*contextPapergradient.get(sequence)[i];
				}
			}
			for(String sequence: wordgradient.keySet()){
				for(int i=0;i<wordgradient.get(sequence).length;i++){
					wordEmbedding.get(sequence)[i]-=learningRateInit*wordgradient.get(sequence)[i];
				}
			}
			for(String sequence: topicgradient.keySet()){
				for(int i=0;i<topicgradient.get(sequence).length;i++){
					topicEmbedding.get(sequence)[i]-=learningRateInit*topicgradient.get(sequence)[i];
				}
			}
			updatePoint=getPaperNetworkLoss(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,paperList,wordList,topicList,paperEmbedding,paperEmbedding_context,wordEmbedding,topicEmbedding,negativeRatio);
			if(updatePoint<lastLoss){
				found=true;
			}else{
				for(String sequence:papergradient.keySet()){
					for(int i=0;i<papergradient.get(sequence).length;i++){
						paperEmbedding.get(sequence)[i]+=learningRateInit*papergradient.get(sequence)[i];
					}
				}
				for(String seq : contextPapergradient.keySet()){
					for(int i=0;i<contextPapergradient.get(seq).length;i++){
						paperEmbedding_context.get(seq)[i]+= learningRateInit*contextPapergradient.get(seq)[i];
					}
				}
				for(String seq : wordgradient.keySet()){
					for(int i=0;i<wordgradient.get(seq).length;i++){
						wordEmbedding.get(seq)[i]+= learningRateInit*wordgradient.get(seq)[i];
					}
				}
				for(String seq : topicgradient.keySet()){
					for(int i=0;i<topicgradient.get(seq).length;i++){
						topicEmbedding.get(seq)[i]+= learningRateInit*topicgradient.get(seq)[i];
					}
				}
				learningRateInit/=2.0;
				if (trackingTimes <= 3) {
					trackingTimes++;
				} else {
					found = true;
				}
			}
		}
		return updatePoint;

	}
	public static double authorBacktrackingLineSearch(double lastLoss, Map<Set<String>,Double> coauthorMap,ArrayList<String> authorList,
		Map<String,double[]> authorEmbedding, Map<String,double[]> authorEmbedding_context,Map<String,double[]> authorgradient,
		Map<String,double[]>contextAuthorgradient,double learningRateInit,int negativeRatio){
		
		for (String sequence : authorgradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < authorgradient.get(sequence).length; i++) {
				normalization += Math.pow(authorgradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < authorgradient.get(sequence).length; i++) {
				authorgradient.get(sequence)[i] /= normalization;
			}
		}
		for (String sequence : contextAuthorgradient.keySet()) {
			double normalization = 0.0;
			for (int i = 0; i < contextAuthorgradient.get(sequence).length; i++) {
				normalization += Math.pow(contextAuthorgradient.get(sequence)[i], 2.0);
			}
			normalization = Math.sqrt(normalization);
			for (int i = 0; i < contextAuthorgradient.get(sequence).length; i++) {
				contextAuthorgradient.get(sequence)[i] /= normalization;
			}
		}

		double updatePoint =0.0;
		boolean found= false;
		int trackingTimes=0;
		while(!found){
			for(String sequence:authorgradient.keySet()){
				for(int i=0;i<authorgradient.get(sequence).length;i++){
					authorEmbedding.get(sequence)[i]-=learningRateInit*authorgradient.get(sequence)[i];
				}
			}
			for(String seq : contextAuthorgradient.keySet()){
				for(int i=0;i<contextAuthorgradient.get(seq).length;i++){
					authorEmbedding_context.get(seq)[i]-= learningRateInit*contextAuthorgradient.get(seq)[i];
				}
			}
			updatePoint = getAuthorNetworkLoss(coauthorMap,authorList,authorEmbedding,authorEmbedding_context,negativeRatio);
			if(updatePoint<= lastLoss){
				found=true;
			}else{
				for(String sequence:authorgradient.keySet()){
					for(int i=0;i<authorgradient.get(sequence).length;i++){
						authorEmbedding.get(sequence)[i]+=learningRateInit*authorgradient.get(sequence)[i];
					}
				}
				for(String seq : contextAuthorgradient.keySet()){
					for(int i=0;i<contextAuthorgradient.get(seq).length;i++){
						authorEmbedding_context.get(seq)[i]+= learningRateInit*contextAuthorgradient.get(seq)[i];
					}
				}
				learningRateInit/=2.0;
				if (trackingTimes <= 3) {
					trackingTimes++;
				} else {
					found = true;
				}

			}
		}
		return updatePoint;
	}

	public static boolean overallConverge(Map<String, Integer> convergeMap) {
		boolean converge = false;
		int convergeNum = 0;
		for (String sequence : convergeMap.keySet()) {
			convergeNum += convergeMap.get(sequence);
		}
		if (convergeMap.containsKey("Author") && convergeMap.containsKey("Paper") && convergeMap.containsKey("Matrix")) {
			if (convergeNum >= 4) {
				converge = true;
			}
		}

		return converge;

	}

	public static double getInterLogisticValue(double[] vector1, double[][] embeddingMatrix, double[] vector2) {
		double value = 1.0 / (1.0 + Math.pow(Math.E, -getVectorMatrixProduct(vector1, embeddingMatrix, vector2)));
		return value;
	}

	public static double getVectorMatrixProduct(double[] vector1, double[][] embeddingMatrix, double[] vector2) {
		double value = 0.0;

		double[] intermediaResult = new double[embeddingMatrix[0].length];
		for (int i = 0; i < intermediaResult.length; i++) {
			double[] column = new double[embeddingMatrix.length];
			for (int c = 0; c < column.length; c++) {
				column[c] = embeddingMatrix[c][i];
			}
			intermediaResult[i] = getVectorInnerProduct(vector1, column);
		}

		value = getVectorInnerProduct(intermediaResult, vector2);

		return value;

	}

	public static double[][] getLinkedEmbeddingMatrixPartialDerivative (double weight,double exponential,double[] authorEmbedding,double[] paperEmbedding){
		double[][] derivative = new double[authorEmbedding.length][paperEmbedding.length];
		for (int i = 0; i < derivative.length; i++) {
			for (int j = 0; j < derivative[0].length; j++) {
				derivative[i][j] = -weight * exponential/(1.0+exponential)  * authorEmbedding[i] * paperEmbedding[j];
			}
		}

		return derivative;
	}

	public static double[][] getNonlinkedEmbeddingMatrixPartialDerivative(double exponential, double[] authorEmbedding,
			double[] paperEmbedding) {
		double[][] derivative = new double[authorEmbedding.length][paperEmbedding.length];
		// double prob = 1.0 / (1.0 + exponential);
		for (int i = 0; i < derivative.length; i++) {
			for (int j = 0; j < derivative[0].length; j++) {
				derivative[i][j] = 1.0 / (1.0+ exponential) * authorEmbedding[i]* paperEmbedding[j];
			}
		}

		return derivative;
	}
	public static double[] getLinkedAuthorDerivative(double weight,double exponential,double[][] embeddingMatrix,double[] paperEmbedding){

		double[] derivative = new double[embeddingMatrix.length];
		for (int d = 0; d < derivative.length; d++) {
			double derivativeConst = 0.0;
			for (int i = 0; i < paperEmbedding.length; i++) {
				derivativeConst += embeddingMatrix[d][i] * paperEmbedding[i];
			}
			derivative[d] = -weight * exponential/ (1.0 + exponential) * derivativeConst;
		}

		return derivative;
	}
	public static double[] getNonLinkedAuthorDerivative(double weight,double exponential,double[][] embeddingMatrix,double[] paperEmbedding){

		double[] derivative = new double[embeddingMatrix.length];
		for (int d = 0; d < derivative.length; d++) {
			double derivativeConst = 0.0;
			for (int i = 0; i < paperEmbedding.length; i++) {
				derivativeConst += embeddingMatrix[d][i] * paperEmbedding[i];
			}
			derivative[d] = weight / (1+exponential) * derivativeConst;
		}

		return derivative;
	}

	 public static double[] getLinkedPaperDerivative(double weight,double exponential,double[][] embeddingMatrix,double[] authorEmbedding){

		double[] derivative = new double[embeddingMatrix[0].length];
		for (int d = 0; d < derivative.length; d++) {
			double derivativeConst = 0.0;
			for (int i = 0; i < authorEmbedding.length; i++) {
				derivativeConst += embeddingMatrix[i][d] * authorEmbedding[i];
			}
			derivative[d] = -weight * exponential/ (1+exponential)  * derivativeConst;
		}

		return derivative;
	 }

	 public static double[] getNonLinkedPaperDerivative(double weight,double exponential,double[][] embeddingMatrix,double[] authorEmbedding){

		double[] derivative = new double[embeddingMatrix[0].length];
		for (int d = 0; d < derivative.length; d++) {
			double derivativeConst = 0.0;
			for (int i = 0; i < authorEmbedding.length; i++) {
				derivativeConst += embeddingMatrix[i][d] * authorEmbedding[i];
			}
			derivative[d] = weight / (1.0+exponential) * derivativeConst;
		}

		return derivative;
	 }

	

	public static ArrayList<Map<String,double[]>> authorNetworkEmbeddingList(Map<Set<String>,Double> coauthorMap, ArrayList<String> authorList,
			int embeddingLength, double embeddingInit, double learningRateInit, int negativeRatio){

		//System.out.println(authorList.size());
		ArrayList<Map<String,double[]>> mapList = new ArrayList<Map<String,double[]>>();
		Map<String,double[]> authorEmbedding = initEmbedding(embeddingLength,embeddingInit,authorList);
		Map<String,double[]> authorEmbedding_context = initEmbedding(embeddingLength,embeddingInit,authorList);
		//System.out.println(authorEmbedding.get("A85801")[1]);



		double lastLoss= getAuthorNetworkLoss(coauthorMap,authorList,authorEmbedding,authorEmbedding_context,negativeRatio);
		System.out.println("last loss=" + lastLoss);
		boolean converge=false;
		long startTime =System.currentTimeMillis();
		int times=1;
		while(!converge){
			double currentLoss= authorNetworkEmbeddingLearning(lastLoss,coauthorMap,authorList,authorEmbedding,authorEmbedding_context,
				learningRateInit,negativeRatio);
			times++;
			System.out.println("current loss=" + currentLoss);
			if(Math.abs(currentLoss- lastLoss)/lastLoss<=0.01||times>10){
				converge= true;
			}else{
				lastLoss= currentLoss;
			}
		}
		long endTime= System.currentTimeMillis();
		System.out.println("time difference =" +(endTime - startTime)/1000);
		String authorEmbeddingFile =linuxpath+"author(NECoRank-T)1.txt";
		File authorEmbeddingfile= new File(authorEmbeddingFile);
		FileWriter fwAuthorEmbedding;
		try{
			fwAuthorEmbedding= new FileWriter(authorEmbeddingfile.getAbsoluteFile());
			BufferedWriter bwAuthorEmbedding = new BufferedWriter(fwAuthorEmbedding);
			for(String sequence: authorEmbedding.keySet()){
				bwAuthorEmbedding.write(sequence+",");
				double[] embedding = authorEmbedding.get(sequence);
				for(int i= 0; i<embedding.length;i++){
					bwAuthorEmbedding.write(String.valueOf(embedding[i]+","));
				}
				bwAuthorEmbedding.newLine();
			}
			bwAuthorEmbedding.flush();
			bwAuthorEmbedding.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		mapList.add(authorEmbedding);
		mapList.add(authorEmbedding_context);
		return mapList;
	}
	public static ArrayList<Map<String,double[]>> paperNetworkEmbeddingList(Map<Set<String>,Double> paperCitationMap,Map<Set<String>,Double> paperWordMap,
		Map<Set<String>,Double> paperTopicMap,Map<Set<String>,Double> wordTopicMap,ArrayList<String> paperList,ArrayList<String> wordList,
		ArrayList<String> topicList, int embeddingLength, double embeddingInit,double learningRateInit,int negativeRatio){

		ArrayList<Map<String,double[]>> mapList = new ArrayList<Map<String,double[]>>();
		Map<String,double[]> paperEmbedding=initEmbedding(embeddingLength,embeddingInit,paperList);
		Map<String,double[]> paperEmbedding_context= initEmbedding(embeddingLength,embeddingInit,paperList);
		Map<String,double[]> wordEmbedding= initEmbedding(embeddingLength,embeddingInit,wordList);
		Map<String,double[]> topicEmbedding= initEmbedding(embeddingLength,embeddingInit,topicList);

		double lastLoss= getPaperNetworkLoss(paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,paperList,wordList,topicList,paperEmbedding,paperEmbedding_context,wordEmbedding,topicEmbedding,negativeRatio);
		System.out.println("last loss= "+ lastLoss);
		boolean converge=false;
		long startTime=System.currentTimeMillis();
		int times=1;
		while(!converge){
			double currentLoss= paperNetworkEmbeddingLearning(lastLoss,paperCitationMap,paperWordMap,paperTopicMap,wordTopicMap,paperList,wordList,topicList,paperEmbedding,paperEmbedding_context,wordEmbedding,topicEmbedding,
				learningRateInit,negativeRatio);
			times++;
			System.out.println("current loss=" + currentLoss);
			if(Math.abs(currentLoss- lastLoss)/lastLoss<=0.01||times>=10){
				converge= true;
			}else{
				lastLoss= currentLoss;
			}
		}
		long endTime= System.currentTimeMillis();
		System.out.println("time difference =" +(endTime - startTime)/1000);
		String paperEmbeddingFile =linuxpath+"paper(NECoRank-T)1.txt";
		File paperEmbeddingfile= new File(paperEmbeddingFile);
		FileWriter fwPaperEmbedding;
		try{
			fwPaperEmbedding= new FileWriter(paperEmbeddingfile.getAbsoluteFile());
			BufferedWriter bwPaperEmbedding = new BufferedWriter(fwPaperEmbedding);
			for(String sequence: paperEmbedding.keySet()){
				bwPaperEmbedding.write(sequence+",");
				double[] embedding = paperEmbedding.get(sequence);
				for(int i= 0; i<embedding.length;i++){
					bwPaperEmbedding.write(String.valueOf(embedding[i]+","));
				}
				bwPaperEmbedding.newLine();
			}
			bwPaperEmbedding.flush();
			bwPaperEmbedding.close();
		}catch(IOException e){
			e.printStackTrace();
		}

		mapList.add(paperEmbedding);
		mapList.add(paperEmbedding_context);
		mapList.add(wordEmbedding);
		mapList.add(topicEmbedding);
		return mapList;
	}
}