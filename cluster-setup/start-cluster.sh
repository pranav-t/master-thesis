echo 'UPDATE VmsTpchEvaluation.jar ON evaluation-driver-1'
# ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@evaluation-driver-1 -tt 'rm -rf ~/VmsTpchEvaluation.jar'
# scp  -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/upload/driver/mr-jar/VmsTpchEvaluation.jar ubuntu@evaluation-driver-1:~/

echo 'DELETE HBASE METRICS'
while read node; do
	arr=($node)
	host_name=${arr[1]}
	if echo "$host_name" | grep -q "sue"
	then
		ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo rm -r /usr/local/hbase/*.metrics' </dev/null
	fi
done < nodes.txt

echo 'KILL HDFS, MR, HBASE ON ALL NODES'
while read node; do
	arr=($node)
	host_name=${arr[1]}
	ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo kill -9 $(jps | grep -E "NameNode|SecondaryNameNode|DataNode|JobTracker|TaskTracker|HMaster|HRegionServer|HQuorumPeer" | cut -d" " -f1 | tr "\n" " ")' </dev/null
done < nodes.txt

echo 'CLEANUP HDFS'
while read node; do
	arr=($node)
	host_name=${arr[1]}
	ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo rm -r /home/ubuntu/zookeeper/*' </dev/null
	ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo rm -r /app/hadoop/tmp'	</dev/null
	ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo mkdir -p /app/hadoop/tmp && sudo chown ubuntu:ubuntu /app/hadoop/tmp && sudo chmod 750 /app/hadoop/tmp' </dev/null
done < nodes.txt

echo 'DRIVER: FORMAT HADOOP NAMENODE'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@evaluation-driver-1 -tt 'bash /usr/local/hadoop/bin/hadoop namenode -format'

echo 'SUE: FORMAT HADOOP NAMENODE'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@sue-master-1 -tt 'bash /usr/local/hadoop/bin/hadoop namenode -format'

# start 
echo 'DRIVER: START HDFS AND MAPRED'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@evaluation-driver-1 -tt 'bash /usr/local/hadoop/bin/start-dfs.sh && bash /usr/local/hadoop/bin/start-mapred.sh'

echo 'SUE-MASTER-1: START HDFS'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@sue-master-1 -tt 'bash /usr/local/hadoop/bin/start-dfs.sh'

echo 'SUE-MASTER-2: START HBASE'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@sue-master-2 -tt 'bash /usr/local/hbase/bin/start-hbase.sh'

echo 'PUT dbgen SCRIPTS ON DRIVER-HDFS'
scp -r -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/upload/driver/dbgen ubuntu@evaluation-driver-1:~/
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@evaluation-driver-1 -tt 'bash /usr/local/hadoop/bin/hadoop fs -put ~/dbgen /user/ubuntu/vmsevaluation/dbgen'

echo 'ALL DONE!!... LAUNCHING MONITOR...'
sleep 6
#nano ~/.config/terminator/config
terminator -m --layout=cluster