#remove old upload files
rm /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hadoop.tar.gz
rm /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hbase.tar.gz
rm /home/pranav/Desktop/thesis/cluster-setup/upload/driver/hadoop.tar.gz

echo 'compressing folders for upload...'

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

echo '\ngenerate ssh-keys for the cluster'
rm ~/Desktop/thesis/cluster-setup/upload/ssh-keys/*
ssh-keygen -f ~/Desktop/thesis/cluster-setup/upload/ssh-keys/id_rsa -q -N ""
cp ~/Desktop/thesis/cluster-setup/upload/ssh-keys/id_rsa.pub ~/Desktop/thesis/cluster-setup/upload/ssh-keys/authorized_keys_temp 
cat ~/.ssh/id_rsa.pub >> ~/Desktop/thesis/cluster-setup/upload/ssh-keys/authorized_keys_temp 

> ~/.ssh/known_hosts

while read node; do
    gnome-terminal -e "sh setup-node.sh $node"
done < nodes.txt
