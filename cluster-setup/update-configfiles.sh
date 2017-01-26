#remove old upload files
rm /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hadoop.tar.gz
rm /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hbase.tar.gz
rm /home/pranav/Desktop/thesis/cluster-setup/upload/driver/hadoop.tar.gz

#update sue config files before upload:
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/sue/hadoop/core-site.xml /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hadoop/conf
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/sue/hadoop/hdfs-site.xml /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hadoop/conf
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/sue/hadoop/masters /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hadoop/conf
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/sue/hadoop/slaves /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hadoop/conf
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/sue/hbase/hbase-site.xml /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hbase/conf 
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/sue/hbase/backup-masters /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hbase/conf
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/sue/hbase/regionservers /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hbase/conf
cd /home/pranav/Desktop/thesis/cluster-setup/upload/sue && tar -zcf hadoop.tar.gz hadoop && cd - 
cd /home/pranav/Desktop/thesis/cluster-setup/upload/sue && tar -zcf hbase.tar.gz hbase && cd -
        
#update driver config files before upload:		
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/driver/hadoop/core-site.xml /home/pranav/Desktop/thesis/cluster-setup/upload/driver/hadoop/conf 
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/driver/hadoop/hdfs-site.xml /home/pranav/Desktop/thesis/cluster-setup/upload/driver/hadoop/conf 
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/driver/hadoop/mapred-site.xml /home/pranav/Desktop/thesis/cluster-setup/upload/driver/hadoop/conf 
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/driver/hadoop/masters /home/pranav/Desktop/thesis/cluster-setup/upload/driver/hadoop/conf 
yes | cp /home/pranav/Desktop/thesis/cluster-setup/config/driver/hadoop/slaves /home/pranav/Desktop/thesis/cluster-setup/upload/driver/hadoop/conf 
cd /home/pranav/Desktop/thesis/cluster-setup/upload/driver && tar -zcf hadoop.tar.gz hadoop && cd -

while read node; do
	arr=($node)
	host_name=${arr[1]}

	if echo "$host_name" | grep -q "driver"
	then
		ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo rm /usr/local/hadoop/conf/core-site.xml /usr/local/hadoop/conf/hdfs-site.xml /usr/local/hadoop/conf/mapred-site.xml /usr/local/hadoop/conf/masters /usr/local/hadoop/conf/slaves' </dev/null	
		scp -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/config/driver/hadoop/* ubuntu@$host_name:/usr/local/hadoop/conf/
	fi

	if echo "$host_name" | grep -q "sue"
	then
		ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo rm /usr/local/hadoop/conf/core-site.xml /usr/local/hadoop/conf/hdfs-site.xml /usr/local/hadoop/conf/masters /usr/local/hadoop/conf/slaves' </dev/null	
		scp -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/config/sue/hadoop/* ubuntu@$host_name:/usr/local/hadoop/conf/

		ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo rm /usr/local/hbase/conf/backup-masters /usr/local/hbase/conf/hbase-site.xml /usr/local/hbase/conf/regionservers' </dev/null	
		scp -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/config/sue/hbase/* ubuntu@$host_name:/usr/local/hbase/conf/
	fi
done < nodes.txt