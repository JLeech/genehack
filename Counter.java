import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.lang.*;
import java.util.Arrays;
import java.io.PrintWriter;

class Counter {


	public int[] checkType( int[] val, String dif){
		int place = 0;
		switch (dif) {
			case "a": place = 0;
					break;
			case "t": place = 1;
					break;
			case "c": place = 2;
					break;
			case "g": place = 3;
					break;
			case "n": place = 4;
					break;
			case ".": place = 5;
					break;
		}
		val[place] += 1;
		return val;
	}

	public Map countHash(File file, Map dictionary){
		
		int counter = 0;
		BufferedReader br = null;
 
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
			while ((sCurrentLine = br.readLine()) != null) {
				String[] words = sCurrentLine.split("\\s+");
				if(sCurrentLine.equals(""))
                	continue;
                Integer place = Integer.parseInt(words[0]);
                String dif = words[1];
                String alt = words[2];
                
                if(dictionary.containsKey(place)) {
                    int[] val = (int[])dictionary.get(place);
                    dictionary.put(place, checkType(val,dif));
                }
                else{
                    int[] val = {0,0,0,0,0,0};
                    dictionary.put(place, checkType(val,dif));
                }
				counter ++;
			}
		} catch (IOException e) {
				e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println(counter);
		System.out.println("size: " + dictionary.size());
		return dictionary;
	}

	public void print_dictionary(Map dictionary){
		for(Object key: dictionary.keySet())
            System.out.println(key + ": " + Arrays.toString((int[])dictionary.get(key)));
	}

	public void print_to_file(Map dictionary){
		try{
			PrintWriter writer = new PrintWriter("collected", "UTF-8");
			for(Object key: dictionary.keySet())
				writer.println(key + " " + Arrays.toString((int[])dictionary.get(key)));
			writer.close();
		}catch(IOException e) {
				e.printStackTrace();
		}finally{
		}
	}

    public static void main(String[] args) {
        final File folder = new File(args[0]);
        int iter = 0;
	Map dictionary = new HashMap();
    	Counter counter = new Counter();
    	for (final File file : folder.listFiles()){
    		if (!file.isDirectory()) {
	    		String name = file.getName();
	    		if (name.startsWith("vcf")) 
	    			System.out.println("found: " + name);
	    			long start = System.nanoTime();    
	    			dictionary = counter.countHash(file,dictionary);
	    			long elapsedTime = System.nanoTime() - start;
	    			System.out.println("took:" + elapsedTime);
				System.out.println("place:"+ iter);
				iter += 1;
				if (iter == 40){
					break;
				}
	    		}
    	}
    	System.out.println("start writing");
    	counter.print_to_file(dictionary);	
    }
}

