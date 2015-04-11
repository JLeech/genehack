File.readlines('names.txt').each do |line| 
	name = line.split(" ").first.downcase
	`curl -XDELETE 'http://localhost:9200/#{name}/'` 
#	`curl -XPUT 'http://localhost:9200/#{name}/'`
#	`curl -XPUT 'http://localhost:9200/#{name}/_mapping/differ' -d '{\"properties\" : {\"place\" : {\"type\":\"long\"},\"diff\" : {\"type\":\"string\"},\"alt\" : {\"type\":\"string\"}}}'`
	puts name
end
