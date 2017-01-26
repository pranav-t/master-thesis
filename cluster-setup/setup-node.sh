echo '##setting up' $1 $2 '##'

host_ip=$1
host_name=$2

echo '\nupdate local known_hosts:'
yes | ssh-keygen -f "/home/pranav/.ssh/known_hosts" -R $host_ip
yes | ssh-keygen -f "/home/pranav/.ssh/known_hosts" -R $host_name
yes | ssh-keyscan -f '/home/pranav/.ssh/known_hosts' -H $host_name,$host_ip >> /home/pranav/.ssh/known_hosts

echo '\nupdate current node''s /etc/hosts file'
scp -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/nodes.txt ubuntu@$host_name:/home/ubuntu/
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo sh -c "cat /home/ubuntu/nodes.txt >> /etc/hosts"'

echo '\nadd rsa key-pair to node:'
scp  -i ~/.ssh/pranav-vmsevaluation.pem ~/Desktop/thesis/cluster-setup/upload/ssh-keys/id_rsa ubuntu@$host_name:/home/ubuntu/.ssh
scp  -i ~/.ssh/pranav-vmsevaluation.pem ~/Desktop/thesis/cluster-setup/upload/ssh-keys/id_rsa.pub ubuntu@$host_name:/home/ubuntu/.ssh
scp  -i ~/.ssh/pranav-vmsevaluation.pem ~/Desktop/thesis/cluster-setup/upload/ssh-keys/authorized_keys_temp ubuntu@$host_name:/home/ubuntu/.ssh
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo cat /home/ubuntu/.ssh/authorized_keys_temp >> /home/ubuntu/.ssh/authorized_keys'

echo '\nadding all other nodes to known_hosts of current node...'
scp  -i ~/.ssh/pranav-vmsevaluation.pem ~/Desktop/thesis/cluster-setup/add_known_hosts.sh ubuntu@$host_name:/home/ubuntu
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo bash add_known_hosts.sh'

echo '\ninstall java:'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'yes | sudo apt-get update'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'yes | sudo apt-get install default-jre'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'yes | sudo apt install openjdk-8-jdk-headless'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo  sh -c "echo ''JAVA_HOME=export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/jre'' >> /etc/environment"'

echo '\nmake /usr/local accessible for upload(make it 777)'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo chmod -R 777 /usr/local'

echo '\nupdate bashrc'
scp -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/bashrc-extn.txt ubuntu@$host_name:/home/ubuntu/
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'sudo sh -c "cat /home/ubuntu/bashrc-extn.txt >> ~/.bashrc" && rm /home/ubuntu/bashrc-extn.txt'

if echo "$host_name" | grep -q "sue"
then
  	echo '\ncopy hadoop to sue node'
  	scp -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hadoop.tar.gz ubuntu@$host_name:/usr/local
  	ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'cd /usr/local/ && tar -xzf hadoop.tar.gz && rm hadoop.tar.gz && chmod -R 777 /usr/local/hadoop'

  	echo '\ncopy hbase to sue node'
  	scp  -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/upload/sue/hbase.tar.gz ubuntu@$host_name:/usr/local
  	ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'cd /usr/local/ && tar -xzf hbase.tar.gz && rm hbase.tar.gz && chmod -R 777 /usr/local/hbase'

  	echo '\n\nALL DONE'
fi

if echo "$host_name" | grep -q "driver"
then
  	echo '\ncopy hadoop to driver node'
  	scp  -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/upload/driver/hadoop.tar.gz ubuntu@$host_name:/usr/local
  	ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@$host_name -tt 'cd /usr/local/ && tar -xzf hadoop.tar.gz && rm hadoop.tar.gz && chmod -R 777 /usr/local/hadoop'

  	echo '\n\nALL DONE'
fi

