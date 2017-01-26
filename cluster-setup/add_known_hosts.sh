touch /home/ubuntu/.ssh/known_hosts
sudo chown ubuntu:ubuntu /home/ubuntu/.ssh/known_hosts
sudo chmod 700 /home/ubuntu/.ssh/known_hosts
while read node; do
	arr=($node)
	host_ip=${arr[0]}
	host_name=${arr[1]}
	ssh-keyscan -f '/home/ubuntu/.ssh/known_hosts' -H $host_name,$host_ip >> /home/ubuntu/.ssh/known_hosts
	ssh-keyscan -f '/home/ubuntu/.ssh/known_hosts' -H $host_ip >> /home/ubuntu/.ssh/known_hosts
	ssh-keyscan -f '/home/ubuntu/.ssh/known_hosts' -H $host_name >> /hoome/ubuntu/.ssh/known_hosts
done < ~/nodes.txt
