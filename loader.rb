place = '/home/eveleech/genomes'
Dir.foreach(place) do |file|
	if file.match(/vcf*/)
		dbase = file.downcase 
		count = 1
		File.readlines("#{place}/#{file}").each do |line|
			data_line = line.split(" ")
			place = data_line[0]	
			dif = data_line[1]
			alt = data_line[2]
			#p "place #{place} dif #{dif} alt #{alt}"
			`curl -XPOST 'http://localhost:9200/#{dbase}/differ/#{count}' -d '{\"diff\" : \"#{dif}\",\"place\": #{place},\"alt\" : \"#{alt}\"}'`
			count +=1
		end	
	end
	break
end	
