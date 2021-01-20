import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class APList {
	public static void main(String[] args) {
//		File file=new File("D:/hjn/processedData/author2(ap-weight-year).txt");
//		File writepa=new File("D:/hjn/futurerank/palist.txt");
//		File writeap=new File("D:/hjn/futurerank/aplist.txt");
//		File rfile=new File("D:/hjn/data/paper5.txt");
//		File file=new File("D:/hjn/reprocessed/Dataset/paper-author.txt");
//		File rfile=new File("D:/hjn/对比实验/MRCoRank/other/p-t.txt");
		File rfile=new File("D:/hjn/结果/Rank值/papervalue(2).csv");
		File file=new File("D:/hjn/reprocessed/Dataset/papertitle.txt");
//		File file1=new File("D:/hjn/reprocessed/222222.csv");
		File wfile=new File("D:/hjn/结果/Rank值/papervalue(test).csv");
//		File wfile1=new File("D:/hjn/reprocessed/未找到的2.csv");
//		File wfile2=new File("D:/hjn/reprocessed/venue列表2.txt");
		try {
			BufferedReader read=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
//			BufferedReader read1=new BufferedReader(new InputStreamReader(new FileInputStream(file1),"utf-8"));
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfile),"utf-8"));
//			BufferedWriter bw1=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfile1),"utf-8"));
//			BufferedWriter bw2=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfile2),"utf-8"));
			String line=null;
			String line1=null;
//			HashMap<ArrayList<String>,Double> map1=new HashMap<ArrayList<String>,Double>();
			HashMap<String,Integer> mapin=new HashMap<String,Integer>();
//			HashMap<String,Double> map3=new HashMap<String,Double>();
			HashMap<String,Integer> map1=new HashMap<String,Integer>();
			HashMap<String,Integer> map2=new HashMap<String,Integer>();
//			HashMap<String,String> map3=new HashMap<String,String>();
//			HashMap<String,String> map4=new HashMap<String,String>();
			HashMap<String,HashSet<String>> map3=new HashMap<String,HashSet<String>>();
//			//HashSet<ArrayList<String>> set=new HashSet<ArrayList<String>>();
			HashSet<String> set=new HashSet<String>();
			HashSet<String> set1=new HashSet<String>();
//			HashSet<String> set2=new HashSet<String>();
//			map3.put("1", 0.0);
			while((line=read.readLine())!=null) {
//				ArrayList<String> list=new ArrayList<String>();
				String[] array=line.split(",");
//				double d=Double.parseDouble(array[2]);
//				mapin.put(array[1], mapin.get(array[1])==null?1:mapin.get(array[1])+1);
				if(line.indexOf("survey")!=-1||line.indexOf("review")!=-1)
					set.add(array[0]);
//				set.add(array[1]);
//				double weight=0;
//				for(int i=1;i<array.length;i++) {
//					double d=Double.parseDouble(array[i]);
//					weight+=d;
//				}
//				bw.write(array[0]+","+weight/(array.length-1)+"\r\n");
//				String s=array[0]+","+array[1];
//				map1.put(s,map1.get(s)==null?1:map1.get(s)+1);
//				map2.put(array[0], map2.get(array[0])==null?1:map2.get(array[0])+1);
//				if(map3.containsKey(array[1])) {
//					map3.get(array[1]).add(array[0]);
//				}else {
//					HashSet<String> setp=new HashSet<String>();
//					setp.add(array[0]);
//					map3.put(array[1], setp);
//				}
//				set.add(array[0]);
//				ArrayList<String> list=new ArrayList<String>();
//				list.add(array[1]);
//				list.add(array[0]);
//				double d=Double.parseDouble(array[2]);
//				//int i=Integer.parseInt(array[1]);
////				String 
//				map1.put(list, map1.get(list)==null?d:map1.get(list)+d);
			}
			read.close();
			System.out.println(set.size());
//			for(String s:set) {
//				if( mapin.containsKey(s)) {
//					bw.write(s+","+mapin.get(s)+"\r\n");
//				}else {
//					bw.write(s+",0"+"\r\n");
//				}
//					
//			}
//			int k=1;
//			for(String s:map1.keySet()) {
//				String[] array=s.split(",");
//				String pid=array[0];
//				String tid=array[1];
//				//if(map1.get(s)>1) {
//				int tf1=map1.get(s);
//				int tf2=map2.get(pid);
//				int n=set.size();
//				int d=map3.get(tid).size();
//				double tfidf=1.0*tf1/tf2*Math.log(1.0*n/(d+1.0));
//				bw.write(tid+","+pid+","+tfidf+"\r\n");
				//bw.write(array[0]+","+array[1]+","+map1.get(s)+","+map2.get(pid)+","+set.size()+","+map3.get(tid).size()+"\r\n");
				//}
				
//				if(map2.get(s)>=10&&s.length()>=4) {
//					bw.write(s+",t"+k+"\r\n");
//					k++;
//				}
//			}
//			while((line1=read1.readLine())!=null) {
////				ArrayList<String> list=new ArrayList<String>();
////				String[] array=line.split(";");
////				String 
//				if(map3.containsKey(line1.trim())) {
////					int k=map3.keySet().size()+1;
////					String s="V"+Integer.toString(k);
//					map4.put(line1.trim(),map3.get(line1.trim()));
//				}else {
//					int k=map3.keySet().size()+1+map4.keySet().size();
//					String s="V"+Integer.toString(k);
//					map4.put(line1.trim(),s);
//				}
//			}
//				int j=array[0].indexOf("'");
//				if(j!=-1) {
//					set.add(array[0].substring(0, j));
//				}else {
//					set.add(array[0]);
//				}
				
//				int i=Integer.parseInt(array[1]);
//				double d=Double.parseDouble(array[2]);
//				bw.write(array[0]+","+array[2]+"\r\n");
				//map3.put(array[0],array[1]+","+array[2]+","+array[3]);
//				if(d>map3.get("1")) {
//					map3.put("1", d);
//				}
				
//				set.add(line.trim());
//				if(!array[3].equals("null")) {
//					if(array[4].equals("null")) {
//						bw.write(array[0]+","+array[2]+","+array[1]+",other"+"\r\n");
//					}else {
//						bw.write(array[0]+","+array[2]+","+array[1]+","+array[4]+"\r\n");
//					}
//					
//				}
//				for(int i=1;i<array.length;i++) {
//					list.add(array[i]);
//				}
//				map.put(array[0], list);
//				int t1=Integer.parseInt(array[2]);
//				double weight=1.0/t1;
//				bw.write(array[1]+","+array[0]+","+weight+"\r\n");
//				int t2=Integer.parseInt(array[3]);
//				double weight=Math.pow(Math.E,-2.0*(2009-t1));
//				mapout.put(array[0],mapout.get(array[0])==null?weight:mapout.get(array[0])+weight);
//				mapin.put(array[2], mapin.get(array[2])==null?weight:mapin.get(array[2])+weight);
//				bw.write(array[0]+","+array[2]+","+weight+"\r\n");
//				if(map.containsKey(array[1])) {
//					map.get(array[1]).add(array[0]);
//				}else {
//					ArrayList<String> list=new ArrayList<String>();
//					list.add(array[0]);
//					map.put(array[1], list);
//				}
				//bw.write(array[1].trim()+"\r\n");
//				for(int i=0;i<array.length;i++) {
//					set.add(array[0]);
//				}
				
//				if(array.length!=2) {
//					System.out.println(line);
//				}
				//else {
//					String[] words=array[1].split(" ");
//					for(int i=0;i<words.length;i++) {
//						set.add(words[i]);
//					}
//				}
				
//				int t1=Integer.parseInt(array[1]);
//				map1.put(array[0], t1);
//				int t2=Integer.parseInt(array[3]);
//				if(t1<2010) {
//					map1.put(array[0], t1);
//					map2.put(array[2], t2);
//					//bw.write(line+"\r\n");
//				}else {
//				
//					map3.put(array[2], t2);
//				}
//				ArrayList<String> list=new ArrayList<String>();
//				list.add(array[0]);
//				list.add(array[1]);
//				set.add(list);
//				ArrayList<String> list1=new ArrayList<String>();
//				list1.add(array[1]);
//				list1.add(array[0]);
//				if(set.contains(list1)) {
//					set.remove(list);
//					set.remove(list1);
//				}
				
//				int a=line.indexOf("#t ");
//				int s=line.indexOf("#index ");
//				int e=line.indexOf(";#@");
//				String pid="p"+line.substring(s+7, e);
//				int year=Integer.parseInt(line.substring(a+3, a+7));
//				//double weight=Math.pow(Math.E, -0.62*(2014-year));
//				map.put(pid, year);
//				//line=read.readLine().trim();
//			}
//			read.close();
//			read1.close();
//			bw.close();
//			for(String key:mapin.keySet()) {
////				ArrayList<String> list=map.get(key);
////				bw.write(key+";"+map3.get(key)+"\r\n");
////				for(int i=0;i<list.size();i++) {
////					bw.write(list.get(i)+",");
////				}
//				double d=mapin.get(key)/map3.get("1");
//				bw.write(key+","+d+"\r\n");
//			}
//			for(String s:set) {
//				bw.write(s+"\r\n");
//			}
//			System.out.println(map1.keySet().size());
//			for(ArrayList<String> key:map1.keySet()) {
//				bw.write(key.get(0)+","+key.get(1)+","+map1.get(key)+"\r\n");
////				if(map2.containsKey(key)&&map3.containsKey(key)) {
////					if(map1.get(key)<2010) {
////						bw.write(key+","+map1.get(key)+"\r\n");
////					}
////					
////				}
//			}
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(rfile),"utf-8"));
//////			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfile),"utf-8"));
			String l=null;
			while((l=br.readLine())!=null) {
				String[] array=l.split(",");
				if(!set.contains(array[0]))
					//mapin.put(array[2],mapin.get(array[2])==null?1:mapin.get(array[2])+1);
					bw.write(l+"\r\n");
					//bw.write(array[0]+","+map1.get(array[0])+","+array[2]+","+array[3]+"\r\n");
			}
			br.close();
//			for(String s:mapin.keySet()) {
//				bw.write(s+","+mapin.get(s)+"\r\n");
//			}
//				String[] array1=array[1].split(" ");
//				String pid=array[0];
//				for(int i=1;i<array.length;i++) {
//					if(map3.containsKey(array[i])) {
//						bw.write(pid+","+map3.get(array[i])+"\r\n");
//					}
//				}
//				double weight=Math.pow(Math.E, -2.0*(2010-map2.get(pid)));
//				bw.write(array[1]+","+pid+","+weight+"\r\n");
//				if(set.contains(pid)) {
//					bw.write(pid+",");
//					for(int i=0;i<array1.length;i++) {
//						bw.write(array1[i]+",");
//						for(int j=i+1;j<array1.length;j++) {
//							bw.write(array1[i]+" "+array1[j]+",");
//						}
//					}
//					bw.write("\r\n");
//				}
//				if(map3.containsKey(pid)) {
//					for(int i=1;i<array.length;i++) {
//						bw.write(array[i]+","+map3.get(pid)+"\r\n");
//					}
////					for(String s:map4.keySet()) {
////						int index=l.indexOf(s);
////						if(index!=-1) {
////							bw.write(pid+","+map4.get(s)+"\r\n");
////							break;
////						}
////					}
//				}
//			}
//				String[] title=array[1].split(" ");
//				int j=title[0].indexOf("'");
//				if(j!=-1) {
//					String s=title[0].substring(0, j);
//					if(map3.containsKey(s)){
//						bw.write(pid+","+map3.get(s)+"\r\n");
//					}else {
//						bw1.write(l+"\r\n");
//					}
//					//set.add(array[0].substring(0, j));
//				}else {
//					if(map3.containsKey(title[0])){
//						bw.write(pid+","+map3.get(title[0])+"\r\n");
//					}else {
//						bw1.write(l+"\r\n");
//					}
//				}
//				if(map3.containsKey(array[0])&&map3.containsKey(array[1])) {
//					System.out.println(array[0]+","+map3.get(array[0])+","+array[1]+","+map3.get(array[1]));
//				}
//			}
//			for(String s:map4.keySet()) {
//				bw2.write(s+","+map4.get(s)+"\r\n");
//			}
//				bw.write(l+","+map3.get(array[0])+","+map3.get(array[3])+"\r\n");
//				double weight=0;
//				map2.put(array[0], 2010);
//				HashSet<String> setp=new HashSet<String>();
//				for(int i=1;i<array.length;i++) {
//					if(map3.containsKey(array[i])) {
//						setp.add(map3.get(array[i]));
//						
//						//weight+=mapin.get(array[i]);
////						System.out.println(map3.get(array[i]));
////						System.out.println(map2.get(array[0]));
////						if(map3.get(array[i])<map2.get(array[0])) {
////							map2.put(array[0], map3.get(array[i]));
////						}
//					}
//					
//				}
//				bw.write(array[0]+",");
//				if(setp.size()>1&&setp.contains("other")) {
//					setp.remove("other");
//				}
//				for(String s:setp) {
//					bw.write(s);
//				}
//				bw.write("\r\n");
//				bw.write(array[0]+","+weight+"\r\n");
//				if(map3.containsKey(array[0])) {
//					bw.write(array[0]+","+array[1]+","+map3.get(array[0])+"\r\n");
//				}
//				else {
//					bw.write(array[0]+",other"+"\r\n");
//				}
//				for(String s:set) {
//					int i=array[0].indexOf(s);
//					if(i!=-1) {
//						bw.write(array[0]+";"+"AI"+"\r\n");
//						break;
//					}
//				}
//			}
//				int a=l.indexOf("#index");
//				int b=l.indexOf(";#@");
//				String id="p"+l.substring(a+7, b);
//				if(set.contains(id)) {
//					int c=l.indexOf(";#c ");
//					String venue=l.substring(c+4,l.length());
////					set1.add(venue);
//					bw.write(id+";"+venue.trim()+"\r\n");
//				}
//				int n=0;
//				for(int i=1;i<array.length;i++) {
//					n+=map3.get(array[i]);
//				}
				
//				if(set.contains(array[1])) {
//					bw.write(l+"\r\n");
//					bw.write(array[1]+","+array[0]+","+array[2]+"\r\n");
//					for(int i=1;i<array.length;i++) {
//						if(map.containsKey(array[i])) {
//							map.get(array[i]).add(array[0]);
//						}else {
//							ArrayList<String> list=new ArrayList<String>();
//							list.add(array[0]);
//							map.put(array[i], list);
//						}
//					}
//				}
//			}
//				double w1=Math.pow(Math.E, -2.0*(2009-map3.get(array[0])));
//				int size=array.length-1;
//				if(array.length>2) {
//					double w2=1.0/(size-1);
//					double weight=w1*w2;
//					for(int i=1;i<array.length;i++) {
//						for(int j=i+1;j<array.length;j++) {
//							ArrayList<String> two=new ArrayList<String>();
//							two.add(array[i]);
//							two.add(array[j]);
//							map1.put(two, map1.get(two)==null?weight:map1.get(two)+weight);
//						}
//					}
//				}
//				
//				double d=Double.parseDouble(array[2]);
//				
//				ArrayList<String> list=map.get(array[0]);
//				ArrayList<String> list1=map.get(array[1]);
//				for(int i=0;i<list.size();i++) {
//					for(int j=0;j<list1.size();j++) {
//						ArrayList<String> two=new ArrayList<String>();
//						two.add(list.get(i));
//						two.add(list1.get(j));
//						map1.put(two,map1.get(two)==null?d:map1.get(two)+d);
//					}
//				}
//				double weight=b*mapin.get(array[0])/mapout.get(array[0]);
//				bw.write(array[0]+","+array[1]+","+weight+"\r\n");
//				set1.add(array[0]);
//				set2.add(array[2]);
//			}
//			br.close();
//			for(String s:map2.keySet()) {
////				String[] arr=s.split(";");
////				if(arr.length>1) {
////					System.out.println(s);
////				}
//				bw.write(s+","+map2.get(s)+"\r\n");
//			}
//			for(ArrayList<String> s:map1.keySet()) {
//				bw.write(s.get(0)+","+s.get(1)+","+map1.get(s)+"\r\n");
//				if(set1.contains(s)&&set2.contains(s)) {
//					bw.write(s+","+map1.get(s)+"\r\n");
//				}
//				if(!set1.contains(s)) {
//					System.out.println(s+"out");
//				}
//				if(!set2.contains(s)) {
//					System.out.println(s+"in");
//				}
//			}
//			for(String s:map.keySet()) {
//				ArrayList<String> list=map.get(s);
//				bw.write(s+",");
//				for(int i=0;i<list.size();i++) {
//					bw.write(list.get(i)+",");
//				}
//				bw.write("\r\n");
//			}
//				int i=l.indexOf("#index");
//				int j=l.indexOf(";#@");
//				int k=l.indexOf(";#t");
//				String pid="p"+l.substring(i+7,j);
//				if(map1.containsKey(pid)) {
//					String authors=l.substring(j+4,k);
//					String[] array=authors.split(";");
//					for(int r=0;r<array.length;r++) {
//						String name=array[r].trim();		
//						if(!map2.containsKey(name)) {
//							map2.put(name, map2.keySet().size()+1);
//						}
//						int pos=r+1;
//						bw.write(pid+",A"+map2.get(name)+","+pos+","+array.length+"\r\n");
//						
//					}
//				}
				
				
//				int t=Integer.parseInt(array[1]);
//				if(t>=2010&&map1.containsKey(array[2])) {
//					bw.write(l+"\r\n");
//				}
//				int i=l.indexOf("#* ");
//				int j=l.indexOf(";#! ");
//				String pid="p"+array[0].substring(7, array[0].length());
//				if(map1.containsKey(pid)) {
//					bw.write(pid+","+l.substring(i+3,j)+"\r\n");
//				}
//				if(map1.containsKey(pid)&&array[1].length()>5&&i!=-1) {
//					//bw.write(pid+","+array[1].substring(3, array[1].length())+"\r\n");
//					bw.write(pid+","+map1.get(pid)+"\r\n");
//				}
//				for(int i=1;i<array.length;i++) {
//					String pcite="p"+array[i].substring(3, array[i].length());
//					if(map.containsKey(pid)&&map.containsKey(pcite)) {
//						int t1=map.get(pid);
//						int t2=map.get(pcite);
//						if(t1>=t2&&t1>=1998&&t2>=1998) {
//							bw.write(pid+","+t1+";"+pcite+","+t2+"\r\n");
//							
//						}
//					}
//					
//				}
//			}
//			for(String name:map2.keySet()) {
//				bw.write("A"+map2.get(name)+"----"+name+"\r\n");
//			}
//			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfile),"utf-8"));
//			for(ArrayList<String> list:set) {
//				String[] array=list.get(1).split(",");
//				int t=Integer.parseInt(array[1]);
//				if(t<2010) {
//					bw.write(list.get(0)+","+list.get(1)+"\r\n");
//				}
//				
//			}
			bw.close();
//			bw1.close();
//			bw2.close();
//			HashMap<String,HashSet<String>> aplist=new HashMap<String,HashSet<String>>();
//			HashMap<String,HashSet<String>> palist=new HashMap<String,HashSet<String>>();
//			while(line!=null) {
//				String[] array=line.split(",");
//				if(aplist.get(array[0])!=null) {
//					HashSet<String> papers=aplist.get(array[0]);
//					papers.add(array[1]);
//					aplist.put(array[0],papers);
//				}else {
//					HashSet<String> papers=new HashSet<String>();
//					papers.add(array[1]);
//					aplist.put(array[0],papers);
//				}
//				if(palist.get(array[1])!=null) {
//					HashSet<String> authors=palist.get(array[1]);
//					authors.add(array[0]);
//					palist.put(array[1], authors);
//				}else {
//					HashSet<String> authors=new HashSet<String>();
//					authors.add(array[0]);
//					palist.put(array[1], authors);
//				}
//				
//				line=read.readLine();
//			}
//			read.close();
//			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writepa),"utf-8"));
//			BufferedWriter bw1=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeap),"utf-8"));
//			for(String author:aplist.keySet()) {
//				bw1.write("\r\n"+author+",");
//				for(String pid:aplist.get(author)) {
//					bw1.write(pid+",");
//				}
//			}
//			bw1.close();
//			for(String paper:palist.keySet()) {
//				bw.write("\r\n"+paper+",");
//				for(String aid:palist.get(paper)) {
//					bw.write(aid+",");
//				}
//			}
//			bw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
