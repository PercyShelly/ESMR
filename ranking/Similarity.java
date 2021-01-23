package ranking;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Similarity {
	public static Map<String,double[]> readData(String filePath,String filePath1){
		Map<String,double[]> map=new HashMap<String,double[]>();
		File file=new File(filePath);
		File file1=new File(filePath1);
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String line=null;
			
			while((line=br.readLine())!=null){
				String[] str=line.split(",");
				double[] vector=new double[str.length-1];
				for(int i=1;i<str.length;i++){
					vector[i-1]=Double.valueOf(str[i].toString());
				}
				map.put(str[0],vector);//½«id×÷Îªkey,Ç¶Èë×÷Îªvalue·ÅÈëmapÖÐ
			}
			br.close();
			System.out.println(map.keySet().size());
			BufferedReader br1=new BufferedReader(new InputStreamReader(new FileInputStream(file1),"utf-8"));
			String line1=null;
			
			while((line1=br1.readLine())!=null){
				String[] str=line1.split(",");
				double[] vector=new double[str.length-1];
				for(int i=1;i<str.length;i++){
					vector[i-1]=Double.valueOf(str[i].toString());
				}
				map.put(str[0],vector);
			}
			br1.close();
			System.out.println(map.keySet().size());
		}catch(IOException e){
			e.printStackTrace();
		}
		return map;
	}
	public static void cosine(Map<String,double[]> map,String filePath,String outPath){
		//Map<ArrayList<String>,Double> edgemap=new HashMap<ArrayList<String>,Double>();
		File file=new File(filePath);
		File outfile= new File(outPath);
		try{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile),"utf-8"));
			String line=null;
			//ArrayList<String> edge=new ArrayList<String>();
			while((line=br.readLine())!=null){
				String[] str=line.split(",");
				if(!str[0].equals(str[1])) {
					// edge.add(str[0]);
					// edge.add(str[1]);
					double[] vector1=map.get(str[0]);
					double[] vector2=map.get(str[1]);
					System.out.print(vector2);
					//if(vector1.length==vector2.length){
						double cos=getCosine(vector1,vector2);
						//double cos=getEuc(vector1,vector2);
						bw.write(str[0]+","+str[1]+","+cos+"\r\n");
					//}
					
					//edgemap.put(edge,cos);
				}
				
			}
			br.close();
			bw.flush();
			bw.close();
	//		System.out.println(edgemap.keySet().size());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static double getCosine(double[] vector1,double[] vector2){
		double z=0.0;
		double left=0.0;
		double right=0.0;
		int j=0;
		for(int i=0;i<vector1.length;i++){
			//if(vector2[i]==null){
				//System.out.println(i); 
			//}
            z+=vector1[i]*vector2[i];  
            
        }
        for(int i=0;i<vector1.length;i++){  
            left+=vector1[i]*vector1[i];  
            right+=vector2[i]*vector2[i];  
        }
        double cosine=z/Math.sqrt(left*right);
        double result=0.5+0.5*cosine;
        return result;
	}
	public static double getEuc(double[] vector1,double[] vector2){
		double z=0.0;
		double left=0.0;
		double right=0.0;
		int j=0;
		for(int i=0;i<vector1.length;i++){
			//if(vector2[i]==null){
				//System.out.println(i); 
			//}
            z+=Math.pow(vector1[i]-vector2[i],2);  
            
        }
//        for(int i=0;i<vector1.length;i++){  
//            left+=vector1[i]*vector1[i];  
//            right+=vector2[i]*vector2[i];  
//        }
        double cosine=Math.sqrt(z);
//        double result=0.5+0.5*cosine;
        return cosine;
	}
	public static void main(String[] args) {
		Similarity simi=new Similarity();
		String p1file = "./author(NECoRank-T)1.txt";
		String p2file = "./paper(NECoRank-T)1.txt";
		String ppfile = "./data/a-p(remain).txt";
		String savesimi = "./data/Result/a-p-out";
		simi.cosine(simi.readData(p1file,p2file),ppfile,savesimi);

	}
}
