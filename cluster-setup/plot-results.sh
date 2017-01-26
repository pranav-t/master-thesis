# plot workload distribution: start
#rm ~/Desktop/thesis/plots/workload-distribution/hotspotted-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-distribution/uniform-data/*.metrics

# while read node; do
# 	arr=($node)
# 	host_name=${arr[1]}
# 	if echo "$host_name" | grep -q "sue-data"
# 	then
# 		ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt "cat /usr/local/hbase/hbase.rs.writeRequestCounts.metrics | grep -Po ', writeRequestCount=\K[0-9]+' > /usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics" </dev/null
# 		#scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-distribution/hotspotted-data/"$host_name"-request-count.metrics
# 		scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-distribution/uniform-data/"$host_name"-request-count.metrics
# 	fi
# done < nodes.txt

# octave --persist plot-in-octave.m
# plot workload distribution: end

# plot workload parallelization: start
# rm ~/Desktop/thesis/plots/workload-parallelization/2-mappers-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-parallelization/4-mappers-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-parallelization/8-mappers-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-parallelization/16-mappers-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-parallelization/32-mappers-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-parallelization/64-mappers-data/*.metrics

# while read node; do
# 	arr=($node)
# 	host_name=${arr[1]}
# 	if echo "$host_name" | grep -q "sue-data"
# 	then
# 		ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt "cat /usr/local/hbase/hbase.rs.writeRequestCounts.metrics | grep -Po ', writeRequestCount=\K[0-9]+' > /usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics" </dev/null
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-parallelization/2-mappers-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-parallelization/4-mappers-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-parallelization/8-mappers-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-parallelization/16-mappers-data/"$host_name"-request-count.metrics
# 		scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-parallelization/32-mappers-data/"$host_name"-request-count.metrics		
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-parallelization/64-mappers-data/"$host_name"-request-count.metrics				
# 	fi
# done < nodes.txt

# octave --persist plot-in-octave.m
# plot workload parallelization: end

# plot workload granularity: start
# rm ~/Desktop/thesis/plots/workload-granularity/0.2mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/0.4mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/0.6mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/0.8mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/1.0mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/1.2mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/1.4mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/1.6mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/1.8mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/2.0mb-parts-data/*.metrics
# rm ~/Desktop/thesis/plots/workload-granularity/3.2mb-parts-data/*.metrics

# while read node; do
# 	arr=($node)
# 	host_name=${arr[1]}
# 	if echo "$host_name" | grep -q "sue-data"
# 	then
# 		ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt "cat /usr/local/hbase/hbase.rs.writeRequestCounts.metrics | grep -Po ', writeRequestCount=\K[0-9]+' > /usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics" </dev/null
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/0.2mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/0.4mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/0.6mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/0.8mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/1.0mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/1.2mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/1.4mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/1.6mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/1.8mb-parts-data/"$host_name"-request-count.metrics
# 		# scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/2.0mb-parts-data/"$host_name"-request-count.metrics
# 		scp -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name:/usr/local/hbase/hbase.rs.writeRequestCounts.filtered.metrics ~/Desktop/thesis/plots/workload-granularity/3.2mb-parts-data/"$host_name"-request-count.metrics
# 	fi
# done < nodes.txt

octave --persist plot-in-octave.m

# plot workload granularity: end