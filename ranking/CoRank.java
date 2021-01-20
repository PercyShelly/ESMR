package ranking;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CoRank {
	private Map<String, Set<Node>> ppmap = new HashMap<String, Set<Node>>();
	private Map<String, Set<Node>> pamap = new HashMap<String, Set<Node>>();
	private Map<String, Set<Node>> apmap = new HashMap<String, Set<Node>>();
	private Map<String, Set<Node>> aamap = new HashMap<String, Set<Node>>();
	private Set<String> paperlist = new HashSet<String>();
	private Set<String> authorlist = new HashSet<String>();
	private Map<String, Double> lastRanking_p = new HashMap<String, Double>();
	private Map<String, Double> nextRanking_p = new HashMap<String, Double>();
	private Map<String, Double> lastRanking_a = new HashMap<String, Double>();
	private Map<String, Double> nextRanking_a = new HashMap<String, Double>();
	private Map<String, Double> vertexweight_p = new HashMap<String, Double>();
	private Map<String, Double> vertexweight_a = new HashMap<String, Double>();
	private double epsilon = 0.000001;
	private double alpha = 0.2;

	public void initializePList(String file) {
		File rfile = new File(file);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rfile), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] array = line.split(",");
				double weight = Double.parseDouble(array[2]);
				paperlist.add(array[0]);
				paperlist.add(array[1]);
				Node node = new Node(array[0], weight);
//            	if(array[0].equals("p1348402")&&array[1].equals("p641693")) {
//            		System.out.println(weight);
//            	}
				if (ppmap.containsKey(array[1])) {
					ppmap.get(array[1]).add(node);
				} else {
					Set<Node> set = new HashSet<Node>();
					set.add(node);
					ppmap.put(array[1], set);
				}
			}
			br.close();

//            Set<Node> set=ppmap.get("p641693");
//            for(Node s:set) {
//            	System.out.println(s.getNodeid()+","+s.getEdgeWeight());
//            }

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initializeAList(String file) {
		File rfile = new File(file);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rfile), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] array = line.split(",");
				double weight = Double.parseDouble(array[2]);
				authorlist.add(array[0]);
				authorlist.add(array[1]);
				Node node0 = new Node(array[0], weight);
				Node node1 = new Node(array[1], weight);
				if (aamap.containsKey(array[1])) {
					aamap.get(array[1]).add(node0);
				} else {
					Set<Node> set = new HashSet<Node>();
					set.add(node0);
					aamap.put(array[1], set);
				}
				if (aamap.containsKey(array[0])) {
					aamap.get(array[0]).add(node1);
				} else {
					Set<Node> set = new HashSet<Node>();
					set.add(node1);
					aamap.put(array[0], set);
				}
			}
			br.close();

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void initializeAPEdge(String file) {
		File rfile = new File(file);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rfile), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] array = line.split(",");
				double weight = Double.parseDouble(array[2]);
				if (paperlist.contains(array[1]) && authorlist.contains(array[0])) {
					Node pnode = new Node(array[1], weight);
					Node anode = new Node(array[0], weight);
					if (pamap.containsKey(array[1])) {
						pamap.get(array[1]).add(anode);
					} else {
						Set<Node> set = new HashSet<Node>();
						set.add(anode);
						pamap.put(array[1], set);
					}
					if (apmap.containsKey(array[0])) {
						apmap.get(array[0]).add(pnode);
					} else {
						Set<Node> set = new HashSet<Node>();
						set.add(pnode);
						apmap.put(array[0], set);
					}
				}

			}
			br.close();
//            System.out.println(pamap.get("p636082").size());
//            System.out.println(pamap.get("p722904").size());
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void iterateRank(int iter, double alpha_p, double beta_p, double alpha_a, double beta_a) {
//		HashMap<String, Double> lastRanking_p = new HashMap<String, Double>();
//        HashMap<String, Double> nextRanking_p = new HashMap<String, Double>();
//        HashMap<String, Double> lastRanking_a = new HashMap<String, Double>();
//        HashMap<String, Double> nextRanking_a = new HashMap<String, Double>();
		Double startRank_p = 1.0 / paperlist.size();
		Double startRank_a = 1.0 / authorlist.size();
		for (String key : paperlist) {
			lastRanking_p.put(key, startRank_p);
			//           nextRanking_p.put(key, startRank_p);
		}
		for (String key : authorlist) {
			lastRanking_a.put(key, startRank_a);
			//           nextRanking_a.put(key, startRank_p);
		}
		double dampling_p = 1.0 - alpha_p - beta_p;
		double dampling_a = 1.0 - alpha_a - beta_a;
//        double dampling_a =0.0;
//        double dampling_p =0.0;

//        System.out.println(lastRanking_p.get("p1270484")+"last");
		for (int i = 0; i < iter; i++) {
			int j = 0;
//        	for(String a:authorlist) {
//        		double weight1=0;
//        		double weight2=0;
//        		for(Node author:aamap.get(a)) {
//        			weight1+=author.getEdgeWeight()*lastRanking_a.get(author.getNodeid());
//        		}
//        		
//        		if(apmap.containsKey(a)) {
//        			for(Node paper:apmap.get(a)) {
//            			weight2+=paper.getEdgeWeight()*lastRanking_p.get(paper.getNodeid());
//            		}
//        		}
//        		
//        		else {
//        			j++;
//        			//System.out.println(a);
//        		}
//        		double nextrank=alpha_a*weight1+beta_a*weight2+dampling_a*this.vertexweight_a.get(a);
//        		if(a.equals("A1282")) {
//        			System.out.println(weight1+","+weight2+","+this.vertexweight_a.get(a));
//        		}
//        		
//        		nextRanking_a.put(a, nextrank);
//        	}
//        	System.out.println(j);

			for (String s : paperlist) {
				double weight1 = 0;
				double weight2 = 0;
				if (ppmap.containsKey(s)) {
					double a = ppmap.get(s).size() * 1.0;
					for (Node paper : ppmap.get(s)) {
						//weight1+=paper.getEdgeWeight()*lastRanking_p.get(paper.getNodeid());
						weight1 += (alpha * paper.getEdgeWeight() + (1 - alpha) * this.vertexweight_p.get(s) / a) * lastRanking_p.get(paper.getNodeid());
					}

				}
				if (pamap.containsKey(s)) {
					double a = pamap.get(s).size() * 1.0;
					for (Node author : pamap.get(s)) {
//            			weight2+=author.getEdgeWeight()*lastRanking_a.get(author.getNodeid());
						weight2 += (alpha * author.getEdgeWeight() + (1 - alpha) * 1.0 / a) * lastRanking_a.get(author.getNodeid());
					}
				}
				double nextrank = alpha_p * weight1 + beta_p * weight2 + dampling_p * this.vertexweight_p.get(s);
//        		if(s.equals("p1058303")) {
//        			System.out.println("p1058303:"+weight1+","+weight2+","+this.vertexweight_p.get(s)+","+nextrank);
//        		}
//        		if(s.equals("p1275689")) {
//        			System.out.println("p1275689:"+weight1+","+weight2+","+this.vertexweight_p.get(s)+","+nextrank);
//        		}
//        		if(s.equals("p1264744")) {
//        			System.out.println("p1264744:"+weight1+","+weight2+","+this.vertexweight_p.get(s)+","+nextrank);
//        		}
				nextRanking_p.put(s, nextrank);

			}
			//System.out.println(nextRanking_p.get("p1270484")+"next");
			//      	lastRanking_p = nextRanking_p;
			//       	int j=0;
			for (String a : authorlist) {
				double weight1 = 0;
				double weight2 = 0;
				for (Node author : aamap.get(a)) {
					double n = aamap.get(a).size() * 1.0;
					weight1 += author.getEdgeWeight() * lastRanking_a.get(author.getNodeid());
//        			weight1+=(alpha*author.getEdgeWeight()+(1-alpha)*this.vertexweight_a.get(a)/n)*lastRanking_a.get(author.getNodeid());
				}

				if (apmap.containsKey(a)) {
					double n = aamap.get(a).size() * 1.0;
					for (Node paper : apmap.get(a)) {
						weight2 += (alpha * paper.getEdgeWeight() + (1 - alpha) * 1.0 / n) * lastRanking_p.get(paper.getNodeid());
//            			weight2+=paper.getEdgeWeight()*nextRanking_p.get(paper.getNodeid());
					}
				} else {
					j++;
					//System.out.println(a);
				}
				double nextrank = alpha_a * weight1 + beta_a * weight2 + dampling_a * this.vertexweight_a.get(a);
				nextRanking_a.put(a, nextrank);
			}
////        	System.out.println(j);
			System.out.println(i);

			int converge = Converge();
			if (converge == 1) {
				break;
			}
			lastRanking_p = nextRanking_p;
			lastRanking_a = nextRanking_a;
		}

	}

	public int Converge() {
		if ((nextRanking_p == null) || (nextRanking_a == null)) {
			return 0;
		}

		for (String s : paperlist) {
			if (Math.abs(nextRanking_p.get(s) - lastRanking_p.get(s)) >= this.epsilon) {
				return 0;
			}
		}
		for (String s : authorlist) {
			if (Math.abs(nextRanking_a.get(s) - lastRanking_a.get(s)) >= this.epsilon) {
				return 0;
			}
		}
		return 1;
	}

	public void saveRankedResults(String pfile, String afile) {
		File filep = new File(pfile);
		File filea = new File(afile);
		try {
			BufferedWriter bwp = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filep), "utf-8"));
			for (String node : paperlist) {
				bwp.write(node + "," + lastRanking_p.get(node) + "\r\n");
			}
			System.out.println("save  paper ranked results finished...");
			bwp.close();
			BufferedWriter bwa = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filea), "utf-8"));
			for (String node : authorlist) {
				bwa.write(node + "," + lastRanking_a.get(node) + "\r\n");
			}
			System.out.println("save  author ranked results finished...");
			bwa.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initializeVertexWeight(String file, String s) {
		File rfile = new File(file);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rfile), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] array = line.split(",");
				double weight = Double.parseDouble(array[1]);
				if (s.equals("p")) {
					this.vertexweight_p.put(array[0], weight);
				}
				if (s.equals("a")) {
					this.vertexweight_a.put(array[0], weight);
				}

			}
			br.close();

		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Similarity simi=new Similarity();
		int n = 20;
		String k = "20";
		String ppOut="./data/Result/p-p-out("+k+").txt";
		String aaOut="./data/Result/a-a-out("+k+").txt";
		String apOut="./data/Result/a-p-out("+k+").txt";
		CoRank corank = new CoRank();
		/**aan数据集***/
		corank.initializeVertexWeight("./data/paperWeight.txt", "p");//D:/hjn/reprocessed/weused/authorWeight1.txt
		corank.initializeVertexWeight("./data/authorWeight.txt", "a");
		corank.initializePList(ppOut);
		corank.initializeAList(aaOut);
		corank.initializeAPEdge(apOut);
		corank.iterateRank(20, 0.3, 0.3, 0.05, 0.9);

		/**aan数据集***/
		String pfile = "./data/Result/papervalue(" + k + ").txt";
		String afile = "./data/Result/authorvalue(" + k + ").txt";
		corank.saveRankedResults(pfile, afile);
	}
}
