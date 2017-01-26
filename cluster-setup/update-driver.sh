echo 'UPDATE VmsTpchEvaluation.jar ON evaluation-driver-1'
ssh -o 'StrictHostKeyChecking no' -i ~/.ssh/pranav-vmsevaluation.pem ubuntu@evaluation-driver-1 -tt 'rm -rf ~/VmsTpchEvaluation.jar'
scp  -i ~/.ssh/pranav-vmsevaluation.pem /home/pranav/Desktop/thesis/cluster-setup/upload/driver/mr-jar/VmsTpchEvaluation.jar ubuntu@evaluation-driver-1:~/