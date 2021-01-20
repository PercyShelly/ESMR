package metric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import ranking.CoRank;

public class MetricNDCG {

	private HashMap<String,ArrayList<PaperRank>> groundpaper;
	private HashMap<String,ArrayList<AuthorRank>> groundauthor;
	private HashMap<String,ArrayList<PaperValue>> ourpaperlist;
	private HashMap<String,ArrayList<AuthorValue>> ourauthorlist;
	private HashMap<String,String> pyfmap;//paper_year_field
	private HashMap<String,String> ayfmap;//author_year_field
	public MetricNDCG() {
		this.groundpaper=new HashMap<String,ArrayList<PaperRank>>();
		this.groundauthor=new HashMap<String,ArrayList<AuthorRank>>();
		this.ourpaperlist=new HashMap<String,ArrayList<PaperValue>>();
		this.ourauthorlist=new HashMap<String,ArrayList<AuthorValue>>();
		this.pyfmap=new HashMap<String,String>();
		this.ayfmap=new HashMap<String,String>();
	}
	public void initializePYF(String pyfFile) {
		File file=new File(pyfFile);
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String line=null;
			while((line=br.readLine())!=null) {
				String[] array=line.split(",");
				String yf="1";
//				String yf=array[1]+","+array[2];//year+field
//				String yf=array[1];//only year
//				String yf=array[2];//only field
				pyfmap.put(array[0], yf);
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void initializeAYF(String ayfFile) {
		File file=new File(ayfFile);
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String line=null;
			while((line=br.readLine())!=null) {
				String[] array=line.split(",");
				String yf="1";
//				String yf=array[1]+","+array[2];//year+field
//				String yf=array[1];//only year
//				String yf=array[2];//only field
				ayfmap.put(array[0], yf);
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void initializePGT(String paperGTfile) {
		File pfile=new File(paperGTfile);
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(pfile),"utf-8"));
			String line=null;
			while((line=br.readLine())!=null) {
				String[] array=line.split(",");
				String pid=array[0];
				int citations=Integer.parseInt(array[1]);
				int year=1;
//				int year=Integer.parseInt(array[2]);
//				String field=array[3];//array[3]或者"field"
				String field="field";
				String key="1";
//				String key=array[2]+","+array[3];
//				String key=array[2];//year
//				String key=array[3];//field
				PaperRank paper=new PaperRank();
				paper.initialize(pid, citations, year, field);
				if(groundpaper.containsKey(key)) {
					groundpaper.get(key).add(paper);
				}else {
					ArrayList<PaperRank> plist=new ArrayList<PaperRank>();
					plist.add(paper);
					groundpaper.put(key, plist);
				}
			}
			br.close();
			System.out.println(groundpaper.size());
			
			for(String k:groundpaper.keySet()) {
				ArrayList<PaperRank> list=groundpaper.get(k);
//				System.out.println(list.size());
//				for(int i=0;i<list.size();i++) {
//					System.out.println(list.get(i).getCitations());
//				}
//				System.out.println("0000");
				Collections.sort(list, new Comparator<PaperRank>() {
					public int compare(PaperRank a, PaperRank b)
		            {
						if (b.getCitations()>a.getCitations()) {
							return 1;
						}else {
							return -1;
						}						
//		                return (b.getCitations()).compareTo(a.getCitations());
		            } 
				});
			}
//			System.out.println(groundpaper.get("1998,DB").get(0).getCitations());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void initializeAGT(String authorGTfile) {
		File afile=new File(authorGTfile);
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(afile),"utf-8"));
			String line=null;
			while((line=br.readLine())!=null) {
				String[] array=line.split(",");
				String aid=array[0];
				int citations=Integer.parseInt(array[1]);
				int year=1;
//				int year=Integer.parseInt(array[2]);
//				String field=array[3];//"field"array[3]
				String field="field";
				String key="1";
//				String key=array[2]+","+array[3];
//				String key=array[3];//field
//  				String key=array[2];//year
				AuthorRank author=new AuthorRank();
				author.initialize(aid, citations, year, field);
				if(groundauthor.containsKey(key)) {
					groundauthor.get(key).add(author);
					
				}else {
					ArrayList<AuthorRank> alist=new ArrayList<AuthorRank>();
					alist.add(author);
					groundauthor.put(key, alist);
				}
			}
			br.close();
			System.out.println(groundauthor.size());
			for(String s:groundauthor.keySet()) {
				ArrayList<AuthorRank> list=groundauthor.get(s);
				Collections.sort(list, new Comparator<AuthorRank>() {
					public int compare(AuthorRank a,AuthorRank b) {
						if(b.getCitations()>a.getCitations())
							return 1;
						return -1;
					}
				});
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void initializePL(String pResult) {
		File file=new File(pResult);
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String line=null;
			while((line=br.readLine())!=null) {
				String[] array=line.split(",");
				double rankvalue=Double.parseDouble(array[1]);
//				double rankvalue=Double.parseDouble(array[3]);
				String key=this.pyfmap.get(array[0]);
				PaperValue paper=new PaperValue();
				paper.initialize(array[0], rankvalue);
				if(ourpaperlist.containsKey(key)) {
					ourpaperlist.get(key).add(paper);
				}else {
					ArrayList<PaperValue> list=new ArrayList<PaperValue>();
					list.add(paper);
					ourpaperlist.put(key, list);
				}
			}
			br.close();
			for(String key:ourpaperlist.keySet()) {
				ArrayList<PaperValue> list=ourpaperlist.get(key);
				Collections.sort(list, new Comparator<PaperValue>() {
					public int compare(PaperValue a,PaperValue b) {
						if(b.getValue()>a.getValue())
							return 1;
						return -1;
					}
				});
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void initializeAL(String aResult) {
		File file=new File(aResult);
		try {
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String line=null;
			while((line=br.readLine())!=null) {
				String[] array=line.split(",");
				double rankvalue=Double.parseDouble(array[1]);
				String key=this.ayfmap.get(array[0]);
				AuthorValue author=new AuthorValue();
				author.initialize(array[0], rankvalue);
//				System.out.println(key);
				if(ourauthorlist.containsKey(key)) {
					ourauthorlist.get(key).add(author);
				}else {
					ArrayList<AuthorValue> list=new ArrayList<AuthorValue>();
					list.add(author);
					ourauthorlist.put(key, list);
				}
				
			}
			for(String key:ourauthorlist.keySet()) {
				ArrayList<AuthorValue> list=ourauthorlist.get(key);
				Collections.sort(list, new Comparator<AuthorValue>() {
					public int compare(AuthorValue a,AuthorValue b) {
						if(b.getValue()>a.getValue())
							return 1;
						return -1;
					}
				});
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void calculateRI_P(int k) {
		
		for(String key:ourpaperlist.keySet()) {
//			String[] arr=key.split(",");
//			if(arr[1].equals("AI")) {
				double dcg=0.0;
				double ndcg=0.0;
				double RI=0.0;
				ArrayList<PaperRank> groundlist=groundpaper.get(key);
				HashSet<String> set=new HashSet<String>();
				if(groundlist.size()>=k) {
					for(int i=0;i<k;i++) {
						set.add(groundlist.get(i).getId());
					}
				}
				ArrayList<PaperValue> ourranklist=ourpaperlist.get(key);
				double idcg=0.0;
				if(ourranklist.size()>=k) {
					int a=0;
					for(int j=0;j<k;j++) {
						String pid=ourranklist.get(j).getID();
						
						idcg+=1.0/Math.log(j+2);
						if(set.contains(pid)) {
//							if(key.indexOf("2007,AI")!=-1) {
//								System.out.println(pid+","+j);
	//						}
							
							a++;
							double cg=1.0/Math.log(j+2);
							double d=1.0*(k-j-1)/k;
							dcg+=cg;
							RI+=1+d;
						}
						
					}
					ndcg=dcg/idcg;
					System.out.print("ndcg:");
					System.out.print(ndcg);
					System.out.print("\n");
					if(key.indexOf("AI")!=-1||key.indexOf("DB")!=-1)
					{
						if(key.split(",")[0].equals("2001")||key.split(",")[0].equals("2003")||key.split(",")[0].equals("2005")||key.split(",")[0].equals("2007"))
						{
							System.out.println(key+","+ndcg+","+a);
							System.out.println(key+","+RI+","+a);
						}
					}
					//if(key.indexOf("AI")!=-1)
						//System.out.println(key+","+RI+","+a+","+ourranklist.size());
					//ndcg=dcg/idcg;
//					if(key.split(",")[0].equals("2001")||key.split(",")[0].equals("2003")||key.split(",")[0].equals("2005")||key.split(",")[0].equals("2007"))
//					{
//						System.out.println(key+","+ndcg+","+a);
//						System.out.println(key+","+RI+","+a);
//					}
					//int ky=Integer.valueOf(key);
					
					//if(ky>=2007)
					//System.out.println(dcg+","+idcg);
//					System.out.println(key+","+ndcg+","+a);
//						System.out.println(key+","+RI+","+a);
				}
				
//			}
			
		}
	}
	public void calculateRI_A(int k) {
//		System.out.println(ourauthorlist.keySet().size());
		for(String key:ourauthorlist.keySet()) {
			double RI=0;
			double dcg=0.0;
			double ndcg=0.0;
			double idcg=0.0;
			ArrayList<AuthorRank> groundlist=groundauthor.get(key);
			HashSet<String> set=new HashSet<String>();
//			System.out.println(key);
			if(groundlist!=null&&groundlist.size()>=k) {
				for(int i=0;i<k;i++) {
					set.add(groundlist.get(i).getId());
				}
			}
			ArrayList<AuthorValue> ourranklist=ourauthorlist.get(key);
			int a=0;
			if(ourranklist.size()>=k) {
				for(int j=0;j<k;j++) {
					String aid=ourranklist.get(j).getID();
					idcg+=1.0/Math.log(j+2);
					//System.out.println(idcg);
					if(set.contains(aid)) {
						a++;
						double cg=1.0/Math.log(j+2);
						double d=1.0*(k-j-1)/k;
						dcg+=cg;
						RI+=1+d;
					}
					
				}
			}
			ndcg=dcg/idcg;//key.split(",")[0].equals("2002")||key.split(",")[0].equals("2004")||key.split(",")[0].equals("2006")||key.split(",")[0].equals("2008")
			System.out.print("ndcg:");
			System.out.print(ndcg);
			System.out.print("\n");
//			if(key.split(",")[1].equals("DB")||key.split(",")[1].equals("AI")) {
//				if(key.split(",")[0].equals("2008"))
//				{
//					System.out.println(key+","+ndcg+","+a);
//					System.out.println(key+","+RI+","+a);
//				}
//
//			}
//				if(key.split(",")[0].equals("2002")||key.split(",")[0].equals("2004")||key.split(",")[0].equals("2006")||key.split(",")[0].equals("2008"))
			//int ky=Integer.valueOf(key);
			//if(ky>=1998)
			
			//System.out.println(dcg+","+idcg);
//				System.out.println(key+","+ndcg+","+a);
//				System.out.println(key+","+RI+","+a);
			//}
			//if(key.indexOf("AI")!=-1||key.indexOf("DB")!=-1)
				//System.out.println(key+","+RI+","+a+","+ourranklist.size());
				
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		int n=20;
		String k="1-20";
//		String paperfile="D:/hjn/结果/向量/paper("+k+").txt";
//		String authorfile="D:/hjn/结果/向量/author("+k+").txt";
//		String paperfile="D:/hjn/DASFAA/paperEmbedding-64.txt";
//		String authorfile="D:/hjn/DASFAA/authorEmbedding-64.txt";
//		Map<String,double[]> map=Similarity.readData(paperfile,authorfile);
//		String ppfile="D:/hjn/reprocessed/weused/p-p(remain).txt";
//		//String aafile="D:/hjn/reprocessed/weused/a-a(remaindouble).txt";
//		String aafile="D:/hjn/reprocessed/a-a(remain).txt";
//		String apfile="D:/hjn/reprocessed/weused/a-p(remain).txt";
		/**AMiner数据集***/
//		String ppOut="D:/hjn/结果/相似度/p-p-out("+k+").txt";
//		String aaOut="D:/hjn/结果/相似度/a-a-out("+k+").txt";
//		String apOut="D:/hjn/结果/相似度/a-a-out("+k+").txt";
////		String ppOut="D:/hjn/reprocessed/weused/p-p(remain).txt";
////		String aaOut="D:/hjn/reprocessed/weused/a-a(remaindouble).txt";
////		//String aafile="D:/hjn/reprocessed/a-a(remain).txt";
////		String apOut="D:/hjn/reprocessed/weused/a-p(remain).txt";
//		/*****************************************************/
//		/**aan数据集***/
////		String ppOut="D:/hjn/aan/Similarity/p-p-out("+k+").csv";
////		String aaOut="D:/hjn/aan/Similarity/a-a-out("+k+").csv";
////		String apOut="D:/hjn/aan/Similarity/a-p-out("+k+").csv";
//		/***********以上************/
////		String ppOut="D:/hjn/aan/processdata/p-p-weight.txt";
////		String aaOut="D:/hjn/aan/processdata/a-a-weight(1).txt";
////		String apOut="D:/hjn/aan/processdata/a-p-weight.txt";
////		Similarity.cosine(map,ppfile,ppOut);
////		Similarity.cosine(map,aafile,aaOut);
////		Similarity.cosine(map,apfile,apOut);
////		
//		CoRank corank=new CoRank();
//		/**AMiner数据集***/
//		corank.initializeVertexWeight("D:/hjn/reprocessed/weused/paperWeight1.txt", "p");//D:/hjn/reprocessed/weused/authorWeight1.txt
//		corank.initializeVertexWeight("D:/hjn/reprocessed/weused/authorWeight1.txt", "a");
//		/**aan数据集***/
////		corank.initializeVertexWeight("D:/hjn/aan/processdata/paperWeight.txt", "p");//D:/hjn/reprocessed/weused/authorWeight1.txt
////		corank.initializeVertexWeight("D:/hjn/aan/processdata/authorWeight.txt", "a");
//		
//		corank.initializePList(ppOut);
//		corank.initializeAList(aaOut);
//		corank.initializeAPEdge(apOut);
////		corank.initializePList(ppfile);
////		corank.initializeAList(aafile);
////		corank.initializeAPEdge(apfile);
//		corank.iterateRank(20,0.0,0.0,0.17,0.8);
////		-------------------
//		
//		/**AMiner数据集***/
//		String pfile="D:/hjn/结果/Rank值/papervalue("+k+").csv";//D:/hjn/对比实验/LINE+CoRank/paperranked.txt
//		String afile="D:/hjn/结果/Rank值/authorvalue("+k+").csv";//D:/hjn/对比实验/LINE+CoRank/authorranked.txt
//		/**aan数据集***/
////		String pfile="D:/hjn/aan/Result/papervalue("+k+").csv";
////		String afile="D:/hjn/aan/Result/authorvalue("+k+").csv";
//		corank.saveRankedResults(pfile, afile);
////		String pfile="D:/hjn/对比实验/ourWeight+CoRank/paperranked.txt";
////		String afile="D:/hjn/对比实验/ourWeight+CoRank/authorranked.txt";
//		MetricNDCG test=new MetricNDCG();
//		/**AMiner数据集***/
//		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
////		test.initializePYF("D:/hjn/reprocessed/paper-year-field.txt");
////		test.initializePGT("D:/hjn/reprocessed/paperGT(pcyf).txt");
////		test.initializePL(pfile);
////		for(int n=1;n<=3;n++) {
////			System.out.println(10*n);
////			test.calculateRI_P(10*n);
////			
////		}
////		System.out.println("paper Finished!");
//		test.initializeAYF("D:/hjn/reprocessed/author-year-field.txt");
//		test.initializeAGT("D:/hjn/reprocessed/authorGT(acyf).txt");
//		test.initializeAL(afile);//our method
//		for(int n=1;n<=3;n++) {
//			System.out.println(5*n);
//			test.calculateRI_A(5*n);
//			
//		}
//		System.out.println("author Finished!");
//		/**aan数据集***/
//		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
//		test.initializePYF("D:/hjn/aan/processdata/paperlist.csv");
//		test.initializePGT("D:/hjn/aan/processdata/papercitation(GT).csv");
//		test.initializePL(pfile);
//		for(int n=1;n<=10;n++) {
//			System.out.println(10*n);
//			test.calculateRI_P(10*n);
//			
//		}
//		System.out.println("paper Finished!");
//		test.initializeAYF("D:/hjn/aan/processdata/authorlist.csv");
//		test.initializeAGT("D:/hjn/aan/processdata/authorcitation(GT).csv");
//		test.initializeAL(afile);//our method
//		for(int n=1;n<=10;n++) {
//			System.out.println(10*n);
//			test.calculateRI_A(10*n);
//			
//		}
//		System.out.println("author Finished!");
		
		/**********对比实验AMiner****************/
		MetricNDCG test=new MetricNDCG();
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		test.initializePYF("data/paperlist.txt");
		test.initializePGT("data/pcitation.txt");
//		test.initializePL("D:/hjn/对比实验/PageRank/paperranked1.txt");//pageRank
////		test.initializePL("D:/hjn/对比实验/FutureRank/paperranked11.txt");//FutureRank
////		test.initializePL("D:/hjn/对比实验/MRCoRank/paperranked.txt");//MRCoRank 
		test.initializePL("data/Result/papervalue(20).csv");
////		test.initializePL("D:/hjn/Result/Ourmethod/PaperRank(pagerank_withoutembed).txt");//hypernetwork
		for(int n=1;n<=10;n++) {
			System.out.println(10*n);
			test.calculateRI_P(10*n);
//			
		}
////		System.out.println("paper Finished!");
		test.initializeAYF("data/authorcit-stat.txt");
		test.initializeAGT("data/authorcit-stat.txt");
//		test.initializeAL("D:/hjn/对比实验/PageRank/authorranked1.txt");//pageRank
//		test.initializeAL("D:/hjn/对比实验/FutureRank/authorranked11.txt");//FutureRank
//		test.initializeAL("D:/hjn/对比实验/MRCoRank/authorranked.txt");//MRCoRank
		test.initializeAL("data/Result/authorvalue(20).csv");
//		test.initializeAL("D:/hjn/对比实验/PageRank/authorrankedH.txt");//hypernetwork
//		for(int n=1;n<=2;n++) {
//			System.out.println(10*n);
//			test.calculateRI_A(10*n);
//
//		}
		System.out.println("author Finished!");
		/**********对比实验AAN****************/
//		MetricNDCG test=new MetricNDCG();
//		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
//		test.initializePYF("D:/hjn/aan/processdata/paperlist.csv");
//		test.initializePGT("D:/hjn/aan/processdata/papercitation(GT).csv");
////		test.initializePL("D:/hjn/aan/对比实验/PageRank/paperranked1.txt");//pageRank
////		test.initializePL("D:/hjn/aan/对比实验/FutureRank/paperranked.txt");//FutureRank
////		test.initializePL("D:/hjn/aan/对比实验/MRCoRank/paperranked.txt");//MRCoRank 
//		test.initializePL("D:/hjn/aan/Result/papervalue(EOE).csv");//D:/hjn/aan/对比实验/NECoRank-NE/paperranked.txt
////		test.initializePL("D:/hjn/aan/对比实验/hyperNetwork/paperranked.txt");//hypernetwork
//		for(int n=1;n<=10;n++) {
//			System.out.println(10*n);
//			test.calculateRI_P(10*n);
//			
//		}
//		
//		System.out.println("paper Finished!");
//		test.initializeAYF("D:/hjn/aan/processdata/authorlist.csv");
//		test.initializeAGT("D:/hjn/aan/processdata/authorcitation(GT).csv");
////		test.initializeAL("D:/hjn/aan/对比实验/PageRank/authorranked1.txt");//pageRank
////		test.initializeAL("D:/hjn/aan/对比实验/FutureRank/authorranked.txt");//FutureRank
////		test.initializeAL("D:/hjn/aan/对比实验/MRCoRank/authorranked.txt");//MRCoRank
//		test.initializeAL("D:/hjn/aan/对比实验/NECoRank-NE/authorranked.txt");
////		test.initializeAL("D:/hjn/aan/对比实验/hyperNetwork/authorranked.txt");//hypernetwork
//		for(int n=1;n<=10;n++) {
//			System.out.println(10*n);
//			test.calculateRI_A(10*n);
//			
//		}
//		
//		System.out.println("author Finished!");
	}

}
