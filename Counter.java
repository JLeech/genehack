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

	public void print_to_file(Map dictionary,Boolean frequency){
		try{
			String file_name = "collected_sorted";
			if (frequency == true){
				file_name += "_normalized";
			}
			PrintWriter writer = new PrintWriter(file_name+".vcf", "UTF-8");
			Object[] keys = dictionary.keySet().toArray();
			Arrays.sort(keys);
			for(Object key : keys){
				int [] data = (int[])dictionary.get(key);
				float [] data_out = new float[6];
				int place = 0;
				String out_string = "";
				if (frequency == true){
					float sum = 0;
					for( int number : data) {
	 				   sum += (float)number;
					}
					while ( place < 6 ) {
						out_string += ((float)data[place])/sum + "\t";
						place += 1;
					}
				} else{
					while ( place < 6 ) {
						out_string += ((float)data[place]) + "\t";
						place += 1;
					}
				}
				writer.println(key + "\t" + out_string);
			}
			writer.close();
		}catch(IOException e) {
				e.printStackTrace();
		}finally{
		}
	}

    public static void main(String[] args) {
        Boolean frequency = false;
        if (args.length == 0) {
        	System.out.println("need more args");
        	return;
        }
        if (args.length == 2) {
        	frequency = Boolean.valueOf(args[1]);
        }
        final File folder = new File(args[0]);
        int iter = 0;
        Map dictionary = new HashMap();
    	Counter counter = new Counter();
    	for (final File file : folder.listFiles()){
    		if (!file.isDirectory()) {
	    		String name = file.getName();
	    		if (name.startsWith("vcf")){
	    			System.out.println("found: " + name);
	    			long start = System.nanoTime();    
	    			dictionary = counter.countHash(file,dictionary);
	    			long elapsedTime = System.nanoTime() - start;
	    			System.out.println("took:" + elapsedTime);
	    			iter += 1;
					if (iter == 30){
						break;
					}
				}
	    	}
    	}
    	System.out.println("start writing");
    		counter.print_to_file(dictionary,frequency);
    }
}

